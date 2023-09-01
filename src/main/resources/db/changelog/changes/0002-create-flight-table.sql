--liquibase formatted sql

--changeset probzyg:2

create table flight(
                       id serial PRIMARY KEY,
                       from_airport VARCHAR(255) references airport(airport),
                       to_airport VARCHAR(255) references airport(airport),
                       carrier VARCHAR(255) NOT NULL,
                       departure_time TIMESTAMP NOT NULL,
                       arrival_time TIMESTAMP NOT NULL
)