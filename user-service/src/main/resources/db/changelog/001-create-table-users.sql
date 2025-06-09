--liquibase formatted sql

--changeset gigaden:001-create-schema
CREATE SCHEMA IF NOT EXISTS entities;

--changeset gigaden:001-create-table-users
CREATE TABLE entities.users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    company_id BIGINT,
    created_on TIMESTAMP DEFAULT current_timestamp
);