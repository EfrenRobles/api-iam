CREATE TABLE client_scopes (
	client_id uuid NOT NULL,
	user_id uuid NOT NULL,
	scope_id uuid NOT NULL,
	CONSTRAINT client_scope_pk PRIMARY KEY (client_id,user_id,scope_id),
	CONSTRAINT client_scope_fk FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE ON UPDATE cascade,
	CONSTRAINT client_scope_fk_1 FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE cascade,
	CONSTRAINT client_scope_fk_2 FOREIGN KEY (scope_id) REFERENCES scopes(scope_id) ON DELETE CASCADE ON UPDATE CASCADE
);