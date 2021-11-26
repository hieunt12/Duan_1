USE [master]
GO
/****** Object:  Database [DuAn1]    Script Date: 11/18/2021 6:47:19 PM ******/
CREATE DATABASE [DuAn1]



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
/****** Object:  Table [dbo].[DocGia]    Script Date: 11/18/2021 6:47:19 PM ******/
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
/****** Object:  Table [dbo].[NhanVien]    Script Date: 11/18/2021 6:47:19 PM ******/
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
/****** Object:  Table [dbo].[PhieuMuon]    Script Date: 11/18/2021 6:47:19 PM ******/
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
 CONSTRAINT [PK_PhieuMuon] PRIMARY KEY CLUSTERED 
(
	[maPM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuMuon_PhieuTra]    Script Date: 11/18/2021 6:47:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuMuon_PhieuTra](
	[MaPM] [int] NOT NULL,
	[MaPT] [int] NOT NULL,
	[ID] [int] NOT NULL,
 CONSTRAINT [PK_PhieuMuon_PhieuTra] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuMuonCT]    Script Date: 11/18/2021 6:47:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuMuonCT](
	[MaPM] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[TrangThai] [bit] NULL,
	[ghiChu] [nvarchar](255) NULL,
 CONSTRAINT [PK_PhieuMuonCT] PRIMARY KEY CLUSTERED 
(
	[MaPM] ASC,
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuTra]    Script Date: 11/18/2021 6:47:19 PM ******/
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
/****** Object:  Table [dbo].[PhieuTraCT]    Script Date: 11/18/2021 6:47:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuTraCT](
	[maPT] [int] NOT NULL,
	[MaSach] [int] NOT NULL,
	[GiaMuon] [float] NULL,
	[TinhTrang] [nvarchar](50) NULL,
	[tienPhat] [float] NULL,
 CONSTRAINT [PK_PhieuTraCT] PRIMARY KEY CLUSTERED 
(
	[maPT] ASC,
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sach]    Script Date: 11/18/2021 6:47:19 PM ******/
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
 CONSTRAINT [PK_Sach] PRIMARY KEY CLUSTERED 
(
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TheLoai]    Script Date: 11/18/2021 6:47:19 PM ******/
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
/****** Object:  Table [dbo].[TheThuVien]    Script Date: 11/18/2021 6:47:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TheThuVien](
	[MaThe] [int] IDENTITY(1,1) NOT NULL,
	[MaDG] [int] NOT NULL,
	[NgayCap] [date] NULL,
	[Ngayhethan] [date] NULL,
	[TinhTrang] [nvarchar](50) NULL,
 CONSTRAINT [PK_TheThuVien] PRIMARY KEY CLUSTERED 
(
	[MaThe] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
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
ALTER TABLE [dbo].[PhieuTraCT]  WITH CHECK ADD  CONSTRAINT [FK_PhieuTraCT_PhieuMuon_PhieuTra] FOREIGN KEY([maPT])
REFERENCES [dbo].[PhieuMuon_PhieuTra] ([ID])
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
USE [master]
GO
ALTER DATABASE [DuAn1] SET  READ_WRITE 
GO


insert into NhanVien values('NV01',N'Nguyễn Văn Đức','0987547352','Ducnv123','06-12-2002','0','030402667334','ducnv@gmail.com',N'Thanh Hoá','0'),
							('NV02',N'Lê Văn Tráng','0967973101','Tranglv123','03-04-2002','0','040202012299','levantrang4302@gmail.com',N'Nghệ An','0'),
							('NV03',N'Nguyễn Trung Hiếu','0968281639','Hieunt123','04-11-2002','0','040203226384','hieunt@gmail.com',N'Cà Mau','0'),
							('NV04',N'Nguyễn Duy Khanh','0972538463','Khanhnd123','11-06-2002','0','030405016384','khanhnd@gmail.com',N'Long An','0'),
							('NV05',N'Đỗ Văn Trường','0983618465','Truongdv123','12-12-2002','0','040538264859','truongdv@gmail.com',N'Phú Thọ','0')


insert into DocGia values(N'Hoàng Văn Dương','12-04-2004','040327485736','duonghv@gmail.com','0','09273547249',N'Đà Nẵng','NV01'),
						(N'Nguyễn Thị Trà My','10-06-2003','042648254966','tramynt@gmail.com','1','0937352846',N'Hà Tĩnh','NV03'),
						(N'Dương Văn Phúc','09-04-2002','040572658423','duonghv@gmail.com','0','0936485628',N'Đà Nẵng','NV01')


insert into TheThuVien values('1','10-10-2021','11-10-2021',N'Bình thường'),
							('2','09-04-2021','10-04-2021',N'Bình thường'),
							('3','11-10-2021','12-10-2021',N'Bình thường')

delete TheLoai
insert into TheLoai values(N'Tài liệu học tập'),
						(N'Ẩm thực'),
						(N'Kinh Tế'),
						(N'Truyện'),
						(N'Kỹ năng sống')

delete Sach
insert into Sach values(N'Những Món Cơm Ngon Đặc Sắc','103','26000','10-06-2019',N'Bình thường','2',N'Kim Đồng'),
						(N'Đại Số Lớp 12','158','32000','12-07-2020',N'Bình thường','1',N'Giáo Dục'),
						(N'Ngữ Văn Lớp 12','236','44000','12-07-2020',N'Bình thường','1',N'Giáo Dục'),
						(N'Tử Huyệt Cảm Xúc','316','108000','07-12-2021',N'Bình thường','5',N'Tuổi Trẻ'),
						(N'Vĩ Đại Do Lựa Chọn','231','76000','05-02-2020',N'Bình thường','3',N'Lao Động')


insert into PhieuMuon values('NV01','10-12-2021','1','7'),
							('NV04','11-13-2021','2','5'),
							('NV02','10-12-2021','3','7'),
							('NV03','09-15-2021','2','7'),
							('NV05','11-02-2021','3','7')


insert into PhieuMuonCT values('1','1','0',N'Bình thường'),
							('2','2','0',N'Bình thường'),
							('3','3','1',N'Bình thường'),
							('4','3','0',N'Bình thường'),
							('5','5','1',N'Bình thường')


insert into PhieuTra values('NV01','1','10-19-2021'),
							('NV04','2','11-18-2021'),
							('NV02','3','10-21-2021'),
							('NV03','2','10-22-2021'),
							('NV05','3','10-09-2021')


insert into PhieuTraCT values('1','1','7000',N'Bình thường','0'),
							('2','2','7000',N'Bình thường','0'),
							('3','3','7000',N'Quá hạn trả 3 ngày','15000'),
							('4','4','7000',N'Bình thường','0'),
							('5','5','7000',N'Hỏng sách','76000')

insert into PhieuMuon_PhieuTra
values('1','1','1'),
		('2','2','2'),
		('3','3','3'),
		('4','4','4'),
		('5','5','5')


select * from NhanVien
select * from DocGia
select * from TheThuVien
select * from TheLoai
select * from Sach
select * from PhieuMuon
select * from PhieuMuonCT
select * from PhieuTra
select * from PhieuTraCT
select * from PhieuMuon_PhieuTra