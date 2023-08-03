--liquibase formatted sql

--changeset probzyg:1

create table airport(
                        country varchar(255) NOT NULL ,
                        city varchar(255) NOT NULL ,
                        airport varchar(255) NOT NULL PRIMARY KEY
)