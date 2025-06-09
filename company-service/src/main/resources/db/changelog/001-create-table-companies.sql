--liquibase formatted sql

--changeset gigaden:001-create-schema
CREATE SCHEMA IF NOT EXISTS entities;

--changeset gigaden:001-create-table-companies
CREATE TABLE entities.companies (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    budget NUMERIC NOT NULL,
    created_on TIMESTAMP DEFAULT current_timestamp
);