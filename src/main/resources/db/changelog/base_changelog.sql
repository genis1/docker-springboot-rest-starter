-- liquibase formatted sql

-- changeset genis.guillem.mimo:create-table-customers
CREATE TABLE `docker_starter`.`customers`
(
    `id`   INT          NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
);
