package com.koithe.mrp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongThucBomId implements Serializable {

    @Column(name = "matp", length = 20)
    private String maTP;

    @Column(name = "manvl", length = 20)
    private String maNVL;
}