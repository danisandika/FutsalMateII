USE [master]
GO
/****** Object:  Database [FutsalMate]    Script Date: 15/8/2020 11:10:16 pm ******/
CREATE DATABASE [FutsalMate]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'FutsalMate', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\FutsalMate.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'FutsalMate_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\FutsalMate_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [FutsalMate] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [FutsalMate].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [FutsalMate] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [FutsalMate] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [FutsalMate] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [FutsalMate] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [FutsalMate] SET ARITHABORT OFF 
GO
ALTER DATABASE [FutsalMate] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [FutsalMate] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [FutsalMate] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [FutsalMate] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [FutsalMate] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [FutsalMate] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [FutsalMate] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [FutsalMate] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [FutsalMate] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [FutsalMate] SET  DISABLE_BROKER 
GO
ALTER DATABASE [FutsalMate] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [FutsalMate] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [FutsalMate] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [FutsalMate] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [FutsalMate] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [FutsalMate] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [FutsalMate] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [FutsalMate] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [FutsalMate] SET  MULTI_USER 
GO
ALTER DATABASE [FutsalMate] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [FutsalMate] SET DB_CHAINING OFF 
GO
ALTER DATABASE [FutsalMate] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [FutsalMate] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [FutsalMate] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [FutsalMate] SET QUERY_STORE = OFF
GO
USE [FutsalMate]
GO
/****** Object:  Table [dbo].[tb_admin]    Script Date: 15/8/2020 11:10:16 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_admin](
	[id_admin] [int] IDENTITY(1,1) NOT NULL,
	[nama] [varchar](50) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[notelp] [varchar](15) NULL,
	[password] [varchar](50) NOT NULL,
	[foto] [varchar](max) NULL,
	[alamat] [varchar](100) NULL,
	[tgl_lahir] [nchar](10) NULL,
	[status] [int] NOT NULL,
 CONSTRAINT [PK_tb_admin_1] PRIMARY KEY CLUSTERED 
(
	[id_admin] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_bank]    Script Date: 15/8/2020 11:10:16 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_bank](
	[id_bank] [int] IDENTITY(1,1) NOT NULL,
	[nama_bank] [varchar](15) NOT NULL,
	[no_rekening] [varchar](50) NOT NULL,
	[kode_bank] [varchar](10) NOT NULL,
	[nama_rekening] [varchar](50) NOT NULL,
	[status] [int] NOT NULL,
 CONSTRAINT [PK_tb_bank] PRIMARY KEY CLUSTERED 
(
	[id_bank] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_futsal]    Script Date: 15/8/2020 11:10:16 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_futsal](
	[id_futsal] [int] IDENTITY(1,1) NOT NULL,
	[nama_futsal] [varchar](100) NOT NULL,
	[alamat_futsal] [varchar](max) NOT NULL,
	[rating] [float] NULL,
	[deskripsi_futsal] [varchar](max) NULL,
	[notelp_futsal] [varchar](15) NULL,
	[fasilitas] [varchar](max) NULL,
	[gambar] [varchar](max) NULL,
	[latitude] [float] NULL,
	[longitude] [float] NULL,
	[nama_bank] [varchar](30) NOT NULL,
	[no_rekening] [varchar](50) NOT NULL,
	[nama_rekening] [varchar](50) NOT NULL,
	[status] [int] NOT NULL,
 CONSTRAINT [PK_tb_futsal] PRIMARY KEY CLUSTERED 
(
	[id_futsal] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_individumatch]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_individumatch](
	[id_matchteam] [varchar](20) NOT NULL,
	[id_pemain] [int] NOT NULL,
 CONSTRAINT [PK_tb_individumatch] PRIMARY KEY CLUSTERED 
(
	[id_matchteam] ASC,
	[id_pemain] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_konfirmasi]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_konfirmasi](
	[id_konfirmasi] [int] IDENTITY(1,1) NOT NULL,
	[id_pemesanan] [varchar](20) NOT NULL,
	[nama_pengirim] [varchar](50) NULL,
	[norek_pengirim] [varchar](50) NULL,
	[jumlah_bayar] [int] NULL,
	[foto_transfer] [varchar](max) NULL,
 CONSTRAINT [PK_tb_konfirmasi] PRIMARY KEY CLUSTERED 
(
	[id_konfirmasi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_lapangan]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_lapangan](
	[id_lapangan] [int] IDENTITY(1,1) NOT NULL,
	[nama_lapangan] [varchar](50) NOT NULL,
	[jenis_lapangan] [varchar](30) NOT NULL,
	[harga] [int] NOT NULL,
	[id_futsal] [int] NOT NULL,
	[gambar] [varchar](max) NULL,
	[status] [int] NOT NULL,
 CONSTRAINT [PK_tb_lapangan] PRIMARY KEY CLUSTERED 
(
	[id_lapangan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_matchteam]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_matchteam](
	[id_matchteam] [varchar](20) NOT NULL,
	[id_home_team] [int] NOT NULL,
	[id_away_team] [int] NULL,
	[id_pemesanan] [varchar](20) NOT NULL,
	[home_score] [int] NULL,
	[away_score] [int] NULL,
	[deskripsi] [varchar](max) NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_tb_matchteam] PRIMARY KEY CLUSTERED 
(
	[id_matchteam] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_pemain]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_pemain](
	[id_pemain] [int] IDENTITY(1,1) NOT NULL,
	[nama] [varchar](50) NOT NULL,
	[gender] [int] NULL,
	[alamat] [varchar](max) NULL,
	[notelp] [varchar](15) NULL,
	[email] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[id_team] [int] NULL,
 CONSTRAINT [PK_tb_pemain] PRIMARY KEY CLUSTERED 
(
	[id_pemain] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_pemesanan]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_pemesanan](
	[id_pemesanan] [varchar](20) NOT NULL,
	[id_lapangan] [int] NOT NULL,
	[id_pemain] [int] NOT NULL,
	[tgl_pemesanan] [date] NOT NULL,
	[tgl_main] [date] NOT NULL,
	[jam_main_mulai] [datetime] NULL,
	[jam_main_selesai] [datetime] NULL,
	[durasi] [int] NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_tb_pemesanan] PRIMARY KEY CLUSTERED 
(
	[id_pemesanan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_pengelola]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_pengelola](
	[id_pengelola] [int] IDENTITY(1,1) NOT NULL,
	[nama] [varchar](50) NOT NULL,
	[notelp] [varchar](15) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[id_futsal] [int] NULL,
	[alamat] [varchar](max) NULL,
	[tgl_lahir] [date] NULL,
	[foto_ktp] [varchar](max) NOT NULL,
	[foto] [varchar](max) NULL,
	[status] [int] NOT NULL,
 CONSTRAINT [PK_tb_admin] PRIMARY KEY CLUSTERED 
(
	[id_pengelola] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_rating]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_rating](
	[id_rating] [int] IDENTITY(1,1) NOT NULL,
	[rating] [float] NOT NULL,
	[komentar] [varchar](max) NULL,
	[id_futsal] [int] NOT NULL,
	[id_pemain] [int] NOT NULL,
 CONSTRAINT [PK_tb_rating] PRIMARY KEY CLUSTERED 
(
	[id_rating] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_sewalapangan]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_sewalapangan](
	[id_sewalapangan] [varchar](20) NOT NULL,
	[id_futsal] [int] NOT NULL,
	[tgl_pembayaran] [date] NULL,
	[tgl_sewa] [date] NULL,
	[tgl_berakhir] [date] NULL,
	[waktu_sewa] [int] NULL,
	[jumlah_uang] [int] NULL,
	[status_bayar] [int] NOT NULL,
	[id_bank] [int] NOT NULL,
	[id_admin] [int] NULL,
	[bukti_transfer] [varchar](max) NULL,
 CONSTRAINT [PK_tb_sewalapangan] PRIMARY KEY CLUSTERED 
(
	[id_sewalapangan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_team]    Script Date: 15/8/2020 11:10:17 pm ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_team](
	[id_team] [int] IDENTITY(1,1) NOT NULL,
	[nama_team] [varchar](50) NOT NULL,
	[deskripsi] [varchar](max) NULL,
	[logo] [varchar](max) NULL,
	[captain] [int] NOT NULL,
	[win] [int] NOT NULL,
	[lose] [int] NOT NULL,
	[rate] [float] NOT NULL,
 CONSTRAINT [PK_tb_team] PRIMARY KEY CLUSTERED 
(
	[id_team] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[tb_individumatch]  WITH CHECK ADD  CONSTRAINT [FK_tb_individumatch_tb_individumatch] FOREIGN KEY([id_matchteam])
REFERENCES [dbo].[tb_matchteam] ([id_matchteam])
GO
ALTER TABLE [dbo].[tb_individumatch] CHECK CONSTRAINT [FK_tb_individumatch_tb_individumatch]
GO
ALTER TABLE [dbo].[tb_individumatch]  WITH CHECK ADD  CONSTRAINT [FK_tb_individumatch_tb_pemain] FOREIGN KEY([id_pemain])
REFERENCES [dbo].[tb_pemain] ([id_pemain])
GO
ALTER TABLE [dbo].[tb_individumatch] CHECK CONSTRAINT [FK_tb_individumatch_tb_pemain]
GO
ALTER TABLE [dbo].[tb_konfirmasi]  WITH CHECK ADD  CONSTRAINT [FK_tb_konfirmasi_tb_pemesanan] FOREIGN KEY([id_pemesanan])
REFERENCES [dbo].[tb_pemesanan] ([id_pemesanan])
GO
ALTER TABLE [dbo].[tb_konfirmasi] CHECK CONSTRAINT [FK_tb_konfirmasi_tb_pemesanan]
GO
ALTER TABLE [dbo].[tb_lapangan]  WITH CHECK ADD  CONSTRAINT [FK_tb_lapangan_tb_futsal] FOREIGN KEY([id_futsal])
REFERENCES [dbo].[tb_futsal] ([id_futsal])
GO
ALTER TABLE [dbo].[tb_lapangan] CHECK CONSTRAINT [FK_tb_lapangan_tb_futsal]
GO
ALTER TABLE [dbo].[tb_matchteam]  WITH CHECK ADD  CONSTRAINT [FK_tb_matchteam_tb_pemesanan] FOREIGN KEY([id_pemesanan])
REFERENCES [dbo].[tb_pemesanan] ([id_pemesanan])
GO
ALTER TABLE [dbo].[tb_matchteam] CHECK CONSTRAINT [FK_tb_matchteam_tb_pemesanan]
GO
ALTER TABLE [dbo].[tb_matchteam]  WITH CHECK ADD  CONSTRAINT [FK_tb_matchteam_tb_team] FOREIGN KEY([id_home_team])
REFERENCES [dbo].[tb_team] ([id_team])
GO
ALTER TABLE [dbo].[tb_matchteam] CHECK CONSTRAINT [FK_tb_matchteam_tb_team]
GO
ALTER TABLE [dbo].[tb_matchteam]  WITH CHECK ADD  CONSTRAINT [FK_tb_matchteam_tb_team1] FOREIGN KEY([id_away_team])
REFERENCES [dbo].[tb_team] ([id_team])
GO
ALTER TABLE [dbo].[tb_matchteam] CHECK CONSTRAINT [FK_tb_matchteam_tb_team1]
GO
ALTER TABLE [dbo].[tb_pemain]  WITH CHECK ADD  CONSTRAINT [FK_tb_pemain_tb_team] FOREIGN KEY([id_team])
REFERENCES [dbo].[tb_team] ([id_team])
GO
ALTER TABLE [dbo].[tb_pemain] CHECK CONSTRAINT [FK_tb_pemain_tb_team]
GO
ALTER TABLE [dbo].[tb_pemesanan]  WITH CHECK ADD  CONSTRAINT [FK_tb_pemesanan_tb_lapangan] FOREIGN KEY([id_lapangan])
REFERENCES [dbo].[tb_lapangan] ([id_lapangan])
GO
ALTER TABLE [dbo].[tb_pemesanan] CHECK CONSTRAINT [FK_tb_pemesanan_tb_lapangan]
GO
ALTER TABLE [dbo].[tb_pemesanan]  WITH CHECK ADD  CONSTRAINT [FK_tb_pemesanan_tb_pemain] FOREIGN KEY([id_pemain])
REFERENCES [dbo].[tb_pemain] ([id_pemain])
GO
ALTER TABLE [dbo].[tb_pemesanan] CHECK CONSTRAINT [FK_tb_pemesanan_tb_pemain]
GO
ALTER TABLE [dbo].[tb_pengelola]  WITH CHECK ADD  CONSTRAINT [FK_tb_admin_tb_futsal] FOREIGN KEY([id_futsal])
REFERENCES [dbo].[tb_futsal] ([id_futsal])
GO
ALTER TABLE [dbo].[tb_pengelola] CHECK CONSTRAINT [FK_tb_admin_tb_futsal]
GO
ALTER TABLE [dbo].[tb_rating]  WITH CHECK ADD  CONSTRAINT [FK_tb_rating_tb_futsal] FOREIGN KEY([id_futsal])
REFERENCES [dbo].[tb_futsal] ([id_futsal])
GO
ALTER TABLE [dbo].[tb_rating] CHECK CONSTRAINT [FK_tb_rating_tb_futsal]
GO
ALTER TABLE [dbo].[tb_rating]  WITH CHECK ADD  CONSTRAINT [FK_tb_rating_tb_pemain] FOREIGN KEY([id_pemain])
REFERENCES [dbo].[tb_pemain] ([id_pemain])
GO
ALTER TABLE [dbo].[tb_rating] CHECK CONSTRAINT [FK_tb_rating_tb_pemain]
GO
ALTER TABLE [dbo].[tb_sewalapangan]  WITH CHECK ADD  CONSTRAINT [FK_tb_sewalapangan_tb_admin] FOREIGN KEY([id_admin])
REFERENCES [dbo].[tb_admin] ([id_admin])
GO
ALTER TABLE [dbo].[tb_sewalapangan] CHECK CONSTRAINT [FK_tb_sewalapangan_tb_admin]
GO
ALTER TABLE [dbo].[tb_sewalapangan]  WITH CHECK ADD  CONSTRAINT [FK_tb_sewalapangan_tb_bank] FOREIGN KEY([id_bank])
REFERENCES [dbo].[tb_bank] ([id_bank])
GO
ALTER TABLE [dbo].[tb_sewalapangan] CHECK CONSTRAINT [FK_tb_sewalapangan_tb_bank]
GO
ALTER TABLE [dbo].[tb_sewalapangan]  WITH CHECK ADD  CONSTRAINT [FK_tb_sewalapangan_tb_futsal] FOREIGN KEY([id_futsal])
REFERENCES [dbo].[tb_futsal] ([id_futsal])
GO
ALTER TABLE [dbo].[tb_sewalapangan] CHECK CONSTRAINT [FK_tb_sewalapangan_tb_futsal]
GO
ALTER TABLE [dbo].[tb_team]  WITH CHECK ADD  CONSTRAINT [FK_tb_team_tb_pemain] FOREIGN KEY([captain])
REFERENCES [dbo].[tb_pemain] ([id_pemain])
GO
ALTER TABLE [dbo].[tb_team] CHECK CONSTRAINT [FK_tb_team_tb_pemain]
GO
USE [master]
GO
ALTER DATABASE [FutsalMate] SET  READ_WRITE 
GO
