CREATE TABLE user_clients (
	user_id uuid NOT NULL,
	client_id uuid NOT NULL,
	role_id uuid NOT NULL,
	scopes jsonb NOT NULL,
	CONSTRAINT client_scopes_pk PRIMARY KEY (user_id,client_id),
	CONSTRAINT client_scopes_fk FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT client_scopes_fk_1 FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE ON UPDATE cascade,
	CONSTRAINT client_scopes_fk_2 FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE ON UPDATE CASCADE
);
