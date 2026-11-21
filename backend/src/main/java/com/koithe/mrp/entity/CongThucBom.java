package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "congthuc_bom")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CongThucBom {

    @EmbeddedId
    private CongThucBomId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maTP")
    @JoinColumn(name = "matp")
    private ThanhPham thanhPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maNVL")
    @JoinColumn(name = "manvl")
    private NguyenVatLieu nguyenVatLieu;

    @Column(name = "slcandung", precision = 12, scale = 3, nullable = false)
    private BigDecimal slCanDung;
}