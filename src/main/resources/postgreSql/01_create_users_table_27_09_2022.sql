CREATE TABLE users (
	user_id uuid NOT NULL,
	user_first_nale varchar NOT NULL,
	user_last_name varchar NULL,
	user_email varchar NOT NULL,
	user_password varchar NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (user_id),
	CONSTRAINT users_un UNIQUE (user_email)
);
