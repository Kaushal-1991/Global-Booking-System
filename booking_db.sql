-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.44 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.14.0.7165
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for booking_db
DROP DATABASE IF EXISTS `booking_db`;
CREATE DATABASE IF NOT EXISTS `booking_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `booking_db`;

-- Dumping structure for table booking_db.bookings
DROP TABLE IF EXISTS `bookings`;
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booked_at` datetime(6) DEFAULT NULL,
  `offering_id` bigint DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKq5jwjdofj19iljaw9inctvtjw` (`parent_id`,`offering_id`),
  KEY `FKtml5idnh175vbojv4im277o65` (`offering_id`),
  CONSTRAINT `FKaecfd3hqewy5mxapp649idmtc` FOREIGN KEY (`parent_id`) REFERENCES `parents` (`id`),
  CONSTRAINT `FKtml5idnh175vbojv4im277o65` FOREIGN KEY (`offering_id`) REFERENCES `offerings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table booking_db.bookings: ~1 rows (approximately)
INSERT INTO `bookings` (`id`, `booked_at`, `offering_id`, `parent_id`) VALUES
	(1, '2026-05-30 09:51:06.966525', 1, 1);

-- Dumping structure for table booking_db.courses
DROP TABLE IF EXISTS `courses`;
CREATE TABLE IF NOT EXISTS `courses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table booking_db.courses: ~1 rows (approximately)
INSERT INTO `courses` (`id`, `course_name`, `description`) VALUES
	(1, 'Python Coding', 'Python for Kids');

-- Dumping structure for table booking_db.offerings
DROP TABLE IF EXISTS `offerings`;
CREATE TABLE IF NOT EXISTS `offerings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `batch_name` varchar(255) DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9a5vdg8tisn9w8y99l3siari` (`course_id`),
  KEY `FKmbhw975o0n1vxeik3o5cl81ee` (`teacher_id`),
  CONSTRAINT `FK9a5vdg8tisn9w8y99l3siari` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKmbhw975o0n1vxeik3o5cl81ee` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table booking_db.offerings: ~1 rows (approximately)
INSERT INTO `offerings` (`id`, `batch_name`, `course_id`, `teacher_id`) VALUES
	(1, 'Saturday Batch', 1, 1);

-- Dumping structure for table booking_db.parents
DROP TABLE IF EXISTS `parents`;
CREATE TABLE IF NOT EXISTS `parents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table booking_db.parents: ~1 rows (approximately)
INSERT INTO `parents` (`id`, `email`, `name`, `timezone`) VALUES
	(1, 'raj@gmail.com', 'Raj', 'Asia/Kolkata');

-- Dumping structure for table booking_db.sessions
DROP TABLE IF EXISTS `sessions`;
CREATE TABLE IF NOT EXISTS `sessions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_time_utc` datetime(6) DEFAULT NULL,
  `start_time_utc` datetime(6) DEFAULT NULL,
  `offering_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsndhaye4u4wuw0w2a6j4q7ahm` (`offering_id`),
  CONSTRAINT `FKsndhaye4u4wuw0w2a6j4q7ahm` FOREIGN KEY (`offering_id`) REFERENCES `offerings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table booking_db.sessions: ~2 rows (approximately)
INSERT INTO `sessions` (`id`, `end_time_utc`, `start_time_utc`, `offering_id`) VALUES
	(1, '2026-06-07 23:00:00.000000', '2026-06-07 22:00:00.000000', 1),
	(2, '2026-06-14 23:00:00.000000', '2026-06-14 22:00:00.000000', 1);

-- Dumping structure for table booking_db.teachers
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE IF NOT EXISTS `teachers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table booking_db.teachers: ~1 rows (approximately)
INSERT INTO `teachers` (`id`, `name`, `timezone`, `email`) VALUES
	(1, 'John', 'America/New_York', 'john@gmail.com');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
