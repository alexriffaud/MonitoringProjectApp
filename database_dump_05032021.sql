CREATE DATABASE  IF NOT EXISTS `projectmonitoring` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `projectmonitoring`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: projectmonitoring
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `join_requirement_task`
--

DROP TABLE IF EXISTS `join_requirement_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `join_requirement_task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task` int NOT NULL,
  `requirement` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `task_join_idx` (`task`),
  KEY `requirement_join_idx` (`requirement`),
  CONSTRAINT `requirement_join` FOREIGN KEY (`requirement`) REFERENCES `requirement` (`id`),
  CONSTRAINT `task_join` FOREIGN KEY (`task`) REFERENCES `task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `join_requirement_task`
--

LOCK TABLES `join_requirement_task` WRITE;
/*!40000 ALTER TABLE `join_requirement_task` DISABLE KEYS */;
INSERT INTO `join_requirement_task` VALUES (4,1,2),(5,1,3),(6,2,3),(7,2,2),(8,3,2),(9,4,2),(10,5,2),(11,6,2),(12,6,2),(13,7,4),(14,8,4),(15,9,4),(16,10,5),(17,11,5),(18,12,6),(19,12,7),(20,13,8),(21,13,9),(22,13,10),(23,14,10),(24,15,11),(25,16,12),(26,17,13),(27,18,14),(28,19,15),(29,20,16),(30,20,15);
/*!40000 ALTER TABLE `join_requirement_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `milestone`
--

DROP TABLE IF EXISTS `milestone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `milestone` (
  `id` int NOT NULL AUTO_INCREMENT,
  `label` varchar(45) NOT NULL,
  `manager` int NOT NULL,
  `expected_delivery_date` date NOT NULL,
  `real_delivery_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_milestone_idx` (`manager`),
  CONSTRAINT `user_milestone` FOREIGN KEY (`manager`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `milestone`
--

LOCK TABLES `milestone` WRITE;
/*!40000 ALTER TABLE `milestone` DISABLE KEYS */;
INSERT INTO `milestone` VALUES (1,'Jalon 1',1,'2021-03-05',NULL),(2,'Jalon 2',1,'2021-03-06',NULL),(3,'Jalon 3',1,'2021-04-07',NULL),(4,'Jalon 4',2,'2021-01-05',NULL),(5,'Jalon 5',2,'2021-08-11',NULL),(6,'Jalon 6',2,'2021-03-25',NULL),(7,'Jalon 7',4,'2021-04-15',NULL),(8,'Jalon 8',4,'2021-05-10',NULL),(9,'Jalon 9',3,'2021-05-20',NULL),(10,'Jalon 10',3,'2021-02-09',NULL),(11,'Jalon 11',4,'2021-03-19',NULL),(12,'Jalon 12',2,'2021-03-14',NULL),(13,'Jalon 13',2,'2021-03-15',NULL);
/*!40000 ALTER TABLE `milestone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `manager` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_project_idx` (`manager`),
  CONSTRAINT `user_project` FOREIGN KEY (`manager`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Project 1',1),(2,'Project 2',1),(3,'Project 3',2),(4,'Project 4',2),(5,'Project 5',3),(6,'Project 6',3),(7,'Project 7',4),(8,'Project 8',4),(9,'Project 9',1),(10,'Project 10',1),(11,'Project 11',2),(12,'Project 12',3),(13,'Project 13',4);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement`
--

DROP TABLE IF EXISTS `requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requirement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `identifier` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  `is_functional` tinyint NOT NULL,
  `type` int DEFAULT NULL,
  `project` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type_requirement_idx` (`type`),
  KEY `project_requirement_idx` (`project`),
  CONSTRAINT `project_requirement` FOREIGN KEY (`project`) REFERENCES `project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `type_requirement` FOREIGN KEY (`type`) REFERENCES `type` (`id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement`
--

LOCK TABLES `requirement` WRITE;
/*!40000 ALTER TABLE `requirement` DISABLE KEYS */;
INSERT INTO `requirement` VALUES (2,'654564','Exigence 1',1,2,1),(3,'456168','Exigence 2',0,NULL,2),(4,'888888','Exigence 3',0,NULL,3),(5,'855855','Exigence 4',0,NULL,3),(6,'785785','Exigence 5',0,NULL,1),(7,'785755','Exigence 6',0,NULL,1),(8,'885455','Exigence 7',0,NULL,1),(9,'123555','Exigence 8',1,1,1),(10,'785888','Exigence 9',1,1,1),(11,'455445','Exigence 10',1,3,1),(12,'452588','Exigence 11',1,3,4),(13,'428855','Exigence 12',1,2,5),(14,'655252','Exigence 13',1,4,6),(15,'529985','Exigence 14',1,4,7),(16,'458788','Exigence 15',1,1,8),(17,'686866','Exigence 16',1,2,9);
/*!40000 ALTER TABLE `requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `label` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  `identifier` varchar(45) NOT NULL,
  `user` int NOT NULL,
  `theoretical_start_date` datetime NOT NULL,
  `load` int NOT NULL,
  `real_start_date` datetime DEFAULT NULL,
  `previous_task` int DEFAULT NULL,
  `milestone` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_task_idx` (`user`),
  KEY `milestone_task_idx` (`milestone`),
  CONSTRAINT `milestone_task` FOREIGN KEY (`milestone`) REFERENCES `milestone` (`id`),
  CONSTRAINT `user_task` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Task 1','Description 1','747455',1,'2021-03-05 00:00:00',5,NULL,NULL,1),(2,'Task 2','Description 2','747444',1,'2021-03-06 00:00:00',10,NULL,NULL,1),(3,'Task 3','Description 3','747433',1,'2021-03-07 00:00:00',24,NULL,NULL,2),(4,'Task 4','Description 4','747422',1,'2021-04-15 00:00:00',11,NULL,NULL,2),(5,'Task 5','Description 5','747411',1,'2021-12-24 00:00:00',9,NULL,NULL,2),(6,'Task 6','Description 6','747400',2,'2021-02-11 00:00:00',14,NULL,NULL,2),(7,'Task 7','Description 7','747415',2,'2021-01-27 00:00:00',15,NULL,NULL,2),(8,'Task 8','Description 8','747416',2,'2021-05-02 00:00:00',20,NULL,NULL,3),(9,'Task 9','Description 9','747412',3,'2021-06-06 00:00:00',35,NULL,NULL,1),(10,'Task 10','Description 10','747413',3,'2021-03-25 00:00:00',88,NULL,NULL,1),(11,'Task 11','Description 11','747410',3,'2021-03-14 00:00:00',14,NULL,NULL,1),(12,'Task 12','Description 12','747411',3,'2021-04-14 00:00:00',5,NULL,NULL,1),(13,'Task 13','Description 13','747409',3,'2021-06-21 00:00:00',2,NULL,NULL,1),(14,'Task 14','Description 14','747408',4,'2021-03-19 00:00:00',1,NULL,NULL,3),(15,'Task 15','Description 15','747407',4,'2021-03-05 00:00:00',5,NULL,NULL,3),(16,'Task 16','Description 16','747406',4,'2021-04-17 00:00:00',16,NULL,NULL,4),(17,'Task 17','Description 17','747405',4,'2021-08-05 00:00:00',24,NULL,NULL,4),(18,'Task 18','Description 18','747404',4,'2021-09-18 00:00:00',31,NULL,NULL,5),(19,'Task 19','Description 19','747403',2,'2021-07-12 00:00:00',17,NULL,NULL,6),(20,'Task 20','Description 20','747402',2,'2021-01-08 00:00:00',10,NULL,NULL,7);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'Performance'),(2,'Interface utilisateur'),(3,'Qualité'),(4,'Services');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `is_manager` tinyint DEFAULT NULL,
  `is_project_manager` varchar(45) DEFAULT NULL,
  `trigramme` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'riffaud','riffaud',1,'1','RIF'),(2,'test','test',0,'0','TES'),(3,'Manager','Manager',1,'0','MAN'),(4,'Richard','richard',0,'1','RIC');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-05 10:12:54
