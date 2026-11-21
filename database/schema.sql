-- Chuyển sang database mrp_koithe
--\c mrp_koithe

-- ============================================================================
-- HỆ THỐNG QUẢN LÝ KHO & SẢN XUẤT (MRP) - KOI THÉ
-- Script khởi tạo cấu trúc cơ sở dữ liệu (DDL) - PostgreSQL
-- Chuẩn hóa: 3NF / BCNF (13 Bảng)
-- ============================================================================

-- Xóa các bảng cũ nếu tồn tại (theo thứ tự ngược lại của khóa ngoại để tránh lỗi)
DROP TABLE IF EXISTS CT_PhieuDeNghiXuatTP CASCADE;
DROP TABLE IF EXISTS PhieuDeNghiXuatTP CASCADE;
DROP TABLE IF EXISTS CT_PhieuDeNghiXuatNVL CASCADE;
DROP TABLE IF EXISTS PhieuDeNghiXuatNVL CASCADE;
DROP TABLE IF EXISTS CT_LenhSanXuat CASCADE;
DROP TABLE IF EXISTS LenhSanXuat CASCADE;
DROP TABLE IF EXISTS CT_PhieuNhapKhoNVL CASCADE;
DROP TABLE IF EXISTS PhieuNhapKhoNVL CASCADE;
DROP TABLE IF EXISTS CongThuc_BOM CASCADE;
DROP TABLE IF EXISTS NhaCungCap CASCADE;
DROP TABLE IF EXISTS NguyenVatLieu CASCADE;
DROP TABLE IF EXISTS ThanhPham CASCADE;
DROP TABLE IF EXISTS NguoiDung CASCADE;

-- ============================================================================
-- NHÓM 1: 4 BẢNG DANH MỤC GỐC (Khởi tạo đầu tiên)
-- ============================================================================

-- 1. Bảng Người Dùng (Tài khoản & Phân quyền)
CREATE TABLE NguoiDung (
    MaND         VARCHAR(20)  PRIMARY KEY,
    TenDangNhap  VARCHAR(50)  NOT NULL UNIQUE,
    MatKhauHash  VARCHAR(255) NOT NULL,
    HoTen        VARCHAR(100) NOT NULL,
    VaiTro       VARCHAR(20)  NOT NULL CHECK (VaiTro IN ('Admin', 'QuanLy', 'NhanVien')),
    SDT          VARCHAR(15)
);

-- 2. Bảng Thành Phẩm (Danh mục món nước thành phẩm)
CREATE TABLE ThanhPham (
    MaTP    VARCHAR(20)    PRIMARY KEY,
    TenTP   VARCHAR(100)   NOT NULL,
    DVT     VARCHAR(20)    NOT NULL,
    SLTon   INT            NOT NULL DEFAULT 0 CHECK (SLTon >= 0),
    GiaBan  NUMERIC(15, 2) NOT NULL DEFAULT 0 CHECK (GiaBan >= 0)
);

-- 3. Bảng Nguyên Vật Liệu (Danh mục nguyên vật liệu lưu kho)
CREATE TABLE NguyenVatLieu (
    MaNVL       VARCHAR(20)    PRIMARY KEY,
    TenNVL      VARCHAR(100)   NOT NULL,
    DVT         VARCHAR(20)    NOT NULL,
    SLTon       NUMERIC(12, 3) NOT NULL DEFAULT 0 CHECK (SLTon >= 0),
    DonGiaNhap  NUMERIC(15, 2) NOT NULL DEFAULT 0 CHECK (DonGiaNhap >= 0)
);

-- 4. Bảng Nhà Cung Cấp (Danh mục đối tác cung ứng NVL)
CREATE TABLE NhaCungCap (
    MaNCC   VARCHAR(20)  PRIMARY KEY,
    TenNCC  VARCHAR(150) NOT NULL,
    DiaChi  VARCHAR(255),
    SDT     VARCHAR(15),
    Email   VARCHAR(100)
);

-- ============================================================================
-- NHÓM 2: BẢNG ĐỊNH MỨC CÔNG THỨC (BOM)
-- ============================================================================

-- 5. Bảng Công Thức BOM (Định mức nguyên vật liệu cho 1 đơn vị thành phẩm)
CREATE TABLE CongThuc_BOM (
    MaTP       VARCHAR(20)    NOT NULL,
    MaNVL      VARCHAR(20)    NOT NULL,
    SLCanDung  NUMERIC(12, 3) NOT NULL CHECK (SLCanDung > 0),
    PRIMARY KEY (MaTP, MaNVL),
    CONSTRAINT fk_bom_thanhpham
        FOREIGN KEY (MaTP) REFERENCES ThanhPham(MaTP) ON DELETE CASCADE,
    CONSTRAINT fk_bom_nguyenvatlieu
        FOREIGN KEY (MaNVL) REFERENCES NguyenVatLieu(MaNVL) ON DELETE RESTRICT
);

-- ============================================================================
-- NHÓM 3: CỤM CHỨNG TỪ NHẬP KHO NGUYÊN VẬT LIỆU
-- ============================================================================

-- 6. Bảng Phiếu Nhập Kho NVL (Header)
CREATE TABLE PhieuNhapKhoNVL (
    MaPN        VARCHAR(20)    PRIMARY KEY,
    NgayLap     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    TongTien    NUMERIC(15, 2) NOT NULL DEFAULT 0 CHECK (TongTien >= 0),
    MaNCC       VARCHAR(20)    NOT NULL,
    MaNguoiLap  VARCHAR(20)    NOT NULL,
    CONSTRAINT fk_pn_nhacungcap
        FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC) ON DELETE RESTRICT,
    CONSTRAINT fk_pn_nguoilap
        FOREIGN KEY (MaNguoiLap) REFERENCES NguoiDung(MaND) ON DELETE RESTRICT
);

-- 7. Bảng Chi Tiết Phiếu Nhập Kho NVL (Detail)
CREATE TABLE CT_PhieuNhapKhoNVL (
    MaPN        VARCHAR(20)    NOT NULL,
    MaNVL       VARCHAR(20)    NOT NULL,
    SLNhap      NUMERIC(12, 3) NOT NULL CHECK (SLNhap > 0),
    DonGiaNhap  NUMERIC(15, 2) NOT NULL CHECK (DonGiaNhap >= 0),
    PRIMARY KEY (MaPN, MaNVL),
    CONSTRAINT fk_ctpn_phieunhap
        FOREIGN KEY (MaPN) REFERENCES PhieuNhapKhoNVL(MaPN) ON DELETE CASCADE,
    CONSTRAINT fk_ctpn_nguyenvatlieu
        FOREIGN KEY (MaNVL) REFERENCES NguyenVatLieu(MaNVL) ON DELETE RESTRICT
);

-- ============================================================================
-- NHÓM 4: CỤM LỆNH SẢN XUẤT & ĐỀ NGHỊ XUẤT KHO
-- ============================================================================

-- 8. Bảng Lệnh Sản Xuất (Header)
CREATE TABLE LenhSanXuat (
    MaLSX            VARCHAR(20) PRIMARY KEY,
    NgayLap          TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    NgayHT           TIMESTAMP   NULL,
    TrangThai        VARCHAR(30) NOT NULL DEFAULT 'ChoTiepNhan'
                     CHECK (TrangThai IN ('ChoTiepNhan', 'DangSanXuat', 'DaHoanThanh', 'DaHuy')),
    MaNguoiLap       VARCHAR(20) NOT NULL,
    MaNguoiPhuTrach  VARCHAR(20) NOT NULL,
    CONSTRAINT fk_lsx_nguoilap
        FOREIGN KEY (MaNguoiLap) REFERENCES NguoiDung(MaND) ON DELETE RESTRICT,
    CONSTRAINT fk_lsx_nguoiphutrach
        FOREIGN KEY (MaNguoiPhuTrach) REFERENCES NguoiDung(MaND) ON DELETE RESTRICT
);

-- 9. Bảng Chi Tiết Lệnh Sản Xuất (Detail)
CREATE TABLE CT_LenhSanXuat (
    MaLSX    VARCHAR(20) NOT NULL,
    MaTP     VARCHAR(20) NOT NULL,
    SLCanSX  INT         NOT NULL CHECK (SLCanSX > 0),
    PRIMARY KEY (MaLSX, MaTP),
    CONSTRAINT fk_ctlsx_lenhsanxuat
        FOREIGN KEY (MaLSX) REFERENCES LenhSanXuat(MaLSX) ON DELETE CASCADE,
    CONSTRAINT fk_ctlsx_thanhpham
        FOREIGN KEY (MaTP) REFERENCES ThanhPham(MaTP) ON DELETE RESTRICT
);

-- 10. Bảng Phiếu Đề Nghị Xuất NVL (Header)
CREATE TABLE PhieuDeNghiXuatNVL (
    MaPDN_NVL     VARCHAR(20) PRIMARY KEY,
    NgayLap       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    NgayDuyet     TIMESTAMP   NULL,
    TrangThai     VARCHAR(30) NOT NULL DEFAULT 'ChoDuyet'
                  CHECK (TrangThai IN ('ChoDuyet', 'DaDuyet', 'TuChoi')),
    MaLSX         VARCHAR(20) NOT NULL,
    MaNguoiLap    VARCHAR(20) NOT NULL,
    MaNguoiDuyet  VARCHAR(20) NULL,
    CONSTRAINT fk_pdnnvl_lenhsanxuat
        FOREIGN KEY (MaLSX) REFERENCES LenhSanXuat(MaLSX) ON DELETE RESTRICT,
    CONSTRAINT fk_pdnnvl_nguoilap
        FOREIGN KEY (MaNguoiLap) REFERENCES NguoiDung(MaND) ON DELETE RESTRICT,
    CONSTRAINT fk_pdnnvl_nguoiduyet
        FOREIGN KEY (MaNguoiDuyet) REFERENCES NguoiDung(MaND) ON DELETE RESTRICT
);

-- 11. Bảng Chi Tiết Phiếu Đề Nghị Xuất NVL (Detail)
CREATE TABLE CT_PhieuDeNghiXuatNVL (
    MaPDN_NVL  VARCHAR(20)    NOT NULL,
    MaNVL      VARCHAR(20)    NOT NULL,
    SLXuat     NUMERIC(12, 3) NOT NULL CHECK (SLXuat > 0),
    PRIMARY KEY (MaPDN_NVL, MaNVL),
    CONSTRAINT fk_ctpdnnvl_phieu
        FOREIGN KEY (MaPDN_NVL) REFERENCES PhieuDeNghiXuatNVL(MaPDN_NVL) ON DELETE CASCADE,
    CONSTRAINT fk_ctpdnnvl_nguyenvatlieu
        FOREIGN KEY (MaNVL) REFERENCES NguyenVatLieu(MaNVL) ON DELETE RESTRICT
);

-- 12. Bảng Phiếu Đề Nghị Xuất Thành Phẩm (Header)
CREATE TABLE PhieuDeNghiXuatTP (
    MaPDN_TP      VARCHAR(20) PRIMARY KEY,
    NgayLap       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    NgayDuyet     TIMESTAMP   NULL,
    TrangThai     VARCHAR(30) NOT NULL DEFAULT 'ChoDuyet'
                  CHECK (TrangThai IN ('ChoDuyet', 'DaDuyet', 'TuChoi')),
    MaNguoiLap    VARCHAR(20) NOT NULL,
    MaNguoiDuyet  VARCHAR(20) NULL,
    CONSTRAINT fk_pdntp_nguoilap
        FOREIGN KEY (MaNguoiLap) REFERENCES NguoiDung(MaND) ON DELETE RESTRICT,
    CONSTRAINT fk_pdntp_nguoiduyet
        FOREIGN KEY (MaNguoiDuyet) REFERENCES NguoiDung(MaND) ON DELETE RESTRICT
);

-- 13. Bảng Chi Tiết Phiếu Đề Nghị Xuất Thành Phẩm (Detail)
CREATE TABLE CT_PhieuDeNghiXuatTP (
    MaPDN_TP  VARCHAR(20) NOT NULL,
    MaTP      VARCHAR(20) NOT NULL,
    SLXuat    INT         NOT NULL CHECK (SLXuat > 0),
    PRIMARY KEY (MaPDN_TP, MaTP),
    CONSTRAINT fk_ctpdntp_phieu
        FOREIGN KEY (MaPDN_TP) REFERENCES PhieuDeNghiXuatTP(MaPDN_TP) ON DELETE CASCADE,
    CONSTRAINT fk_ctpdntp_thanhpham
        FOREIGN KEY (MaTP) REFERENCES ThanhPham(MaTP) ON DELETE RESTRICT
);