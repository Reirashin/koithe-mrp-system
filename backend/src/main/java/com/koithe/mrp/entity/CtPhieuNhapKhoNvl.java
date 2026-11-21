package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ct_phieunhapkhonvl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CtPhieuNhapKhoNvl {

    @EmbeddedId
    private CtPhieuNhapKhoNvlId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maPN")
    @JoinColumn(name = "mapn")
    private PhieuNhapKhoNVL phieuNhapKhoNVL;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maNVL")
    @JoinColumn(name = "manvl")
    private NguyenVatLieu nguyenVatLieu;

    @Column(name = "slnhap", precision = 12, scale = 3, nullable = false)
    private BigDecimal slNhap;

    @Column(name = "dongianhap", precision = 15, scale = 2, nullable = false)
    private BigDecimal donGiaNhap;
}