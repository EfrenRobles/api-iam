CREATE TABLE client_roles (
	client_id uuid NOT NULL,
	user_id uuid NOT NULL,
	role_id uuid NOT NULL,
	CONSTRAINT client_role_pk PRIMARY KEY (client_id,user_id,role_id),
	CONSTRAINT client_role_fk FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE ON UPDATE cascade,
	CONSTRAINT client_role_fk_1 FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE cascade,
	CONSTRAINT client_role_fk_2 FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE ON UPDATE CASCADE
);
