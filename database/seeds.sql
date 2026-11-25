-- ============================================================================
-- HỆ THỐNG QUẢN LÝ KHO & SẢN XUẤT (MRP) - KOI THÉ
-- Script nạp dữ liệu mẫu (DML - seeds.sql)
-- ============================================================================

-- Dọn sạch dữ liệu cũ (nếu chạy lại script nhiều lần)
TRUNCATE TABLE 
    CT_PhieuDeNghiXuatTP, PhieuDeNghiXuatTP,
    CT_PhieuDeNghiXuatNVL, PhieuDeNghiXuatNVL,
    CT_LenhSanXuat, LenhSanXuat,
    CT_PhieuNhapKhoNVL, PhieuNhapKhoNVL,
    CongThuc_BOM, NhaCungCap, NguyenVatLieu, ThanhPham, NguoiDung
RESTART IDENTITY CASCADE;

-- 1. Nạp danh sách Người dùng (Mật khẩu mẫu cho tất cả tài khoản bên dưới là: 123456)
INSERT INTO NguoiDung (MaND, TenDangNhap, MatKhauHash, HoTen, VaiTro, SDT) VALUES
('ND01', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Quản Trị Hệ Thống', 'Admin', '0901000001'),
('ND02', 'huy.ql', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Trần Quốc Huy', 'QuanLy', '0901000002'),
('ND03', 'phat.nv', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Vũ Tấn Phát', 'NhanVien', '0901000003');

-- 2. Nạp danh mục Thành phẩm
INSERT INTO ThanhPham (MaTP, TenTP, DVT, SLTon, GiaBan) VALUES
('TP01', 'Trà sữa Hoàng Kim', 'ly', 10, 45000.00),
('TP02', 'Lục trà Macchiato', 'ly', 15, 42000.00),
('TP03', 'Hồng trà Chanh dây', 'ly', 20, 40000.00);

-- 3. Nạp danh mục Nguyên vật liệu
INSERT INTO NguyenVatLieu (MaNVL, TenNVL, DVT, SLTon, DonGiaNhap) VALUES
('NVL01', 'Trà đen Ceylon', 'g', 5000.000, 320.00),
('NVL02', 'Bột sữa KOI', 'g', 10000.000, 180.00),
('NVL03', 'Trân châu hoàng kim', 'g', 8000.000, 95.00),
('NVL04', 'Bộ ly + nắp + ống hút', 'bo', 500.000, 2500.00),
('NVL05', 'Lục trà lài', 'g', 4000.000, 350.00),
('NVL06', 'Kem sữa Macchiato', 'ml', 6000.000, 120.00);

SELECT setval('seq_nvl', 6);

-- 4. Nạp danh mục Nhà cung cấp
INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email) VALUES
('NCC01', 'Công ty TNHH Nguyên Liệu Trà Đài Loan', 'Quận 7, TP.HCM', '02838112233', 'sales@tradailoan.vn'),
('NCC02', 'Công ty Bao Bì Thực Phẩm Xanh', 'Bình Tân, TP.HCM', '02839445566', 'baobi@tp-xanh.vn');

-- 5. Nạp Công thức định mức BOM (Tiêu hao cho 1 ly thành phẩm)
-- Công thức TP01: Trà sữa Hoàng Kim (15g trà, 35g bột sữa, 50g trân châu, 1 bộ ly)
INSERT INTO CongThuc_BOM (MaTP, MaNVL, SLCanDung) VALUES
('TP01', 'NVL01', 15.000),
('TP01', 'NVL02', 35.000),
('TP01', 'NVL03', 50.000),
('TP01', 'NVL04', 1.000),
-- Công thức TP02: Lục trà Macchiato (12g lục trà, 60ml kem macchiato, 1 bộ ly)
('TP02', 'NVL05', 12.000),
('TP02', 'NVL06', 60.000),
('TP02', 'NVL04', 1.000);

-- 6. Nạp 1 Phiếu nhập kho mẫu của Quản lý Huy
INSERT INTO PhieuNhapKhoNVL (MaPN, NgayLap, TongTien, MaNCC, MaNguoiLap) VALUES
('PN01', '2026-11-20 08:30:00', 3400000.00, 'NCC01', 'ND02');

INSERT INTO CT_PhieuNhapKhoNVL (MaPN, MaNVL, SLNhap, DonGiaNhap) VALUES
('PN01', 'NVL01', 5000.000, 320.00),
('PN01', 'NVL02', 10000.000, 180.00);

-- 7. Nạp 1 Lệnh sản xuất mẫu: Đơn tiệc 50 ly Trà sữa Hoàng Kim giao cho Phát
INSERT INTO LenhSanXuat (MaLSX, NgayLap, NgayHT, TrangThai, MaNguoiLap, MaNguoiPhuTrach) VALUES
('LSX01', '2026-11-20 09:00:00', NULL, 'ChoTiepNhan', 'ND02', 'ND03');

INSERT INTO CT_LenhSanXuat (MaLSX, MaTP, SLCanSX) VALUES
('LSX01', 'TP01', 50);

--Test 1
--SELECT 
--    ct.MaLSX,
--    tp.TenTP,
--    ct.SLCanSX AS SoLuongCanLam,
--    nvl.TenNVL,
--    bom.SLCanDung AS DinhMuc_1_Ly,
--    (ct.SLCanSX * bom.SLCanDung) AS TongNVL_CanDung,
--    nvl.SLTon AS TonKhoHienTai,
--    nvl.DVT
--FROM CT_LenhSanXuat ct
--JOIN ThanhPham tp ON ct.MaTP = tp.MaTP
--JOIN CongThuc_BOM bom ON tp.MaTP = bom.MaTP
--JOIN NguyenVatLieu nvl ON bom.MaNVL = nvl.MaNVL
--WHERE ct.MaLSX = 'LSX01';
--
--
--Test 2
--BEGIN;
--
-- 1. Nhân viên Phát (ND03) lập phiếu đề nghị xuất NVL cho LSX01
--INSERT INTO phieudenghixuatnvl (mapdn_nvl, malsx, manguoilap, trangthai)
--VALUES ('PDN_NVL01', 'LSX01', 'ND03', 'ChoDuyet');
--
--INSERT INTO ct_phieudenghixuatnvl (mapdn_nvl, manvl, slxuat) VALUES
--('PDN_NVL01', 'NVL01', 750.000),
--('PDN_NVL01', 'NVL02', 1750.000),
--('PDN_NVL01', 'NVL03', 2500.000),
--('PDN_NVL01', 'NVL04', 50.000);
--
-- 2. Quản lý Huy (ND02) phê duyệt phiếu & trừ tồn kho NVL
--UPDATE phieudenghixuatnvl
--SET trangthai = 'DaDuyet',
--    ngayduyet = CURRENT_TIMESTAMP,
--    manguoiduyet = 'ND02'
--WHERE mapdn_nvl = 'PDN_NVL01';
--
--UPDATE nguyenvatlieu nvl
--SET slton = nvl.slton - ct.slxuat
--FROM ct_phieudenghixuatnvl ct
--WHERE nvl.manvl = ct.manvl
--  AND ct.mapdn_nvl = 'PDN_NVL01';
--
--UPDATE lenhsanxuat
--SET trangthai = 'DangSanXuat'
--WHERE malsx = 'LSX01';
--
--COMMIT;
--
-- Kiểm tra lại tồn kho NVL sau khi trừ
--SELECT manvl, tennvl, slton, dvt FROM nguyenvatlieu ORDER BY manvl;
--
--Test 3
-- Thử làm tồn kho bị âm (Phải báo lỗi: violates check constraint "nguyenvatlieu_slton_check")
--UPDATE nguyenvatlieu SET slton = -10 WHERE manvl = 'NVL01';
--
--select * from nguyenvatlieu