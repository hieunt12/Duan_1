USE [master]
GO
/****** Object:  Database [DuAn1]    Script Date: 12/9/2021 5:02:07 PM ******/
CREATE DATABASE [DuAn1]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DuAn1', FILENAME = N'D:\document\MSSQL15.SQLEXPRESS\MSSQL\DATA\DuAn1.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DuAn1_log', FILENAME = N'D:\document\MSSQL15.SQLEXPRESS\MSSQL\DATA\DuAn1_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [DuAn1] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DuAn1].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DuAn1] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DuAn1] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DuAn1] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DuAn1] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DuAn1] SET ARITHABORT OFF 
GO
ALTER DATABASE [DuAn1] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DuAn1] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DuAn1] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DuAn1] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DuAn1] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DuAn1] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DuAn1] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DuAn1] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DuAn1] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DuAn1] SET  DISABLE_BROKER 
GO
ALTER DATABASE [DuAn1] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DuAn1] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DuAn1] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DuAn1] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DuAn1] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DuAn1] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DuAn1] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DuAn1] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [DuAn1] SET  MULTI_USER 
GO
ALTER DATABASE [DuAn1] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DuAn1] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DuAn1] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DuAn1] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DuAn1] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DuAn1] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [DuAn1] SET QUERY_STORE = OFF
GO
USE [DuAn1]
GO
/****** Object:  Table [dbo].[DocGia]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DocGia](
	[MaDG] [int] IDENTITY(1,1) NOT NULL,
	[TenDG] [nvarchar](50) NULL,
	[NgaySinh] [date] NULL,
	[CCCD] [varchar](12) NULL,
	[Email] [nvarchar](50) NULL,
	[gioitinh] [bit] NULL,
	[SDT] [varchar](11) NULL,
	[DiaChi] [nvarchar](50) NULL,
	[MaNV] [varchar](50) NOT NULL,
 CONSTRAINT [PK_DocGia] PRIMARY KEY CLUSTERED 
(
	[MaDG] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[maNV] [varchar](50) NOT NULL,
	[TenNV] [nvarchar](50) NULL,
	[SDT] [varchar](11) NULL,
	[matkhau] [varchar](50) NULL,
	[ngaySinh] [date] NULL,
	[vaitro] [bit] NULL,
	[CCCD] [varchar](12) NULL,
	[Email] [varchar](50) NULL,
	[DiaChi] [nvarchar](50) NULL,
	[Gioitinh] [bit] NULL,
 CONSTRAINT [PK_NhanVien] PRIMARY KEY CLUSTERED 
(
	[maNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuMuon]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuMuon](
	[maPM] [int] IDENTITY(1,1) NOT NULL,
	[MaNV] [varchar](50) NOT NULL,
	[NgayMuon] [date] NULL,
	[MaThe] [int] NULL,
	[SoNgayMuon] [int] NULL,
	[TrangThai] [bit] NULL,
	[TienDatCoc] [float] NULL,
 CONSTRAINT [PK_PhieuMuon] PRIMARY KEY CLUSTERED 
(
	[maPM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuMuon_PhieuTra]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuMuon_PhieuTra](
	[MaPM] [int] NOT NULL,
	[MaPT] [int] NOT NULL,
 CONSTRAINT [PK_PhieuMuon_PhieuTra_1] PRIMARY KEY CLUSTERED 
(
	[MaPM] ASC,
	[MaPT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuMuonCT]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuMuonCT](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MaPM] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[TrangThai] [bit] NULL,
	[ghiChu] [nvarchar](255) NULL,
 CONSTRAINT [PK_PhieuMuonCT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuTra]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuTra](
	[MaPT] [int] IDENTITY(1,1) NOT NULL,
	[MaNV] [varchar](50) NULL,
	[MaThe] [int] NULL,
	[NgayThucTra] [date] NULL,
 CONSTRAINT [PK_PhieuTra] PRIMARY KEY CLUSTERED 
(
	[MaPT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuTraCT]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuTraCT](
	[maPT] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[TinhTrang] [nvarchar](50) NULL,
	[tienPhat] [float] NULL,
	[Maphieumuon] [int] NULL,
	[ID] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_PhieuTraCT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sach]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach](
	[MaSach] [int] IDENTITY(1,1) NOT NULL,
	[TenSach] [nvarchar](50) NULL,
	[SoTrang] [int] NULL,
	[Gia] [float] NULL,
	[NgayNhap] [date] NULL,
	[TinhTrang] [nvarchar](50) NULL,
	[MaTL] [int] NULL,
	[NXB] [nvarchar](50) NULL,
	[GiaMuon] [float] NULL,
	[QRCode] [varchar](50) NULL,
 CONSTRAINT [PK_Sach] PRIMARY KEY CLUSTERED 
(
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TheLoai]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TheLoai](
	[maTL] [int] IDENTITY(1,1) NOT NULL,
	[TenTL] [nvarchar](50) NULL,
 CONSTRAINT [PK_TheLoai] PRIMARY KEY CLUSTERED 
(
	[maTL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TheThuVien]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TheThuVien](
	[MaThe] [int] IDENTITY(1,1) NOT NULL,
	[MaDG] [int] NOT NULL,
	[NgayCap] [date] NULL,
	[Ngayhethan] [date] NULL,
	[Hinh] [nvarchar](50) NULL,
	[TinhTrang] [bit] NULL,
	[Solanmuon] [int] NULL,
 CONSTRAINT [PK_TheThuVien] PRIMARY KEY CLUSTERED 
(
	[MaThe] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[PhieuMuon] ADD  CONSTRAINT [DF_PhieuMuon_NgayMuon]  DEFAULT (getdate()) FOR [NgayMuon]
GO
ALTER TABLE [dbo].[PhieuMuon] ADD  CONSTRAINT [DF_PhieuMuon_TrangThai]  DEFAULT ((0)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[PhieuMuonCT] ADD  CONSTRAINT [DF_PhieuMuonCT_TrangThai]  DEFAULT ((0)) FOR [TrangThai]
GO
ALTER TABLE [dbo].[PhieuMuonCT] ADD  CONSTRAINT [DF_PhieuMuonCT_ghiChu]  DEFAULT (N'Bình thường') FOR [ghiChu]
GO
ALTER TABLE [dbo].[TheThuVien] ADD  CONSTRAINT [DF_TheThuVien_TinhTrang]  DEFAULT ((1)) FOR [TinhTrang]
GO
ALTER TABLE [dbo].[TheThuVien] ADD  CONSTRAINT [DF_TheThuVien_Solanmuon]  DEFAULT ((3)) FOR [Solanmuon]
GO
ALTER TABLE [dbo].[DocGia]  WITH CHECK ADD  CONSTRAINT [FK_DocGia_NhanVien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[DocGia] CHECK CONSTRAINT [FK_DocGia_NhanVien]
GO
ALTER TABLE [dbo].[PhieuMuon]  WITH CHECK ADD  CONSTRAINT [FK_PhieuMuon_NhanVien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[PhieuMuon] CHECK CONSTRAINT [FK_PhieuMuon_NhanVien]
GO
ALTER TABLE [dbo].[PhieuMuon]  WITH CHECK ADD  CONSTRAINT [FK_PhieuMuon_TheThuVien] FOREIGN KEY([MaThe])
REFERENCES [dbo].[TheThuVien] ([MaThe])
GO
ALTER TABLE [dbo].[PhieuMuon] CHECK CONSTRAINT [FK_PhieuMuon_TheThuVien]
GO
ALTER TABLE [dbo].[PhieuMuon_PhieuTra]  WITH CHECK ADD  CONSTRAINT [FK_PhieuMuon_PhieuTra_PhieuMuon] FOREIGN KEY([MaPM])
REFERENCES [dbo].[PhieuMuon] ([maPM])
GO
ALTER TABLE [dbo].[PhieuMuon_PhieuTra] CHECK CONSTRAINT [FK_PhieuMuon_PhieuTra_PhieuMuon]
GO
ALTER TABLE [dbo].[PhieuMuon_PhieuTra]  WITH CHECK ADD  CONSTRAINT [FK_PhieuMuon_PhieuTra_PhieuTra] FOREIGN KEY([MaPT])
REFERENCES [dbo].[PhieuTra] ([MaPT])
GO
ALTER TABLE [dbo].[PhieuMuon_PhieuTra] CHECK CONSTRAINT [FK_PhieuMuon_PhieuTra_PhieuTra]
GO
ALTER TABLE [dbo].[PhieuMuonCT]  WITH CHECK ADD  CONSTRAINT [FK_PhieuMuonCT_PhieuMuon] FOREIGN KEY([MaPM])
REFERENCES [dbo].[PhieuMuon] ([maPM])
GO
ALTER TABLE [dbo].[PhieuMuonCT] CHECK CONSTRAINT [FK_PhieuMuonCT_PhieuMuon]
GO
ALTER TABLE [dbo].[PhieuMuonCT]  WITH CHECK ADD  CONSTRAINT [FK_PhieuMuonCT_Sach] FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaSach])
GO
ALTER TABLE [dbo].[PhieuMuonCT] CHECK CONSTRAINT [FK_PhieuMuonCT_Sach]
GO
ALTER TABLE [dbo].[PhieuTra]  WITH CHECK ADD  CONSTRAINT [FK_PhieuTra_NhanVien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[PhieuTra] CHECK CONSTRAINT [FK_PhieuTra_NhanVien]
GO
ALTER TABLE [dbo].[PhieuTra]  WITH CHECK ADD  CONSTRAINT [FK_PhieuTra_TheThuVien] FOREIGN KEY([MaThe])
REFERENCES [dbo].[TheThuVien] ([MaThe])
GO
ALTER TABLE [dbo].[PhieuTra] CHECK CONSTRAINT [FK_PhieuTra_TheThuVien]
GO
ALTER TABLE [dbo].[PhieuTraCT]  WITH CHECK ADD  CONSTRAINT [FK_PhieuTraCT_PhieuMuon_PhieuTra] FOREIGN KEY([Maphieumuon], [maPT])
REFERENCES [dbo].[PhieuMuon_PhieuTra] ([MaPM], [MaPT])
GO
ALTER TABLE [dbo].[PhieuTraCT] CHECK CONSTRAINT [FK_PhieuTraCT_PhieuMuon_PhieuTra]
GO
ALTER TABLE [dbo].[PhieuTraCT]  WITH CHECK ADD  CONSTRAINT [FK_PhieuTraCT_Sach] FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaSach])
GO
ALTER TABLE [dbo].[PhieuTraCT] CHECK CONSTRAINT [FK_PhieuTraCT_Sach]
GO
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD  CONSTRAINT [FK_Sach_TheLoai] FOREIGN KEY([MaTL])
REFERENCES [dbo].[TheLoai] ([maTL])
GO
ALTER TABLE [dbo].[Sach] CHECK CONSTRAINT [FK_Sach_TheLoai]
GO
ALTER TABLE [dbo].[TheThuVien]  WITH CHECK ADD  CONSTRAINT [FK_TheThuVien_DocGia] FOREIGN KEY([MaDG])
REFERENCES [dbo].[DocGia] ([MaDG])
GO
ALTER TABLE [dbo].[TheThuVien] CHECK CONSTRAINT [FK_TheThuVien_DocGia]
GO
/****** Object:  StoredProcedure [dbo].[DoanhThu]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[DoanhThu] @nam int as
begin

select MONTH(NgayThucTra) as Thang,
       SUM(GiaMuon) as giamuon,
	   SUM(tienPhat) as Tienphat,
	   SUM(GiaMuon)+SUM(tienPhat) as TongTien
	   from PhieuTra join PhieuTraCT on PhieuTra.MaPT = PhieuTraCT.maPT
                                         join Sach on PhieuTraCT.MaSach = Sach.MaSach
where YEAR(NgayThucTra) = @nam
group by  MONTH(NgayThucTra)
end
GO
/****** Object:  StoredProcedure [dbo].[TKDocGia]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[TKDocGia] as
begin
select DocGia.MaDG,
       TenDG,
	   TheThuVien.MaThe,
	   COUNT(PhieuMuonCT.MaSach) as SoSachMuon
                     from DocGia join TheThuVien on DocGia.MaDG = TheThuVien.MaDG
                     join PhieuMuon on TheThuVien.MaThe = PhieuMuon.MaThe
					  join PhieuMuonCT on PhieuMuon.maPM = PhieuMuonCT.MaPM 
  group by DocGia.MaDG,
       TenDG,
	   TheThuVien.MaThe
end
GO
/****** Object:  StoredProcedure [dbo].[TKSach]    Script Date: 12/9/2021 5:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[TKSach] as
begin
select Sach.MaSach,TenSach,NXB,COUNT(PhieuMuonCT.MaSach) as SoLanMUon
       from Sach join PhieuMuonCT on Sach.MaSach = PhieuMuonCT.MaSach 
	   group by Sach.MaSach,TenSach,NXB
end
GO
USE [master]
GO
ALTER DATABASE [DuAn1] SET  READ_WRITE 
GO
