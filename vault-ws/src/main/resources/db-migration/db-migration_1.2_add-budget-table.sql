CREATE TABLE `budget` (
  `budget_id`      INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `start_date`     TIMESTAMP        NOT NULL,
  `end_date`       TIMESTAMP        NOT NULL,
  `amount`         DECIMAL(15, 2)   NOT NULL,
  `category_id`    INT(10) UNSIGNED NOT NULL,
  `user_id`        INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`budget_id`),
  KEY `fk_budget_category_idx` (`category_id`),
  CONSTRAINT `fk_budget_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
