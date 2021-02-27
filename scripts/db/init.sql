CREATE DATABASE account_balance;
CREATE USER account_balance_admin with encrypted password 'changeme';
GRANT ALL PRIVILEGES ON DATABASE account_balance to account_balance_admin;

\c account_balance account_balance_admin

CREATE TABLE account
(
    account_num     UUID,
    version         INT DEFAULT 1,
    created_on      TIMESTAMP DEFAULT now() NOT NULL,
    modified_on     TIMESTAMP DEFAULT now() NOT NULL,
    name            VARCHAR(50) NOT NULL,
    PRIMARY KEY (account_num)
);

CREATE TABLE transaction
(
    id          SERIAL PRIMARY KEY,
    version     INT DEFAULT 1,
    account_num UUID NOT NULL,
    created_on  TIMESTAMP NOT NULL,
    updated_on  TIMESTAMP NOT NULL,
    type        VARCHAR(8) NOT NULL,
    FOREIGN KEY (account_num) REFERENCES account (account_num)
);
