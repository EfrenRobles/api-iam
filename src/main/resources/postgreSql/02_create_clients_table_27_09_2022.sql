CREATE TABLE clients (
	client_id uuid not NULL,
	client_code varchar NOT NULL,
	client_name varchar NOT NULL,
	CONSTRAINT client_pk PRIMARY KEY (client_id)
);