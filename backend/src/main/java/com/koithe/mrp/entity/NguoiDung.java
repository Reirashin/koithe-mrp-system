package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nguoidung")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDung {

    @Id
    @Column(name = "mand", length = 20)
    private String maND;

    @Column(name = "tendangnhap", length = 50, nullable = false, unique = true)
    private String tenDangNhap;

    @Column(name = "matkhauhash", length = 255, nullable = false)
    private String matKhauHash;

    @Column(name = "hoten", length = 100, nullable = false)
    private String hoTen;

    @Column(name = "vaitro", length = 20, nullable = false)
    private String vaiTro;

    @Column(name = "sdt", length = 15)
    private String sdt;
}