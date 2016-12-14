-- --------------------------------------------------------
-- 主機:                           localhost
-- 服務器版本:                        10.1.13-MariaDB - mariadb.org binary distribution
-- 服務器操作系統:                      Win64
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 導出 jersey 的資料庫結構
DROP DATABASE IF EXISTS `jersey`;
CREATE DATABASE IF NOT EXISTS `jersey` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jersey`;


-- 導出  表 jersey.commodity 結構
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE IF NOT EXISTS `commodity` (
  `commodity_id` varchar(50) NOT NULL,
  `purchase_case_id` varchar(50) DEFAULT NULL,
  `item_name` varchar(45) DEFAULT NULL,
  `commodity_type_id` varchar(50) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `sell_price` int(11) DEFAULT NULL,
  `authority` varchar(45) DEFAULT NULL,
  `is_stored` tinyint(4) NOT NULL DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `link` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`commodity_id`),
  KEY `FK_commodity_purchase_case` (`purchase_case_id`),
  KEY `FK_commodity_commodity_type` (`commodity_type_id`),
  CONSTRAINT `FK_commodity_commodity_type` FOREIGN KEY (`commodity_type_id`) REFERENCES `commodity_type` (`commodity_type_id`),
  CONSTRAINT `FK_commodity_purchase_case` FOREIGN KEY (`purchase_case_id`) REFERENCES `purchase_case` (`purchase_case_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.commodity 的資料：~0 rows (大約)
DELETE FROM `commodity`;
/*!40000 ALTER TABLE `commodity` DISABLE KEYS */;
/*!40000 ALTER TABLE `commodity` ENABLE KEYS */;


-- 導出  表 jersey.commodity_attr 結構
DROP TABLE IF EXISTS `commodity_attr`;
CREATE TABLE IF NOT EXISTS `commodity_attr` (
  `commodity_attr_id` varchar(50) NOT NULL,
  `commodity_type_id` varchar(50) DEFAULT NULL,
  `commodity_attr` varchar(50) DEFAULT NULL,
  `commodity_attr_authority` varchar(15) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`commodity_attr_id`),
  KEY `FK_commodity_attr_commodity_type` (`commodity_type_id`),
  CONSTRAINT `FK_commodity_attr_commodity_type` FOREIGN KEY (`commodity_type_id`) REFERENCES `commodity_type` (`commodity_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.commodity_attr 的資料：~6 rows (大約)
DELETE FROM `commodity_attr`;
/*!40000 ALTER TABLE `commodity_attr` DISABLE KEYS */;
/*!40000 ALTER TABLE `commodity_attr` ENABLE KEYS */;


-- 導出  表 jersey.commodity_attr_mapping 結構
DROP TABLE IF EXISTS `commodity_attr_mapping`;
CREATE TABLE IF NOT EXISTS `commodity_attr_mapping` (
  `commodity_attr_mapping_id` varchar(50) NOT NULL,
  `commodity_id` varchar(50) DEFAULT NULL,
  `commodity_attr_id` varchar(50) DEFAULT NULL,
  `commodity_attr_value` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`commodity_attr_mapping_id`),
  KEY `FK_commodity_attr_mapping_commodity` (`commodity_id`),
  KEY `FK_commodity_attr_mapping_commodity_attr` (`commodity_attr_id`),
  CONSTRAINT `FK_commodity_attr_mapping_commodity` FOREIGN KEY (`commodity_id`) REFERENCES `commodity` (`commodity_id`),
  CONSTRAINT `FK_commodity_attr_mapping_commodity_attr` FOREIGN KEY (`commodity_attr_id`) REFERENCES `commodity_attr` (`commodity_attr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.commodity_attr_mapping 的資料：~0 rows (大約)
DELETE FROM `commodity_attr_mapping`;
/*!40000 ALTER TABLE `commodity_attr_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `commodity_attr_mapping` ENABLE KEYS */;


-- 導出  表 jersey.commodity_type 結構
DROP TABLE IF EXISTS `commodity_type`;
CREATE TABLE IF NOT EXISTS `commodity_type` (
  `commodity_type_id` varchar(50) NOT NULL,
  `commodity_type` varchar(50) DEFAULT NULL,
  `authority` varchar(30) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`commodity_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.commodity_type 的資料：~0 rows (大約)
DELETE FROM `commodity_type`;
/*!40000 ALTER TABLE `commodity_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `commodity_type` ENABLE KEYS */;


-- 導出  表 jersey.picture 結構
DROP TABLE IF EXISTS `picture`;
CREATE TABLE IF NOT EXISTS `picture` (
  `picture_id` varchar(50) NOT NULL,
  `commodity_id` varchar(50) NOT NULL,
  `sequence_id` int(11) DEFAULT NULL,
  `picture` longblob,
  `file_name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`picture_id`),
  KEY `FK_picture_commodity` (`commodity_id`),
  CONSTRAINT `FK_picture_commodity` FOREIGN KEY (`commodity_id`) REFERENCES `commodity` (`commodity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.picture 的資料：~0 rows (大約)
DELETE FROM `picture`;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;


-- 導出  表 jersey.purchase_case 結構
DROP TABLE IF EXISTS `purchase_case`;
CREATE TABLE IF NOT EXISTS `purchase_case` (
  `purchase_case_id` varchar(50) NOT NULL,
  `sell_case_id` varchar(50) DEFAULT NULL,
  `store` varchar(50) DEFAULT NULL,
  `progress` varchar(45) DEFAULT NULL,
  `shipping_company` varchar(50) DEFAULT NULL,
  `tracking_number` varchar(45) DEFAULT NULL,
  `tracking_number_link` varchar(2083) DEFAULT NULL,
  `agent` varchar(45) DEFAULT NULL,
  `agent_tracking_number` varchar(45) DEFAULT NULL,
  `agent_tracking_number_link` varchar(2083) DEFAULT NULL,
  `is_abroad` tinyint(4) NOT NULL DEFAULT '0',
  `cost` int(11) DEFAULT NULL,
  `agent_cost` int(11) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`purchase_case_id`),
  KEY `FK_purchase_case_sell_case` (`sell_case_id`),
  KEY `FK_purchase_case_store` (`store`),
  KEY `FK_purchase_case_store_2` (`shipping_company`),
  CONSTRAINT `FK_purchase_case_sell_case` FOREIGN KEY (`sell_case_id`) REFERENCES `sell_case` (`sell_case_id`),
  CONSTRAINT `FK_purchase_case_store` FOREIGN KEY (`store`) REFERENCES `store` (`store_id`),
  CONSTRAINT `FK_purchase_case_store_2` FOREIGN KEY (`shipping_company`) REFERENCES `store` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.purchase_case 的資料：~0 rows (大約)
DELETE FROM `purchase_case`;
/*!40000 ALTER TABLE `purchase_case` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_case` ENABLE KEYS */;


-- 導出  表 jersey.sell_case 結構
DROP TABLE IF EXISTS `sell_case`;
CREATE TABLE IF NOT EXISTS `sell_case` (
  `sell_case_id` varchar(50) NOT NULL,
  `addressee` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `tracking_number` varchar(45) DEFAULT NULL,
  `transport_method` varchar(45) DEFAULT NULL,
  `is_shipping` tinyint(4) NOT NULL DEFAULT '0',
  `income` int(11) DEFAULT NULL,
  `transport_cost` int(11) DEFAULT NULL,
  `collected` int(11) DEFAULT NULL,
  `uncollected` int(11) DEFAULT NULL,
  `shipping_time` datetime DEFAULT NULL,
  `close_time` datetime DEFAULT NULL,
  `is_checked` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`sell_case_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.sell_case 的資料：~0 rows (大約)
DELETE FROM `sell_case`;
/*!40000 ALTER TABLE `sell_case` DISABLE KEYS */;
/*!40000 ALTER TABLE `sell_case` ENABLE KEYS */;


-- 導出  表 jersey.store 結構
DROP TABLE IF EXISTS `store`;
CREATE TABLE IF NOT EXISTS `store` (
  `store_id` varchar(50) NOT NULL,
  `type` varchar(50) DEFAULT '0',
  `name` varchar(50) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.store 的資料：~0 rows (大約)
DELETE FROM `store`;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
/*!40000 ALTER TABLE `store` ENABLE KEYS */;


-- 導出  表 jersey.system_param 結構
DROP TABLE IF EXISTS `system_param`;
CREATE TABLE IF NOT EXISTS `system_param` (
  `system_param_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`system_param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.system_param 的資料：~9 rows (大約)
DELETE FROM `system_param`;
/*!40000 ALTER TABLE `system_param` DISABLE KEYS */;
INSERT INTO `system_param` (`system_param_id`, `name`, `value`, `create_time`, `last_modify_time`) VALUES
	('SP20161207000000', 'USER_CONFIG_ID', '0', '2016-12-05 00:34:18', '2016-12-05 00:34:18'),
	('SP20161207000001', 'STORE_ID', '0', '2016-12-05 00:34:02', '2016-12-05 00:34:02'),
	('SP20161207000002', 'PICTURE_ID', '0', '2016-12-05 00:33:47', '2016-12-05 00:33:47'),
	('SP20161207000003', 'COMMODITY_ATTR_MAPPING_ID', '0', '2016-12-05 00:33:24', '2016-12-05 00:33:24'),
	('SP20161207000004', 'COMMODITY_TYPE_ID', '24', '2016-12-05 00:32:52', '2016-12-15 00:23:31'),
	('SP20161207000005', 'COMMODITY_ATTR_ID', '27', '2016-12-04 18:57:21', '2016-12-14 23:52:22'),
	('SP20161207000006', 'SELL_CASE_ID', '0', '2016-12-03 15:29:59', '2016-12-03 15:30:01'),
	('SP20161207000007', 'PURCHASE_CASE_ID', '0', '2016-12-03 15:29:56', '2016-12-03 15:29:58'),
	('SP20161207000008', 'COMMODITY_ID', '0', '2016-12-03 15:29:53', '2016-12-03 15:29:55');
/*!40000 ALTER TABLE `system_param` ENABLE KEYS */;


-- 導出  表 jersey.user_config 結構
DROP TABLE IF EXISTS `user_config`;
CREATE TABLE IF NOT EXISTS `user_config` (
  `user_config_id` varchar(50) NOT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `authority` varchar(30) DEFAULT NULL,
  `commodity_page_size` int(11) NOT NULL,
  `purchase_case_page_size` int(11) NOT NULL,
  `sell_case_page_size` int(11) NOT NULL,
  `store_page_size` int(11) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在導出表  jersey.user_config 的資料：~0 rows (大約)
DELETE FROM `user_config`;
/*!40000 ALTER TABLE `user_config` DISABLE KEYS */;
INSERT INTO `user_config` (`user_config_id`, `user_name`, `authority`, `commodity_page_size`, `purchase_case_page_size`, `sell_case_page_size`, `store_page_size`, `create_time`, `last_modify_time`) VALUES
	('1', 'jersey', 'ADMIN', 100, 100, 100, 100, '2016-08-12 01:01:35', '2016-08-12 01:01:39');
/*!40000 ALTER TABLE `user_config` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
