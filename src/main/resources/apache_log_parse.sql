-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 07, 2016 at 01:20 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `apache_log_parse`
--

-- --------------------------------------------------------

--
-- Table structure for table `ip_address_location`
--

CREATE TABLE IF NOT EXISTS `ip_address_location` (
  `ip_address` varchar(20) DEFAULT NULL,
  `country_code` varchar(2) DEFAULT NULL,
  `country_name` varchar(60) DEFAULT NULL,
  `region` varchar(2) DEFAULT NULL,
  `region_name` varchar(60) DEFAULT NULL,
  `city` varchar(60) DEFAULT NULL,
  `postal_code` varchar(32) DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `dma_code` int(11) DEFAULT NULL,
  `area_code` int(11) DEFAULT NULL,
  `metro_code` int(11) DEFAULT NULL,
  `date_entered` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Stand-in structure for view `ip_cnt`
--
CREATE TABLE IF NOT EXISTS `ip_cnt` (
`ip_address` varchar(20)
,`tot_ip_cnt` bigint(21)
);
-- --------------------------------------------------------

--
-- Table structure for table `log_data`
--

CREATE TABLE IF NOT EXISTS `log_data` (
  `ip_address` varchar(20) DEFAULT NULL,
  `remote_user` char(10) DEFAULT NULL,
  `time_accessed` timestamp NULL DEFAULT NULL,
  `request` varchar(1000) DEFAULT NULL,
  `stat_cd` int(11) DEFAULT NULL,
  `bytes_sent` int(11) DEFAULT NULL,
  `time_entered` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `log_data`
--
DROP TRIGGER IF EXISTS `back_up_log_data`;
DELIMITER //
CREATE TRIGGER `back_up_log_data` AFTER INSERT ON `log_data`
 FOR EACH ROW insert into log_data_old (ip_address, remote_user, time_accessed, request, stat_cd, bytes_sent, time_entered)
    values (NEW.ip_address, NEW.remote_user, NEW.time_accessed, NEW.request, NEW.stat_cd, NEW.bytes_sent, NEW.time_entered)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `log_data_old`
--

CREATE TABLE IF NOT EXISTS `log_data_old` (
  `ip_address` varchar(20) DEFAULT NULL,
  `remote_user` char(10) DEFAULT NULL,
  `time_accessed` timestamp NULL DEFAULT NULL,
  `request` varchar(1000) DEFAULT NULL,
  `stat_cd` int(11) DEFAULT NULL,
  `bytes_sent` int(11) DEFAULT NULL,
  `time_entered` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `time_accessed_day_cnt`
--

CREATE TABLE IF NOT EXISTS `time_accessed_day_cnt` (
  `time_accessed` date DEFAULT NULL,
  `one_am` int(11) DEFAULT NULL,
  `two_am` int(11) DEFAULT NULL,
  `three_am` int(11) DEFAULT NULL,
  `four_am` int(11) DEFAULT NULL,
  `five_am` int(11) DEFAULT NULL,
  `six_am` int(11) DEFAULT NULL,
  `seven_am` int(11) DEFAULT NULL,
  `eight_am` int(11) DEFAULT NULL,
  `nine_am` int(11) DEFAULT NULL,
  `ten_am` int(11) DEFAULT NULL,
  `eleven_am` int(11) DEFAULT NULL,
  `twelve_pm` int(11) DEFAULT NULL,
  `one_pm` int(11) DEFAULT NULL,
  `two_pm` int(11) DEFAULT NULL,
  `three_pm` int(11) DEFAULT NULL,
  `four_pm` int(11) DEFAULT NULL,
  `five_pm` int(11) DEFAULT NULL,
  `six_pm` int(11) DEFAULT NULL,
  `seven_pm` int(11) DEFAULT NULL,
  `eight_pm` int(11) DEFAULT NULL,
  `nine_pm` int(11) DEFAULT NULL,
  `ten_pm` int(11) DEFAULT NULL,
  `eleven_pm` int(11) DEFAULT NULL,
  `twelve_am` int(11) DEFAULT NULL,
  `tot_day_cnt` int(11) DEFAULT NULL,
  `time_entered` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure for view `ip_cnt`
--
DROP TABLE IF EXISTS `ip_cnt`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ip_cnt` AS select `log_data`.`ip_address` AS `ip_address`,count(`log_data`.`ip_address`) AS `tot_ip_cnt` from `log_data` group by 1 order by 1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
