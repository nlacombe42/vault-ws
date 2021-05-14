create table `parent_transaction`
(
    `parent_transaction_id` int(10) unsigned not null,
    `account_id`            int(10) unsigned not null,
    `datetime`              timestamp      not null,
    `description`           text           not null,
    `amount`                decimal(15, 2) not null,
    primary key (`parent_transaction_id`),
    key                     `fk_parent_transaction_account_idx` (`account_id`),
    constraint `fk_parent_transaction_account_idx` foreign key (`account_id`) references `account` (`account_id`)
        on delete no action
        on update no action
);

alter table `transaction`
    add column `parent_transaction_id` int(10) unsigned null default null;
