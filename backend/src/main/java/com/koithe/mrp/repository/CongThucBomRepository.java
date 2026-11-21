package com.koithe.mrp.repository;

import com.koithe.mrp.entity.CongThucBom;
import com.koithe.mrp.entity.CongThucBomId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CongThucBomRepository extends JpaRepository<CongThucBom, CongThucBomId> {
    List<CongThucBom> findByIdMaTP(String maTP);
}