CREATE DATABASE sample WITH ENCODING 'UTF8';
CREATE USER postgres ENCRYPTED PASSWORD 'root1!!!!!!!!!' NOSUPERUSER NOCREATEDB NOCREATEROLE;
GRANT ALL PRIVILEGES ON DATABASE crawling TO mis;

-- \connect sample

CREATE TABLE public.account (
	idx int4 NOT NULL DEFAULT nextval('user_idx_seq'::regclass),
	passwd varchar NOT NULL,
	email varchar NOT NULL,
	CONSTRAINT account_pk PRIMARY KEY (idx)
);

INSERT INTO account ("email", "passwd")
VALUES ('taxol1203@naver.com', 'daebal!!!!2222');