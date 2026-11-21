package com.koithe.mrp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CtPhieuDeNghiXuatNvlId implements Serializable {

    @Column(name = "mapdn_nvl", length = 20)
    private String maPdnNvl;

    @Column(name = "manvl", length = 20)
    private String maNVL;
}