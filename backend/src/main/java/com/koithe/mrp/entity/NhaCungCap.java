package com.koithe.mrp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nhacungcap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhaCungCap {

    @Id
    @Column(name = "mancc", length = 20)
    private String maNCC;

    @Column(name = "tenncc", length = 150, nullable = false)
    private String tenNCC;

    @Column(name = "diachi", length = 255)
    private String diaChi;

    @Column(name = "sdt", length = 15)
    private String sdt;

    @Column(name = "email", length = 100)
    private String email;
}