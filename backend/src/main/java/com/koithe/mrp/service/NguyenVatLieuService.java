package com.koithe.mrp.service;

import com.koithe.mrp.dto.request.CapNhatNguyenVatLieuRequest;
import com.koithe.mrp.dto.request.TaoNguyenVatLieuRequest;
import com.koithe.mrp.dto.response.NguyenVatLieuResponse;
import com.koithe.mrp.entity.NguyenVatLieu;
import com.koithe.mrp.exception.DuplicateResourceException;
import com.koithe.mrp.exception.ResourceNotFoundException;
import com.koithe.mrp.repository.NguyenVatLieuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class NguyenVatLieuService {

    private final NguyenVatLieuRepository nguyenVatLieuRepo;

    @Transactional(readOnly = true)
    public List<NguyenVatLieuResponse> layDanhSach(String tuKhoa) {
        List<NguyenVatLieu> danhSach = (tuKhoa == null || tuKhoa.isBlank())
                ? nguyenVatLieuRepo.layTatCaSapXep()
                : nguyenVatLieuRepo.timKiemTheoTuKhoa(tuKhoa.trim());

        return danhSach.stream()
                .map(NguyenVatLieuResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public NguyenVatLieuResponse layChiTiet(String maNVL) {
        NguyenVatLieu entity = timTheoMaHoacBaoLoi(maNVL);
        return NguyenVatLieuResponse.fromEntity(entity);
    }

    @Transactional
    public NguyenVatLieuResponse taoMoi(TaoNguyenVatLieuRequest request) {
        String tenChuanHoa = request.getTenNVL().trim();
        String dvtChuanHoa = request.getDvt().trim().toLowerCase(Locale.ROOT);

        // Kiểm tra trùng tên trước khi gọi sequence để tránh lãng phí số thứ tự
        if (nguyenVatLieuRepo.tonTaiTenNVL(tenChuanHoa)) {
            throw new DuplicateResourceException("Nguyên vật liệu '" + tenChuanHoa + "' đã tồn tại trong hệ thống");
        }

        Long soThuTu = nguyenVatLieuRepo.laySoThuTuTiepTheo();
        String maMoi = String.format("NVL%02d", soThuTu);

        NguyenVatLieu entity = NguyenVatLieu.builder()
                .maNVL(maMoi)
                .tenNVL(tenChuanHoa)
                .dvt(dvtChuanHoa)
                .slTon(BigDecimal.ZERO)
                .donGiaNhap(request.getDonGiaNhap())
                .build();

        NguyenVatLieu daLuu = nguyenVatLieuRepo.save(entity);
        return NguyenVatLieuResponse.fromEntity(daLuu);
    }

    @Transactional
    public NguyenVatLieuResponse capNhat(String maNVL, CapNhatNguyenVatLieuRequest request) {
        NguyenVatLieu entity = timTheoMaHoacBaoLoi(maNVL);

        String tenChuanHoa = request.getTenNVL().trim();
        String dvtChuanHoa = request.getDvt().trim().toLowerCase(Locale.ROOT);

        if (nguyenVatLieuRepo.tonTaiTenNVLKhacMa(tenChuanHoa, entity.getMaNVL())) {
            throw new DuplicateResourceException("Nguyên vật liệu '" + tenChuanHoa + "' đã tồn tại trong hệ thống");
        }

        // Khóa tuyệt đối trường slTon: chỉ cập nhật thông tin danh mục
        entity.setTenNVL(tenChuanHoa);
        entity.setDvt(dvtChuanHoa);
        entity.setDonGiaNhap(request.getDonGiaNhap());

        NguyenVatLieu daLuu = nguyenVatLieuRepo.save(entity);
        return NguyenVatLieuResponse.fromEntity(daLuu);
    }

    @Transactional
    public void xoa(String maNVL) {
        NguyenVatLieu entity = timTheoMaHoacBaoLoi(maNVL);
        nguyenVatLieuRepo.delete(entity);
        // Flush ngay để kích hoạt kiểm tra ON DELETE RESTRICT của PostgreSQL (trả 409 qua GlobalExceptionHandler)
        nguyenVatLieuRepo.flush();
    }

    // helper nội bộ (những helper nào dùng chung cho nhiều service thì hẳn tính tới việc tách ra thành 1 file riêng không cần overengineering ngay lúc này)
    private NguyenVatLieu timTheoMaHoacBaoLoi(String maNVL) {
        String maChuanHoa = maNVL.trim();
        return nguyenVatLieuRepo.findById(maChuanHoa)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nguyên vật liệu với mã: " + maChuanHoa));
    }
}
