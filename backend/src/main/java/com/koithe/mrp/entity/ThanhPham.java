package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "thanhpham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhPham {

    @Id
    @Column(name = "matp", length = 20)
    private String maTP;

    @Column(name = "tentp", length = 100, nullable = false)
    private String tenTP;

    @Column(name = "dvt", length = 20, nullable = false)
    private String dvt;

    @Column(name = "slton", nullable = false)
    private Integer slTon;

    @Column(name = "giaban", precision = 15, scale = 2, nullable = false)
    private BigDecimal giaBan;
}