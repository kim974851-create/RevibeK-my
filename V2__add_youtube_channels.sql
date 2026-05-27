-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: kpop_radio
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `youtube_channels`
--

DROP TABLE IF EXISTS `youtube_channels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `youtube_channels` (
  `id` char(36) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT (uuid()),
  `url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `channel_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `channel_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `uploads_playlist` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subscriber_count` bigint DEFAULT NULL,
  `last_checked_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `channel_id` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='유튜브 채널 목록';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `youtube_channels`
--

LOCK TABLES `youtube_channels` WRITE;
/*!40000 ALTER TABLE `youtube_channels` DISABLE KEYS */;
INSERT INTO `youtube_channels` VALUES ('fbd95066-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@HarmoVerse',NULL,'@HarmoVerse',NULL,NULL,NULL,'2026-05-27 15:55:02'),('fbd95632-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@chenzie1004','UCl-mHvTirLyYXEBoHzyVgLA','chenzie','UUl-mHvTirLyYXEBoHzyVgLA',6200,NULL,'2026-05-27 15:55:02'),('fbd96533-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@THE_aIDOL','UCQCTSiEbB8c0MAXlr5NhtQA','aIDOL','UUQCTSiEbB8c0MAXlr5NhtQA',11100,NULL,'2026-05-27 15:55:02'),('fbd966b4-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@EQUINOX.entertainment','UCyJGK3bJjqsILlBDX5spswQ','EQUINOX Entertainment','UUyJGK3bJjqsILlBDX5spswQ',3180,NULL,'2026-05-27 15:55:02'),('fbd96749-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@Dir.Fevernova','UCGP6GoJgmluyOITsI1HsrVQ','Dir. Fevernova','UUGP6GoJgmluyOITsI1HsrVQ',858,NULL,'2026-05-27 15:55:02'),('fbd967b5-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/channel/UCkVBepcU7jd1LjgKKfjz83Q','UCkVBepcU7jd1LjgKKfjz83Q','ERRDAY GROOVE (얼데이그루브)','UUkVBepcU7jd1LjgKKfjz83Q',60800,NULL,'2026-05-27 15:55:02'),('fbd9685f-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@A_I_Go','UC3UzxqqweD9ajoEue923V5w','إيهَم ','UU3UzxqqweD9ajoEue923V5w',0,NULL,'2026-05-27 15:55:02'),('fbd968e9-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@DALLASTUDIOS','UCoc_xlqn3sddicPBNMKj9Tw','DALLA STUDIOS','UUoc_xlqn3sddicPBNMKj9Tw',759,NULL,'2026-05-27 15:55:02'),('fbd96968-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/channel/UC4woL-HW4aiHLp4AjyxS7Og','UC4woL-HW4aiHLp4AjyxS7Og','CareBoys','UU4woL-HW4aiHLp4AjyxS7Og',415,NULL,'2026-05-27 15:55:02'),('fbd96a21-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@TREE-WAVE','UCNcWt_LxVO_Hqo-0HcMGb_Q','TREE WAVE','UUNcWt_LxVO_Hqo-0HcMGb_Q',3970,NULL,'2026-05-27 15:55:02'),('fbd96a95-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@i-playlist-hr','UCVS5gHrD0tZV8W4YvvGMuSA','i플리','UUVS5gHrD0tZV8W4YvvGMuSA',710,NULL,'2026-05-27 15:55:02'),('fbd96b16-5998-11f1-bf32-8cb0e994c0ea','https://www.youtube.com/@뮤잇-Music_it','UCdA3zZbviZv96ArWtR12cLw','뮤잇','UUdA3zZbviZv96ArWtR12cLw',673,NULL,'2026-05-27 15:55:02');
/*!40000 ALTER TABLE `youtube_channels` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-27 16:04:27
