--liquibase formatted sql
--changeset rmorenko:create_book_table
create table Book
(
    book_id    long not null primary key,
    book_name  varchar(100) not null
);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;