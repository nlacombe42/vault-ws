CREATE TABLE `user_config` (
  `user_config_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `timezone` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`user_config_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC)
);
