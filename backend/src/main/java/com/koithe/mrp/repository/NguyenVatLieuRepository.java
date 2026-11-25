package com.koithe.mrp.repository;

import com.koithe.mrp.entity.NguyenVatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NguyenVatLieuRepository extends JpaRepository<NguyenVatLieu, String> {

    @Query("SELECT n FROM NguyenVatLieu n ORDER BY n.maNVL ASC")
    List<NguyenVatLieu> layTatCaSapXep();

    @Query("SELECT n FROM NguyenVatLieu n " +
           "WHERE LOWER(n.maNVL) LIKE LOWER(CONCAT('%', :tuKhoa, '%')) " +
           "   OR LOWER(n.tenNVL) LIKE LOWER(CONCAT('%', :tuKhoa, '%')) " +
           "ORDER BY n.maNVL ASC")
    List<NguyenVatLieu> timKiemTheoTuKhoa(@Param("tuKhoa") String tuKhoa);

    @Query("SELECT COUNT(n) > 0 FROM NguyenVatLieu n WHERE LOWER(n.tenNVL) = LOWER(:tenNVL)")
    boolean tonTaiTenNVL(@Param("tenNVL") String tenNVL);

    @Query("SELECT COUNT(n) > 0 FROM NguyenVatLieu n WHERE LOWER(n.tenNVL) = LOWER(:tenNVL) AND n.maNVL <> :maNVL")
    boolean tonTaiTenNVLKhacMa(@Param("tenNVL") String tenNVL, @Param("maNVL") String maNVL);

    @Query(value = "SELECT nextval('seq_nvl')", nativeQuery = true)
    Long laySoThuTuTiepTheo();
}