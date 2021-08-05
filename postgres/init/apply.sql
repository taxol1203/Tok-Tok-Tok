CREATE TABLE "SSAFY_PJT1".chat_session
(
    session_id character varying NOT NULL,
    created_at timestamp with time zone NOT NULL,
    fk_created_by_idx integer NOT NULL,
    fk_client_idx integer,
    fk_host_idx integer,
    fk_permission_idx integer,
    status character varying(4),
    unread integer,
    qna_history character varying(1000) NULL,
    PRIMARY KEY (session_id)
);