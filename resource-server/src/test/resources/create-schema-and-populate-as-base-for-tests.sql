CREATE TABLE account
(
    id UUID,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    modified_on TIMESTAMP DEFAULT now() NOT NULL,
    name VARCHAR(50) NOT NULL,
    balance NUMERIC(11,2) DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id SERIAL PRIMARY KEY,
    account_id UUID NOT NULL,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    type VARCHAR(10) NOT NULL,
    amount NUMERIC(11,2) DEFAULT 0,
    FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE INDEX idx_transaction_account_type ON transaction(account_id, type);
CREATE INDEX idx_transaction_account_created ON transaction(account_id, created_on);
CREATE INDEX idx_transaction_account_created_type ON transaction(account_id, created_on, type);

INSERT INTO account(id, name, balance) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', 'first account',  4316.00);
INSERT INTO account(id, name, balance) VALUES ('27e85b94-791c-11eb-9439-0242ac130002', 'second account', 2500.00);

INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '35 DAY' , 'DEPOSIT',  5000.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '29 DAY' , 'DEPOSIT',  5000.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '12 DAY' , 'DEPOSIT',  5000.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '12 DAY' , 'DEPOSIT',  5000.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '12 DAY' , 'DEPOSIT',  5000.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '11 DAY' , 'WITHDRAW',  500.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '6 DAY' ,  'DEPOSIT',   100.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '5 DAY' ,  'WITHDRAW',  300.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW() - INTERVAL '4 DAY' ,  'DEPOSIT',    16.00);
INSERT INTO transaction(account_id, created_on, type, amount) VALUES ('4fa3179e-78b5-11eb-9439-0242ac130002', NOW(),  'DEPOSIT',    6.00);
