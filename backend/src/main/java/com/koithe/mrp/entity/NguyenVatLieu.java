package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "nguyenvatlieu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguyenVatLieu {

    @Id
    @Column(name = "manvl", length = 20)
    private String maNVL;

    @Column(name = "tennvl", length = 100, nullable = false)
    private String tenNVL;

    @Column(name = "dvt", length = 20, nullable = false)
    private String dvt;

    @Column(name = "slton", precision = 12, scale = 3, nullable = false)
    private BigDecimal slTon;

    @Column(name = "dongianhap", precision = 15, scale = 2, nullable = false)
    private BigDecimal donGiaNhap;
}