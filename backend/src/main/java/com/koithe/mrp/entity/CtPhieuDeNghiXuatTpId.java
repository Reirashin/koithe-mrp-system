package com.koithe.mrp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CtPhieuDeNghiXuatTpId implements Serializable {

    @Column(name = "mapdn_tp", length = 20)
    private String maPdnTp;

    @Column(name = "matp", length = 20)
    private String maTP;
}