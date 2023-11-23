CREATE TABLE bank (
  id BIGINT AUTO_INCREMENT NOT NULL,
   address VARCHAR(255) NULL,
   CONSTRAINT pk_bank PRIMARY KEY (id)
);

CREATE TABLE bank_account (
  id BIGINT AUTO_INCREMENT NOT NULL,
   balance DOUBLE NULL,
   account_number INT NULL,
   bank_id BIGINT NULL,
   CONSTRAINT pk_bankaccount PRIMARY KEY (id)
);

ALTER TABLE bank_account ADD CONSTRAINT uc_bankaccount_accountnumber UNIQUE (account_number);

ALTER TABLE bank_account ADD CONSTRAINT FK_BANKACCOUNT_ON_BANK FOREIGN KEY (bank_id) REFERENCES bank (id);
