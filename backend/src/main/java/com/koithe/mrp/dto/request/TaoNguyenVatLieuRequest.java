package com.koithe.mrp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dữ liệu tạo mới nguyên vật liệu (mã NVL tự động sinh, slTon mặc định bằng 0)")
public class TaoNguyenVatLieuRequest {

    @NotBlank(message = "Tên nguyên vật liệu không được để trống")
    @Size(max = 100, message = "Tên nguyên vật liệu không được vượt quá 100 ký tự")
    @Schema(description = "Tên nguyên vật liệu", example = "Siro chanh dây")
    private String tenNVL;

    @NotBlank(message = "Đơn vị tính không được để trống")
    @Size(max = 20, message = "Đơn vị tính không được vượt quá 20 ký tự")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s/.-]+$", message = "Đơn vị tính chỉ được chứa chữ cái, chữ số và ký tự / . -")
    @Schema(description = "Đơn vị tính (tự chuẩn hóa về chữ thường khi lưu)", example = "ml")
    private String dvt;

    @NotNull(message = "Đơn giá nhập không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Đơn giá nhập phải lớn hơn hoặc bằng 0")
    @Digits(integer = 13, fraction = 2, message = "Đơn giá nhập tối đa 13 chữ số phần nguyên và 2 chữ số thập phân")
    @Schema(description = "Đơn giá nhập tham chiếu (VNĐ)", example = "150.00")
    private BigDecimal donGiaNhap;
}
