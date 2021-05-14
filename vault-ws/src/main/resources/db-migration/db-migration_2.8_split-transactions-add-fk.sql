alter table `transaction`
    add constraint `fk_transaction_parent_transaction` foreign key (`parent_transaction_id`) references `parent_transaction` (`parent_transaction_id`);
