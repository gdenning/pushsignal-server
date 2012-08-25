-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: PushSignal
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.10

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
-- Table structure for table `TConfigC2dm`
--

DROP TABLE IF EXISTS `TConfigC2dm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TConfigC2dm` (
  `ConfigId` int(11) NOT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `AuthToken` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ConfigId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TEvent`
--

DROP TABLE IF EXISTS `TEvent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TEvent` (
  `EventID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  `CreateDate` datetime NOT NULL,
  `CreateUserID` bigint(20) NOT NULL,
  PRIMARY KEY (`EventID`),
  KEY `FK_Event_User` (`CreateUserID`),
  CONSTRAINT `FK_Event_User` FOREIGN KEY (`CreateUserID`) REFERENCES `TUser` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TEventInvite`
--

DROP TABLE IF EXISTS `TEventInvite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TEventInvite` (
  `EventInviteID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EventID` bigint(20) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `UserID` bigint(20) DEFAULT NULL,
  `CreateDate` datetime NOT NULL,
  PRIMARY KEY (`EventInviteID`),
  UNIQUE KEY `UK_EmailUserID` (`Email`,`UserID`),
  KEY `FK_EventInvite_Event` (`EventID`),
  KEY `FK_EventInvite_User` (`UserID`),
  CONSTRAINT `FK_EventInvite_Event` FOREIGN KEY (`EventID`) REFERENCES `TEvent` (`EventID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EventInvite_User` FOREIGN KEY (`UserID`) REFERENCES `TUser` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TEventMember`
--

DROP TABLE IF EXISTS `TEventMember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TEventMember` (
  `EventMemberID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EventID` bigint(20) NOT NULL,
  `UserID` bigint(20) NOT NULL,
  PRIMARY KEY (`EventMemberID`),
  UNIQUE KEY `UK_SignalGroupIDUserID` (`EventID`,`UserID`),
  KEY `FK_EventMember_Event` (`EventID`),
  KEY `FK_EventMember_User` (`UserID`),
  CONSTRAINT `FK_EventMember_Event` FOREIGN KEY (`EventID`) REFERENCES `TEvent` (`EventID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EventMember_User` FOREIGN KEY (`UserID`) REFERENCES `TUser` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TTrigger`
--

DROP TABLE IF EXISTS `TTrigger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TTrigger` (
  `TriggerID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EventID` bigint(20) NOT NULL,
  `CreateDate` datetime NOT NULL,
  `CreateUserID` bigint(20) NOT NULL,
  PRIMARY KEY (`TriggerID`),
  KEY `FK_Trigger_User` (`CreateUserID`),
  KEY `FK_Trigger_Event` (`EventID`),
  CONSTRAINT `FK_Trigger_Event` FOREIGN KEY (`EventID`) REFERENCES `TEvent` (`EventID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Trigger_User` FOREIGN KEY (`CreateUserID`) REFERENCES `TUser` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TTriggerAlert`
--

DROP TABLE IF EXISTS `TTriggerAlert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TTriggerAlert` (
  `TriggerAlertID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TriggerID` bigint(20) NOT NULL,
  `UserID` bigint(20) NOT NULL,
  `ModifiedDate` datetime NOT NULL,
  `Status` varchar(20) NOT NULL,
  PRIMARY KEY (`TriggerAlertID`),
  KEY `FK_TriggerAck_Trigger` (`TriggerID`),
  KEY `FK_TriggerAck_User` (`UserID`),
  CONSTRAINT `FK_TriggerAck_Trigger` FOREIGN KEY (`TriggerID`) REFERENCES `TTrigger` (`TriggerID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TriggerAck_User` FOREIGN KEY (`UserID`) REFERENCES `TUser` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=278 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TUser`
--

DROP TABLE IF EXISTS `TUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TUser` (
  `UserID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  `DeviceType` varchar(50) DEFAULT NULL,
  `DeviceID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `UK_Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TUserDevice`
--

DROP TABLE IF EXISTS `TUserDevice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TUserDevice` (
  `UserDeviceID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserID` bigint(20) NOT NULL,
  `DeviceType` varchar(50) NOT NULL,
  `DeviceID` varchar(1024) DEFAULT NULL,
  `LastSeenDate` datetime NOT NULL,
  `RegistrationID` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`UserDeviceID`),
  KEY `FK_UserDevice_User` (`UserID`),
  CONSTRAINT `FK_UserDevice_User` FOREIGN KEY (`UserID`) REFERENCES `TUser` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-04-03 21:18:50
