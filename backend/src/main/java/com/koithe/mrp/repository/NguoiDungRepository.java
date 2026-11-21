package com.koithe.mrp.repository;

import com.koithe.mrp.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
}