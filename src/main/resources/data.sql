INSERT INTO student(id, first_name, last_name, age, email, password)
VALUES ('3a2fdca3-657a-4cbf-82c2-9a1154309a00', 'Taleh', 'Qasimli', 19, 'taleh.qasimli@gmail.com',
        '$2a$12$FtcANvpQWOVkQzDgqAb5BOIhYTorI7uBiivVEpDKnIWx/EOpVhI0K');
INSERT INTO student(id, first_name, last_name, age, email, password)
VALUES ('dfdadc17-dcb4-4ebd-bda1-dad1b202d121', 'Senan', 'Hatamov', 20, 'senan.hatamov@gmail.com',
        '$2a$12$FtcANvpQWOVkQzDgqAb5BOIhYTorI7uBiivVEpDKnIWx/EOpVhI0K');

-- If you don't give id column then it can't generate auto increment
-- password --> 12345

DROP TABLE if EXISTS oauth_client_details CASCADE ;
CREATE TABLE oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

DROP TABLE if EXISTS oauth_client_token CASCADE;
CREATE TABLE oauth_client_token
(
    token_id          VARCHAR(256),
    token             bytea,
    authentication_id VARCHAR(256),
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

DROP TABLE if EXISTS oauth_access_token CASCADE;
CREATE TABLE oauth_access_token
(
    token_id          VARCHAR(256),
    token             bytea,
    authentication_id VARCHAR(256),
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    bytea,
    refresh_token     VARCHAR(256)
);

DROP TABLE if EXISTS oauth_refresh_token CASCADE;
CREATE TABLE oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          bytea,
    authentication bytea
);

DROP TABLE if EXISTS oauth_code CASCADE;
CREATE TABLE oauth_code
(
    code           VARCHAR(256),
    authentication bytea
);