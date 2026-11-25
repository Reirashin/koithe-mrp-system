package com.koithe.mrp.controller;

import com.koithe.mrp.dto.request.CapNhatNguyenVatLieuRequest;
import com.koithe.mrp.dto.request.TaoNguyenVatLieuRequest;
import com.koithe.mrp.dto.response.ApiResponse;
import com.koithe.mrp.dto.response.NguyenVatLieuResponse;
import com.koithe.mrp.service.NguyenVatLieuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nguyen-vat-lieu")
@RequiredArgsConstructor
@Tag(name = "Nguyên vật liệu", description = "API quản lý danh mục nguyên vật liệu lưu kho")
public class NguyenVatLieuController {

    private final NguyenVatLieuService nguyenVatLieuService;

    @GetMapping
    @Operation(summary = "Lấy danh sách nguyên vật liệu", description = "Trả về danh sách nguyên vật liệu kèm tồn kho hiện tại; hỗ trợ lọc theo từ khóa mã hoặc tên.")
    public ResponseEntity<ApiResponse<List<NguyenVatLieuResponse>>> layDanhSach(
            @Parameter(description = "Từ khóa tìm kiếm theo mã hoặc tên NVL")
            @RequestParam(required = false) String tuKhoa) {
        List<NguyenVatLieuResponse> danhSach = nguyenVatLieuService.layDanhSach(tuKhoa);
        return ResponseEntity.ok(ApiResponse.thanhCong(danhSach, "Lấy danh sách nguyên vật liệu thành công"));
    }

    @GetMapping("/{maNVL}")
    @Operation(summary = "Xem chi tiết nguyên vật liệu", description = "Lấy thông tin chi tiết 1 nguyên vật liệu theo mã.")
    public ResponseEntity<ApiResponse<NguyenVatLieuResponse>> layChiTiet(
            @Parameter(description = "Mã nguyên vật liệu", example = "NVL01")
            @PathVariable String maNVL) {
        NguyenVatLieuResponse response = nguyenVatLieuService.layChiTiet(maNVL);
        return ResponseEntity.ok(ApiResponse.thanhCong(response, "Lấy chi tiết nguyên vật liệu thành công"));
    }

    @PostMapping
    @Operation(summary = "Tạo mới nguyên vật liệu", description = "Tự động sinh mã kế tiếp từ seq_nvl, chuẩn hóa đơn vị tính chữ thường, slTon mặc định bằng 0.")
    public ResponseEntity<ApiResponse<NguyenVatLieuResponse>> taoMoi(
            @Valid @RequestBody TaoNguyenVatLieuRequest request) {
        NguyenVatLieuResponse response = nguyenVatLieuService.taoMoi(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.taoMoi(response, "Tạo mới nguyên vật liệu thành công"));
    }

    @PutMapping("/{maNVL}")
    @Operation(summary = "Cập nhật nguyên vật liệu", description = "Cập nhật tên, đơn vị tính, đơn giá nhập; khóa hoàn toàn trường slTon.")
    public ResponseEntity<ApiResponse<NguyenVatLieuResponse>> capNhat(
            @Parameter(description = "Mã nguyên vật liệu", example = "NVL01")
            @PathVariable String maNVL,
            @Valid @RequestBody CapNhatNguyenVatLieuRequest request) {
        NguyenVatLieuResponse response = nguyenVatLieuService.capNhat(maNVL, request);
        return ResponseEntity.ok(ApiResponse.thanhCong(response, "Cập nhật nguyên vật liệu thành công"));
    }

    @DeleteMapping("/{maNVL}")
    @Operation(summary = "Xóa nguyên vật liệu", description = "Xóa nguyên vật liệu nếu chưa gắn với công thức BOM hoặc chứng từ nhập/xuất kho.")
    public ResponseEntity<ApiResponse<Void>> xoa(
            @Parameter(description = "Mã nguyên vật liệu", example = "NVL07")
            @PathVariable String maNVL) {
        nguyenVatLieuService.xoa(maNVL);
        return ResponseEntity.ok(ApiResponse.thanhCong(null, "Xóa nguyên vật liệu thành công"));
    }
}
