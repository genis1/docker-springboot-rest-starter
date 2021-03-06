-- liquibase formatted sql

-- changeset genis.guillem.mimo:create-table-customers
CREATE TABLE `customers`
(
    `id`   INT          NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
);
-- changeset genis.guillem.mimo:create-table-versions
CREATE TABLE `versions`
(
    `id`   INT          NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
);