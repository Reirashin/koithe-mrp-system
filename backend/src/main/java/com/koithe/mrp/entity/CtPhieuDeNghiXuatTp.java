package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ct_phieudenghixuattp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CtPhieuDeNghiXuatTp {

    @EmbeddedId
    private CtPhieuDeNghiXuatTpId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maPdnTp")
    @JoinColumn(name = "mapdn_tp")
    private PhieuDeNghiXuatTP phieuDeNghiXuatTP;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maTP")
    @JoinColumn(name = "matp")
    private ThanhPham thanhPham;

    @Column(name = "slxuat", nullable = false)
    private Integer slXuat;
}