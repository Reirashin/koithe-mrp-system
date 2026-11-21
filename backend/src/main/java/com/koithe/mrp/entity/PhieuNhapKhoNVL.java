package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "phieunhapkhonvl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhieuNhapKhoNVL {

    @Id
    @Column(name = "mapn", length = 20)
    private String maPN;

    @Column(name = "ngaylap", nullable = false)
    private LocalDateTime ngayLap;

    @Column(name = "tongtien", precision = 15, scale = 2, nullable = false)
    private BigDecimal tongTien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mancc", nullable = false)
    private NhaCungCap nhaCungCap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manguoilap", nullable = false)
    private NguoiDung nguoiLap;
}