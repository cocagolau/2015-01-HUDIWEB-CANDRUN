-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `test` ;

-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `test` ;

-- -----------------------------------------------------
-- Table `test`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test`.`user` ;

CREATE TABLE IF NOT EXISTS `test`.`user` (
  `email` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `state` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test`.`goal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test`.`goal` ;

CREATE TABLE IF NOT EXISTS `test`.`goal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contents` VARCHAR(255) NOT NULL,
  `user_email` VARCHAR(255) NOT NULL,
  `start_date` TIMESTAMP NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mod_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` TINYINT NOT NULL DEFAULT 0,
  `success_days` INT NOT NULL DEFAULT 0,
  `combo` INT NOT NULL DEFAULT 0,
  `max_combo` INT NOT NULL DEFAULT 0,
  `achievement` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_goal_user_idx` (`user_email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test`.`tasks`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test`.`tasks` ;

CREATE TABLE IF NOT EXISTS `test`.`tasks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contents` VARCHAR(255) NOT NULL,
  `nudge` INT NOT NULL DEFAULT 0,
  `praise` INT NOT NULL DEFAULT 0,
  `combo` INT NOT NULL DEFAULT 0,
  `success_days` INT NOT NULL DEFAULT 0,
  `max_combo` INT NOT NULL DEFAULT 0,
  `goal_id` INT NOT NULL DEFAULT 0,
  `achievement` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_objectives_goal1_idx` (`goal_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test`.`user_has_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test`.`user_has_user` ;

CREATE TABLE IF NOT EXISTS `test`.`user_has_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `requester` VARCHAR(255) NOT NULL,
  `receiver` VARCHAR(255) NOT NULL,
  `state` VARCHAR(45) NOT NULL DEFAULT 0,
  `ask_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `complete_date` TIMESTAMP NULL,
  INDEX `fk_user_has_user_user2_idx` USING BTREE (`receiver` ASC),
  INDEX `fk_user_has_user_user1_idx` USING BTREE (`requester` ASC),
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `test`.`user_has_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test`.`user_has_user` ;

CREATE TABLE IF NOT EXISTS `test`.`preliminary_user` (
  `email` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `verify_key` varchar(255) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
