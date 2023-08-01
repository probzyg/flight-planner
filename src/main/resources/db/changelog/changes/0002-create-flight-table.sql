--liquibase formatted sql

--changeset probzyg:2

create table flight(
                       id serial PRIMARY KEY,
                       from_airport VARCHAR(255) references airport(airport) NOT NULL,
                       to_airport VARCHAR(255) references airport(airport) NOT NULL,
                       carrier VARCHAR(255) NOT NULL,
                       departure_time VARCHAR(255) NOT NULL,
                       arrival_time VARCHAR(255) NOT NULL
)