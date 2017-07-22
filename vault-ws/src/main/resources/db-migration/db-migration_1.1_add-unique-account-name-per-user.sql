ALTER TABLE `account`
  ADD CONSTRAINT UNIQUE KEY `uq_account_name_user` (`user_id`, `name`);
