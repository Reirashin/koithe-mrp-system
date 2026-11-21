package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ct_phieudenghixuatnvl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CtPhieuDeNghiXuatNvl {

    @EmbeddedId
    private CtPhieuDeNghiXuatNvlId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maPdnNvl")
    @JoinColumn(name = "mapdn_nvl")
    private PhieuDeNghiXuatNVL phieuDeNghiXuatNVL;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maNVL")
    @JoinColumn(name = "manvl")
    private NguyenVatLieu nguyenVatLieu;

    @Column(name = "slxuat", precision = 12, scale = 3, nullable = false)
    private BigDecimal slXuat;
}