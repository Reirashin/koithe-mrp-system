package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "phieudenghixuattp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhieuDeNghiXuatTP {

    @Id
    @Column(name = "mapdn_tp", length = 20)
    private String maPdnTp;

    @Column(name = "ngaylap", nullable = false)
    private LocalDateTime ngayLap;

    @Column(name = "ngayduyet")
    private LocalDateTime ngayDuyet;

    @Column(name = "trangthai", length = 30, nullable = false)
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manguoilap", nullable = false)
    private NguoiDung nguoiLap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manguoiduyet")
    private NguoiDung nguoiDuyet;
}