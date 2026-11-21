package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "phieudenghixuatnvl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhieuDeNghiXuatNVL {

    @Id
    @Column(name = "mapdn_nvl", length = 20)
    private String maPdnNvl;

    @Column(name = "ngaylap", nullable = false)
    private LocalDateTime ngayLap;

    @Column(name = "ngayduyet")
    private LocalDateTime ngayDuyet;

    @Column(name = "trangthai", length = 30, nullable = false)
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "malsx", nullable = false)
    private LenhSanXuat lenhSanXuat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manguoilap", nullable = false)
    private NguoiDung nguoiLap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manguoiduyet")
    private NguoiDung nguoiDuyet;
}