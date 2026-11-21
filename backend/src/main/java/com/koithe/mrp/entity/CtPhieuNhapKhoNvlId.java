package com.koithe.mrp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CtPhieuNhapKhoNvlId implements Serializable {

    @Column(name = "mapn", length = 20)
    private String maPN;

    @Column(name = "manvl", length = 20)
    private String maNVL;
}