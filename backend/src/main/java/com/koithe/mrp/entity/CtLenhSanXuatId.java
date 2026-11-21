package com.koithe.mrp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CtLenhSanXuatId implements Serializable {

    @Column(name = "malsx", length = 20)
    private String maLSX;

    @Column(name = "matp", length = 20)
    private String maTP;
}