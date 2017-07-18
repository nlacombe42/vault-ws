CREATE TABLE `account` (
  `account_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`    INT(10) UNSIGNED NOT NULL,
  `name`       VARCHAR(100)     NOT NULL,
  PRIMARY KEY (`account_id`)
);

CREATE TABLE `category` (
  `category_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(100)     NOT NULL,
  `user_id`     INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`category_id`)
);

CREATE TABLE `transaction` (
  `transaction_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id`     INT(10) UNSIGNED NOT NULL,
  `datetime`       TIMESTAMP        NOT NULL,
  `description`    TEXT             NOT NULL,
  `amount`         DECIMAL(15, 2)   NOT NULL,
  `category_id`    INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `fk_transaction_account_idx` (`account_id`),
  KEY `fk_transaction_category_idx` (`category_id`),
  CONSTRAINT `fk_transaction_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
