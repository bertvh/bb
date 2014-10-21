-- MySQL dump 10.13  Distrib 5.5.36, for Win32 (x86)
--
-- Host: localhost    Database: rest
-- ------------------------------------------------------
-- Server version	5.5.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acct_account`
--

DROP TABLE IF EXISTS `acct_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acct_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active_ind` varchar(1) NOT NULL,
  `activity_dt_tm` datetime NOT NULL,
  `created_dt_tm` datetime NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acct_account`
--

LOCK TABLES `acct_account` WRITE;
/*!40000 ALTER TABLE `acct_account` DISABLE KEYS */;
INSERT INTO `acct_account` VALUES (1,'Y','2014-09-03 12:45:02','2014-06-08 07:41:23','Youspan'),(2,'Y','2014-09-05 09:56:25','2014-06-24 09:54:16','Wikizz'),(3,'Y','2014-09-03 21:53:40','2013-11-21 08:07:55','Quamba'),(4,'Y','2014-09-12 21:42:10','2014-06-11 16:43:23','Vimbo'),(5,'Y','2014-09-10 12:12:44','2013-11-26 08:03:49','Yombu'),(6,'Y','2014-09-08 11:01:17','2013-11-28 15:35:10','Bluezoom'),(7,'Y','2014-09-03 03:46:59','2014-05-15 03:53:30','Centidel'),(8,'Y','2014-09-13 21:54:26','2014-05-11 00:56:02','Jaxworks'),(9,'Y','2014-09-15 17:46:30','2014-04-15 09:41:42','Topicblab'),(10,'Y','2014-09-08 16:56:05','2014-04-02 12:50:40','Jaxnation');
/*!40000 ALTER TABLE `acct_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acct_account_capability`
--

DROP TABLE IF EXISTS `acct_account_capability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acct_account_capability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active_ind` varchar(1) NOT NULL,
  `activity_dt_tm` datetime NOT NULL,
  `created_dt_tm` datetime NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `capability_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_s17ncu40nbilq88rmkctj3la2` (`account_id`),
  KEY `FK_id5x0llia0t7p8qkmh0539nuk` (`capability_id`),
  CONSTRAINT `FK_id5x0llia0t7p8qkmh0539nuk` FOREIGN KEY (`capability_id`) REFERENCES `acct_capability` (`id`),
  CONSTRAINT `FK_s17ncu40nbilq88rmkctj3la2` FOREIGN KEY (`account_id`) REFERENCES `acct_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acct_account_capability`
--

LOCK TABLES `acct_account_capability` WRITE;
/*!40000 ALTER TABLE `acct_account_capability` DISABLE KEYS */;
INSERT INTO `acct_account_capability` VALUES (1,'Y','2014-09-12 04:34:24','2014-06-01 10:43:25',1,7),(2,'Y','2014-09-13 22:15:25','2013-09-23 12:34:58',1,13),(3,'Y','2014-09-07 05:44:35','2013-10-03 02:52:50',1,14),(4,'Y','2014-09-12 11:35:06','2014-04-14 23:02:29',1,15),(5,'Y','2014-09-08 18:15:42','2014-07-21 06:16:27',3,7),(6,'N','2014-09-12 08:22:17','2014-03-08 17:41:43',3,14),(7,'Y','2014-09-10 21:13:47','2014-03-27 09:24:23',3,13),(8,'Y','2014-09-06 13:16:55','2013-11-25 00:25:02',4,15);
/*!40000 ALTER TABLE `acct_account_capability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acct_capability`
--

DROP TABLE IF EXISTS `acct_capability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acct_capability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active_ind` varchar(1) NOT NULL,
  `activity_dt_tm` datetime NOT NULL,
  `created_dt_tm` datetime NOT NULL,
  `name` varchar(30) NOT NULL,
  `role_type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acct_capability`
--

LOCK TABLES `acct_capability` WRITE;
/*!40000 ALTER TABLE `acct_capability` DISABLE KEYS */;
INSERT INTO `acct_capability` VALUES (1,'Y','2014-09-10 23:42:49','2014-01-23 23:54:13','add_user','ROLE'),(2,'Y','2014-09-04 20:07:02','2014-07-03 11:33:35','edit_user','ROLE'),(3,'Y','2014-09-14 12:20:49','2014-01-24 06:24:15','delete_user','ROLE'),(4,'Y','2014-09-13 17:02:28','2014-03-22 09:19:00','add_account','ROLE'),(5,'Y','2014-09-09 20:18:15','2014-07-12 11:20:38','edit_account','ROLE'),(6,'Y','2014-09-10 12:30:27','2014-08-15 01:10:36','delete_account','ROLE'),(7,'Y','2014-09-11 12:02:09','2014-01-05 12:21:33','VIP','ACCOUNT'),(8,'N','2014-09-04 13:29:51','2013-11-11 07:05:05','throw_rocks','ROLE'),(10,'Y','2014-09-04 13:29:51','2013-11-11 07:05:05','make_shoes','ROLE'),(11,'Y','2014-09-04 13:29:51','2013-11-11 07:05:05','eat_dirt','ROLE'),(12,'N','2014-09-04 13:29:51','2013-11-11 07:05:05','fire_clay','ROLE'),(13,'Y','2014-09-11 12:02:09','2014-01-05 12:21:33','silver','ACCOUNT'),(14,'Y','2014-09-11 12:02:09','2014-01-05 12:21:33','platinum','ACCOUNT'),(15,'Y','2014-09-11 12:02:09','2014-01-05 12:21:33','tin','ACCOUNT'),(16,'N','2014-09-11 12:02:09','2014-01-05 12:21:33','magic','ACCOUNT');
/*!40000 ALTER TABLE `acct_capability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acct_role`
--

DROP TABLE IF EXISTS `acct_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acct_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active_ind` varchar(1) NOT NULL,
  `activity_dt_tm` datetime NOT NULL,
  `created_dt_tm` datetime NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acct_role`
--

LOCK TABLES `acct_role` WRITE;
/*!40000 ALTER TABLE `acct_role` DISABLE KEYS */;
INSERT INTO `acct_role` VALUES (1,'Y','2014-09-09 20:33:40','2013-10-22 18:31:01','admin'),(2,'Y','2014-09-11 22:12:09','2014-05-03 08:40:44','subscriber'),(3,'Y','2014-09-11 22:12:09','2014-05-03 08:40:44','yeoman'),(4,'Y','2014-09-11 22:12:09','2014-05-03 08:40:44','freelancer'),(5,'Y','2014-09-11 22:12:09','2014-05-03 08:40:44','complainer');
/*!40000 ALTER TABLE `acct_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acct_role_capability`
--

DROP TABLE IF EXISTS `acct_role_capability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acct_role_capability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active_ind` varchar(1) NOT NULL,
  `activity_dt_tm` datetime NOT NULL,
  `created_dt_tm` datetime NOT NULL,
  `capability_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hmc8m730ngna1mh4i0gby5leq` (`capability_id`),
  KEY `FK_j57kissjiwko8vols7k8bt0ps` (`role_id`),
  CONSTRAINT `FK_j57kissjiwko8vols7k8bt0ps` FOREIGN KEY (`role_id`) REFERENCES `acct_role` (`id`),
  CONSTRAINT `FK_hmc8m730ngna1mh4i0gby5leq` FOREIGN KEY (`capability_id`) REFERENCES `acct_capability` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acct_role_capability`
--

LOCK TABLES `acct_role_capability` WRITE;
/*!40000 ALTER TABLE `acct_role_capability` DISABLE KEYS */;
INSERT INTO `acct_role_capability` VALUES (1,'Y','2014-09-12 04:34:24','2014-06-01 10:43:25',1,1),(2,'Y','2014-09-13 22:15:25','2013-09-23 12:34:58',2,1),(3,'Y','2014-09-07 05:44:35','2013-10-03 02:52:50',3,1),(4,'Y','2014-09-12 11:35:06','2014-04-14 23:02:29',4,1),(5,'Y','2014-09-09 07:05:52','2014-05-17 15:53:46',5,1),(6,'Y','2014-09-09 03:16:28','2014-02-22 00:18:01',3,3),(7,'Y','2014-09-08 18:15:42','2014-07-21 06:16:27',2,3),(8,'Y','2014-09-12 08:22:17','2014-03-08 17:41:43',4,3),(9,'Y','2014-09-10 21:13:47','2014-03-27 09:24:23',5,3),(10,'Y','2014-09-06 13:16:55','2013-11-25 00:25:02',3,4);
/*!40000 ALTER TABLE `acct_role_capability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acct_user`
--

DROP TABLE IF EXISTS `acct_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acct_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active_ind` varchar(1) NOT NULL,
  `activity_dt_tm` datetime NOT NULL,
  `created_dt_tm` datetime NOT NULL,
  `email` longtext NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` longtext NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qbwmio803vtvsr1wueu0ettmu` (`user_name`),
  KEY `FK_ck1x450trxeepe2iyk9s23ygf` (`account_id`),
  KEY `FK_orrb8yr0ewg2ownv7aocic13w` (`role_id`),
  CONSTRAINT `FK_orrb8yr0ewg2ownv7aocic13w` FOREIGN KEY (`role_id`) REFERENCES `acct_role` (`id`),
  CONSTRAINT `FK_ck1x450trxeepe2iyk9s23ygf` FOREIGN KEY (`account_id`) REFERENCES `acct_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acct_user`
--

LOCK TABLES `acct_user` WRITE;
/*!40000 ALTER TABLE `acct_user` DISABLE KEYS */;
INSERT INTO `acct_user` VALUES (1,'Y','2014-09-13 12:50:18','2014-04-12 21:45:32','jsnyder0@gnu.org','Joseph','Snyder','FFBf6qa6wygU','jsnyder0',8,2),(2,'Y','2014-09-04 06:24:27','2014-08-20 17:12:43','sgilbert1@guardian.co.uk','Sharon','Gilbert','8BR7Yrfp','sgilbert1',NULL,1),(3,'Y','2014-09-08 04:44:59','2014-01-26 16:21:49','cphillips2@accuweather.com','Christopher','Phillips','nY42l9kw1F8','cphillips2',4,1),(4,'Y','2014-09-13 00:51:51','2014-01-08 23:32:14','dsullivan3@nydailynews.com','Debra','Sullivan','ZS2kgP0ANl','dsullivan3',NULL,2),(5,'Y','2014-09-15 06:10:47','2013-10-11 12:25:28','shunt4@posterous.com','Sarah','Hunt','59PKBh','shunt4',NULL,1),(6,'Y','2014-09-12 13:44:32','2013-10-20 13:28:57','wanderson5@springer.com','Willie','Anderson','vEWTiGgM1c','wanderson5',NULL,2),(7,'Y','2014-09-13 23:40:33','2014-01-20 21:54:03','jsullivan6@typepad.com','Jeffrey','Sullivan','aEVdJbUzHl','jsullivan6',NULL,2),(8,'Y','2014-09-03 13:33:27','2013-10-09 16:14:29','jcampbell7@parallels.com','Jose','Campbell','ONNIycoVvkK','jcampbell7',NULL,2),(9,'Y','2014-09-12 05:33:56','2014-07-03 17:10:20','jboyd8@epa.gov','Janet','Boyd','WJaMY7hY4','jboyd8',7,1),(10,'Y','2014-09-11 16:27:20','2014-04-20 12:54:44','ghunter9@dailymotion.com','Gregory','Hunter','2SMP7tjZ2','ghunter9',NULL,2),(11,'Y','2014-09-02 17:29:02','2013-09-22 05:07:49','ebarnesa@msn.com','Earl','Barnes','wjRtoxberZRK','ebarnesa',4,2),(12,'Y','2014-09-12 16:02:29','2014-01-02 12:39:46','jmillsb@google.pl','Janet','Mills','pZop17Y','jmillsb',NULL,1),(13,'Y','2014-09-09 13:04:56','2013-12-30 07:26:09','kdunnc@posterous.com','Kimberly','Dunn','IA6Uaa','kdunnc',8,1),(14,'Y','2014-09-02 09:12:41','2013-10-26 14:02:01','jgeorged@howstuffworks.com','Justin','George','xRFp5Hv2FP5U','jgeorged',NULL,1),(15,'Y','2014-09-13 12:07:32','2013-12-07 07:28:44','jcastilloe@engadget.com','Johnny','Castillo','B5qhPp0Q','jcastilloe',NULL,1),(16,'Y','2014-09-10 13:47:23','2013-12-15 00:19:11','rmorrisf@slashdot.org','Ruth','Morris','VZAwYhI6n','rmorrisf',NULL,1),(17,'Y','2014-09-04 23:17:30','2014-06-13 16:35:46','rtuckerg@house.gov','Rachel','Tucker','Tt8HRHbOkL4B','rtuckerg',NULL,1),(18,'Y','2014-09-09 22:29:30','2013-09-26 16:32:48','bpattersonh@nyu.edu','Barbara','Patterson','dtFmHBF','bpattersonh',NULL,2),(19,'Y','2014-09-15 22:37:26','2014-03-12 17:59:37','kthompsoni@ehow.com','Kimberly','Thompson','PqfXbakYYFX','kthompsoni',NULL,1),(20,'Y','2014-09-09 12:30:23','2013-11-08 14:27:14','tbakerj@ameblo.jp','Timothy','Baker','gjbGkQg9wzEI','tbakerj',1,1),(21,'Y','2014-09-14 13:41:08','2014-06-05 05:59:13','kgutierrezk@soundcloud.com','Keith','Gutierrez','jCvkQpqz','kgutierrezk',NULL,1),(22,'Y','2014-09-15 17:25:54','2013-12-09 01:16:49','rpattersonl@dion.ne.jp','Ruth','Patterson','o6v2dHhyP','rpattersonl',NULL,1),(23,'Y','2014-09-10 08:11:38','2013-12-26 10:15:17','rfergusonm@yandex.ru','Russell','Ferguson','Bn3FgFtuVw','rfergusonm',NULL,1),(24,'Y','2014-09-07 11:29:11','2014-08-13 15:38:32','jhuntern@tamu.edu','Jerry','Hunter','e184Q9lQ','jhuntern',NULL,2),(25,'Y','2014-09-13 04:53:12','2014-01-22 13:17:53','afullero@quantcast.com','Antonio','Fuller','o4pMnwFOPeeb','afullero',NULL,1),(26,'Y','2014-09-13 10:21:59','2013-11-22 20:14:34','ajenkinsp@homestead.com','Alice','Jenkins','KdIMuH','ajenkinsp',NULL,2),(27,'Y','2014-09-08 08:53:54','2014-08-13 08:03:52','aharrisq@comcast.net','Aaron','Harris','IUXqkC7','aharrisq',NULL,2),(28,'Y','2014-09-03 22:30:46','2014-03-24 09:13:59','ccarpenterr@slashdot.org','Carl','Carpenter','Es1HgBAHexk','ccarpenterr',4,2),(29,'Y','2014-09-04 21:52:15','2013-12-25 17:31:42','mhudsons@seattletimes.com','Marilyn','Hudson','hZTYPiy4','mhudsons',NULL,1),(30,'Y','2014-09-14 18:37:44','2014-05-30 12:25:43','sandrewst@vkontakte.ru','Shirley','Andrews','Lr9ysX','sandrewst',NULL,1);
/*!40000 ALTER TABLE `acct_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-20 17:02:21
