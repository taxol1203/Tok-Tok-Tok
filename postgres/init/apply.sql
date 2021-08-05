CREATE TABLE "SSAFY_PJT1".chat_session (
	session_id varchar NOT NULL,
	created_at timestamptz NOT NULL,
	fk_created_by_idx int4 NOT NULL,
	fk_client_idx int4 NULL,
	fk_host_idx int4 NULL,
	fk_permission_idx int4 NULL,
	status varchar(4) NULL,
	unread int4 NULL,
	qna_history varchar(1000) NULL
);