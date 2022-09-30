CREATE TABLE public.user_clients (
	user_id uuid NOT NULL,
	client_id uuid NOT NULL,
	"role" varchar NOT NULL,
	scopes jsonb NOT NULL,
	CONSTRAINT client_scopes_pk PRIMARY KEY (user_id,client_id),
	CONSTRAINT client_scopes_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT client_scopes_fk_1 FOREIGN KEY (client_id) REFERENCES public.clients(client_id) ON DELETE CASCADE ON UPDATE CASCADE
);
