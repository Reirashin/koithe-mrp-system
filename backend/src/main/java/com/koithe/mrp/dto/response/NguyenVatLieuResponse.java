package com.koithe.mrp.dto.response;

import com.koithe.mrp.entity.NguyenVatLieu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Thông tin nguyên vật liệu trả về cho client")
public class NguyenVatLieuResponse {

    @Schema(description = "Mã nguyên vật liệu", example = "NVL01")
    private String maNVL;

    @Schema(description = "Tên nguyên vật liệu", example = "Trà đen Ceylon")
    private String tenNVL;

    @Schema(description = "Đơn vị tính", example = "g")
    private String dvt;

    @Schema(description = "Số lượng tồn kho hiện tại", example = "5000.000")
    private BigDecimal slTon;

    @Schema(description = "Đơn giá nhập tham chiếu (VNĐ)", example = "320.00")
    private BigDecimal donGiaNhap;

    public static NguyenVatLieuResponse fromEntity(NguyenVatLieu entity) {
        return NguyenVatLieuResponse.builder()
                .maNVL(entity.getMaNVL())
                .tenNVL(entity.getTenNVL())
                .dvt(entity.getDvt())
                .slTon(entity.getSlTon())
                .donGiaNhap(entity.getDonGiaNhap())
                .build();
    }
}
