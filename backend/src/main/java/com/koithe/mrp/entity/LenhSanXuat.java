package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lenhsanxuat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LenhSanXuat {

    @Id
    @Column(name = "malsx", length = 20)
    private String maLSX;

    @Column(name = "ngaylap", nullable = false)
    private LocalDateTime ngayLap;

    @Column(name = "ngayht")
    private LocalDateTime ngayHT;

    @Column(name = "trangthai", length = 30, nullable = false)
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manguoilap", nullable = false)
    private NguoiDung nguoiLap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manguoiphutrach", nullable = false)
    private NguoiDung nguoiPhuTrach;
}