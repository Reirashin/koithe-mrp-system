package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ct_lenhsanxuat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CtLenhSanXuat {

    @EmbeddedId
    private CtLenhSanXuatId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maLSX")
    @JoinColumn(name = "malsx")
    private LenhSanXuat lenhSanXuat;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maTP")
    @JoinColumn(name = "matp")
    private ThanhPham thanhPham;

    @Column(name = "slcansx", nullable = false)
    private Integer slCanSX;
}