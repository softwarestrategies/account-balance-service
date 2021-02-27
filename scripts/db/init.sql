CREATE DATABASE account_balance;
CREATE USER account_balance_admin with encrypted password 'changeme';
GRANT ALL PRIVILEGES ON DATABASE account_balance to account_balance_admin;

\c account_balance account_balance_admin

CREATE TABLE account
(
    id UUID,
    version INT DEFAULT 1,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    modified_on TIMESTAMP DEFAULT now() NOT NULL,
    name VARCHAR(50) NOT NULL,
    balance NUMERIC(11,2) DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id SERIAL PRIMARY KEY,
    version INT DEFAULT 1,
    account_id UUID NOT NULL,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    modified_on TIMESTAMP DEFAULT now() NOT NULL,
    type VARCHAR(10) NOT NULL,
    amount NUMERIC(11,2) DEFAULT 0,
    FOREIGN KEY (account_id) REFERENCES account (id)
);

INSERT INTO account(id, name, balance) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', 'first account', 4500.00);
INSERT INTO account(id, name) VALUES ('a2bcfe2a-78b7-11eb-9439-0242ac130002', 'second account');

INSERT INTO transaction(id, account_id, type, amount) VALUES (1, 'a2bcfe2a-78b7-11eb-9439-0242ac130002', 'DEPOSIT', 5000.00);
INSERT INTO transaction(id, account_id, type, amount) VALUES (2, 'a2bcfe2a-78b7-11eb-9439-0242ac130002', 'WITHDRAW', 500.00);
