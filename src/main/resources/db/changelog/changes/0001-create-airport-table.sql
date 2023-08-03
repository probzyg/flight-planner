--liquibase formatted sql

--changeset probzyg:1

create table airport(
                        country varchar(255),
                        city varchar(255),
                        airport varchar(255) PRIMARY KEY
)