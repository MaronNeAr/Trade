-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: trade
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position` (
  `id` int NOT NULL AUTO_INCREMENT,
  `currency_code` varchar(45) DEFAULT NULL,
  `display_name` varchar(45) DEFAULT NULL,
  `inventory` decimal(10,2) DEFAULT NULL,
  `price` decimal(12,6) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1,'luna','LUNA',53.15,NULL,'marlon1475'),(3,'bch','BCH',1.10,NULL,'marlon1475');
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_detail`
--

DROP TABLE IF EXISTS `trade_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(5) DEFAULT NULL,
  `base_currency` varchar(10) DEFAULT NULL,
  `quote_currency` varchar(10) DEFAULT NULL,
  `price` decimal(12,6) DEFAULT NULL,
  `size` decimal(10,2) DEFAULT NULL,
  `time` bigint DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_detail`
--

LOCK TABLES `trade_detail` WRITE;
/*!40000 ALTER TABLE `trade_detail` DISABLE KEYS */;
INSERT INTO `trade_detail` VALUES (5,'buy','btc','usdt',200.133435,1.09,1676197473781,'marlon1475'),(6,'buy','luna','usdt',200.133435,1.09,1676197557549,'marlon1475'),(7,'sell','btc','usdt',200.133435,1.09,1676197848992,'marlon1475'),(8,'sell','luna','usdt',200.133435,1.09,1676197982920,'marlon1475'),(11,'buy','btc','usdt',21889.630000,0.21,1676204835876,'marlon1475'),(12,'buy','bch','usdt',125.610000,1.10,1676204888325,'marlon1475'),(13,'buy','eth','btc',0.070231,0.32,1676204952943,'marlon1475'),(14,'buy','luna','usdt',1.845300,10.63,1676204989968,'marlon1475'),(15,'buy','luna','usdt',1.845300,10.63,1676204996876,'marlon1475'),(16,'buy','luna','usdt',1.845300,10.63,1676205211024,'marlon1475'),(17,'buy','luna','usdt',1.845300,10.63,1676205213514,'marlon1475'),(18,'buy','luna','usdt',1.845300,10.63,1676205215398,'marlon1475'),(19,'sell','btc','usdt',21844.440000,0.18,1676205861063,'marlon1475');
/*!40000 ALTER TABLE `trade_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tweet`
--

DROP TABLE IF EXISTS `tweet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tweet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(45) DEFAULT NULL,
  `picture` varchar(45) DEFAULT NULL,
  `position` varchar(45) DEFAULT NULL,
  `time` int DEFAULT NULL,
  `followers` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweet`
--

LOCK TABLES `tweet` WRITE;
/*!40000 ALTER TABLE `tweet` DISABLE KEYS */;
INSERT INTO `tweet` VALUES (1,'这是第一条测试推文','img/tweet/test1.png','上海市黄浦区',1675497590,'超人不会飞、Andy Lee、阿巴阿巴、Aurora_NeAr','marlon1475'),(2,'这是第二条测试推文','img/tweet/test2.png','北京市海淀区',1675497789,'纯情','admin1'),(3,'这是第三条测试推文','img/tweet/test3.png','深圳市龙岗区',1675492346,'拾柒','admin2'),(4,'这是第四条测试推文','img/tweet/test4.jpg','广州市越秀区',1675492321,'','admin3');
/*!40000 ALTER TABLE `tweet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tweet_comment`
--

DROP TABLE IF EXISTS `tweet_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tweet_comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `observer` varchar(45) DEFAULT NULL,
  `comment` varchar(45) DEFAULT NULL,
  `tweet_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweet_comment`
--

LOCK TABLES `tweet_comment` WRITE;
/*!40000 ALTER TABLE `tweet_comment` DISABLE KEYS */;
INSERT INTO `tweet_comment` VALUES (3,'marlon1475','这是一条测试评论',2),(4,'marlon1475','这是一条测试评论',3),(5,'Aurora_NeAr','这是第二条测试推文啊哈哈哈哈哈',1),(6,'Aurora_NeAr','这可能是一条评论捏',2),(7,'Aurora_NeAr','啊？是嘛 我不道啊',3),(8,'阿巴阿巴','评论',1),(9,'Aurora_NeAr','哈哈',1),(10,'Aurora_NeAr','哎呀呀',2);
/*!40000 ALTER TABLE `tweet_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `v_account`
--

DROP TABLE IF EXISTS `v_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `v_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(45) DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT NULL,
  `usdt` decimal(10,2) DEFAULT NULL,
  `btc` decimal(10,2) DEFAULT NULL,
  `eth` decimal(10,2) DEFAULT NULL,
  `ht` decimal(10,2) DEFAULT NULL,
  `husd` decimal(10,2) DEFAULT NULL,
  `usdd` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `v_account`
--

LOCK TABLES `v_account` WRITE;
/*!40000 ALTER TABLE `v_account` DISABLE KEYS */;
INSERT INTO `v_account` VALUES (1,'marlon1475',5000.00,4058.13,0.01,0.32,0.00,0.00,0.00),(2,'admin1',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(3,'admin2',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(4,'admin3',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(5,'admin4',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(6,'admin5',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(7,'admin6',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(8,'admin7',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(9,'admin8',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(10,'admin9',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(11,'admin10',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(12,'2402165946',10000.00,0.00,0.00,0.00,0.00,0.00,0.00),(13,'13299531475',10000.00,0.00,0.00,0.00,0.00,0.00,0.00);
/*!40000 ALTER TABLE `v_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `v_user`
--

DROP TABLE IF EXISTS `v_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `v_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `admin` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `v_user`
--

LOCK TABLES `v_user` WRITE;
/*!40000 ALTER TABLE `v_user` DISABLE KEYS */;
INSERT INTO `v_user` VALUES (1,'Aurora_NeAr','ZxSYi6RxUbJkzqh3xu2IzQ==','marlon1475','img/icon/test1.png',0,1),(2,'纯情男中专生','o7uKFqMTXk9lvtjBQpekPA==','admin1','img/icon/test2.jpg',0,1),(3,'pluto.','JHWpodhy/t2VjoVh+gMi9w==','admin2','img/icon/test3.jpg',0,0),(4,'拾柒','IXIhRR2HVRaL2dv7BOtfwA==','admin3','img/icon/test4.jpg',0,0),(5,'阿巴阿巴','Dh9clJY1T5qr9muzp4TAPg==','admin4','img/icon/test1.png',0,0),(6,'歪比巴卜','aZHlPB/yq+ZUvFp58eAtJA==','admin5','img/icon/test1.png',0,0),(7,'哎嘿嘿','1sCTjpo9NqA2q3HOZuIDkQ==','admin6','img/icon/test2.jpg',0,0),(8,'null','swTR6F+kNG4AazahgcLAtQ==','admin7','img/icon/test3.jpg',0,0),(9,'null','WBzeSJ/T/4quxyAThcpEaw==','admin8','img/icon/test4.jpg',0,0),(10,'null','pNwIufu3pnOdyWxR3ZO9sQ==','admin9','img/icon/test1.png',0,0),(11,'null','9B996iU8BLkt97PnAG1Mdw==','admin10','img/icon/test2.jpg',0,0),(12,'阿巴阿巴','bFXVoqPHBESXNtV7s2H63Q==','2402165946','img/icon/test3.jpg',0,0),(13,'歪比巴伯','ytIEi6flgp/XSvzrbwkGTg==','13299531475','img/icon/test4.jpg',0,0);
/*!40000 ALTER TABLE `v_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'trade'
--

--
-- Dumping routines for database 'trade'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-12 21:01:51
