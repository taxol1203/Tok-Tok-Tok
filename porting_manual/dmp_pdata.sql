--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

DROP DATABASE "SSAFY_PJT1";




--
-- Drop roles
--

DROP ROLE padmin;
DROP ROLE postgres;


--
-- Roles
--

CREATE ROLE padmin;
ALTER ROLE padmin WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'md5812484f4d04e105d3c91bf1aabff36de';
CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'md53d28fe5137e6e0147de964b9d6a15cea';






--
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3 (Debian 13.3-1.pgdg100+1)
-- Dumped by pg_dump version 13.3 (Debian 13.3-1.pgdg100+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "SSAFY_PJT1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3 (Debian 13.3-1.pgdg100+1)
-- Dumped by pg_dump version 13.3 (Debian 13.3-1.pgdg100+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: SSAFY_PJT1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "SSAFY_PJT1" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE "SSAFY_PJT1" OWNER TO postgres;

\connect "SSAFY_PJT1"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: SSAFY_PJT1; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA "SSAFY_PJT1";


ALTER SCHEMA "SSAFY_PJT1" OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".account (
    email character varying NOT NULL,
    pk_idx integer NOT NULL,
    passwd character varying NOT NULL,
    username character varying NOT NULL
);


ALTER TABLE "SSAFY_PJT1".account OWNER TO postgres;

--
-- Name: account_pk_idx_seq; Type: SEQUENCE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE SEQUENCE "SSAFY_PJT1".account_pk_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "SSAFY_PJT1".account_pk_idx_seq OWNER TO postgres;

--
-- Name: account_pk_idx_seq; Type: SEQUENCE OWNED BY; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER SEQUENCE "SSAFY_PJT1".account_pk_idx_seq OWNED BY "SSAFY_PJT1".account.pk_idx;


--
-- Name: answer; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".answer (
    pk_idx integer NOT NULL,
    content character varying NOT NULL,
    fk_next_idx integer DEFAULT 2,
    fk_previous_idx integer
);


ALTER TABLE "SSAFY_PJT1".answer OWNER TO postgres;

--
-- Name: answer_pk_idx_seq; Type: SEQUENCE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE SEQUENCE "SSAFY_PJT1".answer_pk_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "SSAFY_PJT1".answer_pk_idx_seq OWNER TO postgres;

--
-- Name: answer_pk_idx_seq; Type: SEQUENCE OWNED BY; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER SEQUENCE "SSAFY_PJT1".answer_pk_idx_seq OWNED BY "SSAFY_PJT1".answer.pk_idx;


--
-- Name: chat_message; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".chat_message (
    pk_idx integer NOT NULL,
    message text NOT NULL,
    fk_author_idx integer NOT NULL,
    created timestamp with time zone NOT NULL,
    deleted boolean,
    fk_session_id character varying NOT NULL,
    type character varying(4)
);


ALTER TABLE "SSAFY_PJT1".chat_message OWNER TO postgres;

--
-- Name: chat_message_pk_idx_seq; Type: SEQUENCE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE SEQUENCE "SSAFY_PJT1".chat_message_pk_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "SSAFY_PJT1".chat_message_pk_idx_seq OWNER TO postgres;

--
-- Name: chat_message_pk_idx_seq; Type: SEQUENCE OWNED BY; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER SEQUENCE "SSAFY_PJT1".chat_message_pk_idx_seq OWNED BY "SSAFY_PJT1".chat_message.pk_idx;


--
-- Name: chat_session; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".chat_session (
    session_id character varying NOT NULL,
    created_at timestamp with time zone NOT NULL,
    fk_created_by_idx integer NOT NULL,
    fk_client_idx integer,
    fk_host_idx integer,
    fk_permission_idx integer,
    status character varying(4),
    unread integer,
    qna_history character varying(1000)
);


ALTER TABLE "SSAFY_PJT1".chat_session OWNER TO postgres;

--
-- Name: permission; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".permission (
    pk_idx integer NOT NULL,
    name character varying NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    fk_role_idx integer
);


ALTER TABLE "SSAFY_PJT1".permission OWNER TO postgres;

--
-- Name: permission_pk_idx_seq; Type: SEQUENCE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE SEQUENCE "SSAFY_PJT1".permission_pk_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "SSAFY_PJT1".permission_pk_idx_seq OWNER TO postgres;

--
-- Name: permission_pk_idx_seq; Type: SEQUENCE OWNED BY; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER SEQUENCE "SSAFY_PJT1".permission_pk_idx_seq OWNED BY "SSAFY_PJT1".permission.pk_idx;


--
-- Name: question; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".question (
    pk_idx integer NOT NULL,
    content character varying,
    title character varying
);


ALTER TABLE "SSAFY_PJT1".question OWNER TO postgres;

--
-- Name: question_pk_idx_seq; Type: SEQUENCE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE SEQUENCE "SSAFY_PJT1".question_pk_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "SSAFY_PJT1".question_pk_idx_seq OWNER TO postgres;

--
-- Name: question_pk_idx_seq; Type: SEQUENCE OWNED BY; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER SEQUENCE "SSAFY_PJT1".question_pk_idx_seq OWNED BY "SSAFY_PJT1".question.pk_idx;


--
-- Name: role; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".role (
    pk_idx integer NOT NULL,
    name character varying NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    admin boolean
);


ALTER TABLE "SSAFY_PJT1".role OWNER TO postgres;

--
-- Name: role_pk_idx_seq; Type: SEQUENCE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE SEQUENCE "SSAFY_PJT1".role_pk_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "SSAFY_PJT1".role_pk_idx_seq OWNER TO postgres;

--
-- Name: role_pk_idx_seq; Type: SEQUENCE OWNED BY; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER SEQUENCE "SSAFY_PJT1".role_pk_idx_seq OWNED BY "SSAFY_PJT1".role.pk_idx;


--
-- Name: service_case; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".service_case (
    pk_idx integer NOT NULL,
    title character varying NOT NULL,
    link character varying,
    fk_charge_idx integer NOT NULL,
    fk_customer_idx integer NOT NULL,
    fk_session_id character varying NOT NULL,
    feedback integer
);


ALTER TABLE "SSAFY_PJT1".service_case OWNER TO postgres;

--
-- Name: service_case_pk_idx_seq; Type: SEQUENCE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE SEQUENCE "SSAFY_PJT1".service_case_pk_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "SSAFY_PJT1".service_case_pk_idx_seq OWNER TO postgres;

--
-- Name: service_case_pk_idx_seq; Type: SEQUENCE OWNED BY; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER SEQUENCE "SSAFY_PJT1".service_case_pk_idx_seq OWNED BY "SSAFY_PJT1".service_case.pk_idx;


--
-- Name: user_roles; Type: TABLE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE TABLE "SSAFY_PJT1".user_roles (
    pk_idx integer NOT NULL,
    fk_user_idx integer,
    fk_role_idx integer
);


ALTER TABLE "SSAFY_PJT1".user_roles OWNER TO postgres;

--
-- Name: user_roles_pk_idx_seq; Type: SEQUENCE; Schema: SSAFY_PJT1; Owner: postgres
--

CREATE SEQUENCE "SSAFY_PJT1".user_roles_pk_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "SSAFY_PJT1".user_roles_pk_idx_seq OWNER TO postgres;

--
-- Name: user_roles_pk_idx_seq; Type: SEQUENCE OWNED BY; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER SEQUENCE "SSAFY_PJT1".user_roles_pk_idx_seq OWNED BY "SSAFY_PJT1".user_roles.pk_idx;


--
-- Name: account pk_idx; Type: DEFAULT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".account ALTER COLUMN pk_idx SET DEFAULT nextval('"SSAFY_PJT1".account_pk_idx_seq'::regclass);


--
-- Name: answer pk_idx; Type: DEFAULT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".answer ALTER COLUMN pk_idx SET DEFAULT nextval('"SSAFY_PJT1".answer_pk_idx_seq'::regclass);


--
-- Name: chat_message pk_idx; Type: DEFAULT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_message ALTER COLUMN pk_idx SET DEFAULT nextval('"SSAFY_PJT1".chat_message_pk_idx_seq'::regclass);


--
-- Name: permission pk_idx; Type: DEFAULT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".permission ALTER COLUMN pk_idx SET DEFAULT nextval('"SSAFY_PJT1".permission_pk_idx_seq'::regclass);


--
-- Name: question pk_idx; Type: DEFAULT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".question ALTER COLUMN pk_idx SET DEFAULT nextval('"SSAFY_PJT1".question_pk_idx_seq'::regclass);


--
-- Name: role pk_idx; Type: DEFAULT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".role ALTER COLUMN pk_idx SET DEFAULT nextval('"SSAFY_PJT1".role_pk_idx_seq'::regclass);


--
-- Name: service_case pk_idx; Type: DEFAULT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".service_case ALTER COLUMN pk_idx SET DEFAULT nextval('"SSAFY_PJT1".service_case_pk_idx_seq'::regclass);


--
-- Name: user_roles pk_idx; Type: DEFAULT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".user_roles ALTER COLUMN pk_idx SET DEFAULT nextval('"SSAFY_PJT1".user_roles_pk_idx_seq'::regclass);


--
-- Data for Name: account; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".account (email, pk_idx, passwd, username) FROM stdin;
admin@naver.com	1	$2a$10$zdrT8RCqvn50SP3xHXFiJufYjWPpMaf0rbtgYcnsTqfL/Mpt45/Wi	admin
user12@naver.com	2	$2a$10$AN1qSl54UK1e8HeigqDRuup9FooYadC1.mArW1dnqvkJ//7b6MGgi	user
sobin@gmail.com	3	$2a$10$sNrDKNrvht1nIXwH..KeK.6x/zrw1KvR.n3Cg47m4zrRQoxrNjUCG	소빈
ms13@nate.com	4	$2a$10$SVTAP/xqxrYh//kQRsaP7u88X96WZXPRRDxjZBHkOvuvy8RYq7Xaa	민수
user14@naver.com	5	$2a$10$uOast97DMIEZhO0OfDrxHuIzZXgTeE07ELfoMXnqw/.iyE/KOJ/.u	대연
user15@naver.com	6	$2a$10$.t/l3vYFTWdkvktqUWnf8eizZDQXJQlPfz9bMan1p0TnjWih8i2Ty	주연
user16@naver.com	7	$2a$10$KdD8SCqJ1aLnSVhx49ayc.kN/Dt.c6U7jT12hc2T3BpSJflj5Zs1G	일민
user17@gmail.com	8	$2a$10$TtWnniMNoiwSeZ3KKcEaVOPIiImy5CxkV0P2xqOdMUUBOBFug.xkS	Ilmin
user18@hanmail.net	9	$2a$10$HlHBUUE9DoRUVtAa7eT0fuMGvnK38uDFQ.VJLZ2V.vFfDqSXPS5Qm	dohyung
user19@nate.com	10	$2a$10$O4md8W1U1uMvTWpkLZWFT.DdBD93zG/8ENzmwqQ9qU.mfs2j4yK/C	MinSu
user20@nate.com	11	$2a$10$oxoV6pCRGZFxDRKdx/FQieesk.70JOLVojqZxDEkhu2ce8nm1lJpC	Daeyeon
\.


--
-- Data for Name: answer; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".answer (pk_idx, content, fk_next_idx, fk_previous_idx) FROM stdin;
94	화면이 안켜져요	90	88
95	화면이 너무 어두워요	89	88
96	네 충분합니다	2	90
97	네 충분합니다	2	89
44	1234	2	\N
22	노트북	10	9
45	1234	2	\N
98	asdf	7	\N
54	네 충분합니다.	2	46
58	asdf	5	\N
100	wer	8	\N
99	asdf	7	\N
6	❤ 네 충분합니다	2	4
17	네 충분합니다	2	7
101	adsf	11	\N
20	네 충분합니다	2	8
25	네 충분합니다	2	11
66	fdsa	5	\N
68	adsfadf	8	\N
11	다운로드하는 데 문제가 생겼어요	8	6
10	Office 제품 키는 어디서 찾을 수 있나요	7	6
1	Winodws 관련 문의	3	1
2	Office 관련 문의	6	1
3	전자기기 관련 문의	9	1
76	asdf	2	\N
42	네 충분합니다.	2	\N
72	asdf	4	\N
73	adf	8	\N
34	네 해결되었습니다. 👍	2	\N
21	모니터	\N	9
30	화면이 너무 어두워요	\N	\N
29	화면이 안켜져요	\N	\N
80	test	4	\N
83	afd	4	\N
4	🤔 Windows 제품 키는 어떻게 찾을 수 있나요	4	3
5	32비트/64 비트 차이를 모르겠어요	5	3
87	adsfa	7	\N
9	네 충분합니다	2	5
90	노트북이 너무 뜨거워요	46	10
23	전원이 안켜져요	11	10
\.


--
-- Data for Name: chat_message; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".chat_message (pk_idx, message, fk_author_idx, created, deleted, fk_session_id, type) FROM stdin;
1		2	2021-08-19 14:40:04.424372+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	JOIN
2	안녕하세요 고객님	1	2021-08-19 14:40:13.088075+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	MSG
3	어떤 문제가 있으실까요?	1	2021-08-19 14:40:17.649391+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	MSG
4	오피스 제품키를 입력하라고 하는데 어딨는지 못찾겠어요	2	2021-08-19 14:40:21.640696+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	MSG
5	패키지 어디에 있다는 건가요?	2	2021-08-19 14:40:27.567308+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	MSG
6	아 네. 	1	2021-08-19 14:40:30.953378+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	MSG
7	화상상담으로 패키지 위치 찾아드려도 될까요?	1	2021-08-19 14:40:36.464212+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	MSG
8	네	2	2021-08-19 14:40:38.344499+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	MSG
9	화상상담을 요청합니다.	1	2021-08-19 14:40:40.650393+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	VID
10		2	2021-08-19 14:41:08.795615+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	END
11		2	2021-08-19 14:41:15.076743+09	f	a58a2d5a-4887-44bc-ba1a-304410ba5604	END
12		2	2021-08-19 14:41:26.050754+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	JOIN
13	안녕하세요 고객님	1	2021-08-19 14:41:33.221268+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
14	Office 관련 문의 맞으실까요?	1	2021-08-19 14:41:40.645183+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
15	네. 오피스 제품키를 입력하라고 하는데 어딨는지 못찾겠어요	2	2021-08-19 14:41:48.066801+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
16	패키지 어디에 있다는 건가요?	2	2021-08-19 14:41:52.829204+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
17	네.	1	2021-08-19 14:42:00.027755+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
18	화상상담으로 패키지 위치 찾아드려도 될까요?	1	2021-08-19 14:42:06.011884+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
19	네	2	2021-08-19 14:42:07.448796+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
20	화상상담을 요청합니다.	1	2021-08-19 14:42:12.106746+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	VID
21	감사합니다~	1	2021-08-19 14:43:14.760748+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
22	감사합니다	2	2021-08-19 14:43:21.142045+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	MSG
23		2	2021-08-19 14:43:49.513522+09	f	96b6e8fb-0269-4beb-82f9-439f19447f4a	END
24		2	2021-08-19 14:44:43.0772+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	JOIN
25	 안녕하세요. 고객님 어떤 문제가 있으실까요?	1	2021-08-19 14:44:54.593998+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	MSG
26	오피스 제품키를 입력하라고 하는데 어딨는지 못찾겠어요	2	2021-08-19 14:45:05.645376+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	MSG
27	패키지 어디에 있다는 건가요?	2	2021-08-19 14:45:11.14897+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	MSG
28	네. 화상상담으로 패키지 위치 찾아드려도될까요?	1	2021-08-19 14:45:12.560442+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	MSG
29	네	2	2021-08-19 14:45:14.289795+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	MSG
30	화상상담을 요청합니다.	1	2021-08-19 14:45:15.725716+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	VID
31	감사합니다	2	2021-08-19 14:46:11.938184+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	MSG
32	감사합니다	1	2021-08-19 14:46:13.419632+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	MSG
33		2	2021-08-19 14:46:16.271006+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	END
34		2	2021-08-19 14:47:37.237828+09	f	6c0346db-8f06-4fcb-8182-eafd77233e97	JOIN
35		2	2021-08-19 14:47:37.317548+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	JOIN
36	안녕하세요. 고객님	1	2021-08-19 14:47:59.357059+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
37	Office 관련 문의 맞으실까요?	1	2021-08-19 14:48:02.866934+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
38	오피스 제품키를 입력하라고 하는데	2	2021-08-19 14:48:08.434517+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
39	어딨는지 못찾겠어요	2	2021-08-19 14:48:11.689881+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
40	패키지 어디에 있다는건가요?	2	2021-08-19 14:48:15.223485+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
41	네.	1	2021-08-19 14:48:17.164857+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
42	화상상담으로 패키지 위치 찾아드려도 될까요?	1	2021-08-19 14:48:18.803094+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
43	네	2	2021-08-19 14:48:20.935076+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
44	화상상담을 요청합니다.	1	2021-08-19 14:48:22.414133+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	VID
45	감사합니다~	1	2021-08-19 14:49:10.897849+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
46	넵	2	2021-08-19 14:49:14.05401+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	MSG
47		2	2021-08-19 14:49:16.939277+09	f	b617021e-feb1-4f3c-81f7-6ac94b28897e	END
48		3	2021-08-19 16:23:13.649548+09	f	d00cd24e-b8c4-45ab-b64e-5539942d184d	JOIN
49	안녕하세요. 고객님	2	2021-08-19 16:23:31.326588+09	f	d00cd24e-b8c4-45ab-b64e-5539942d184d	MSG
50	Windows 비트 관련해서 질문 주신거 맞으실까요?	2	2021-08-19 16:23:39.636223+09	f	d00cd24e-b8c4-45ab-b64e-5539942d184d	MSG
51	네	3	2021-08-19 16:23:44.437505+09	f	d00cd24e-b8c4-45ab-b64e-5539942d184d	MSG
52	두 개 비트가 뭐가 다른지 잘 모르겠어요	3	2021-08-19 16:23:49.005052+09	f	d00cd24e-b8c4-45ab-b64e-5539942d184d	MSG
53	뭘 사면 좋을까요	3	2021-08-19 16:23:56.996562+09	f	d00cd24e-b8c4-45ab-b64e-5539942d184d	MSG
54		4	2021-08-19 16:25:08.287861+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	JOIN
55	안녕하세요. 고객님	2	2021-08-19 16:25:17.253688+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
56	어떻게 도와드릴까요?	2	2021-08-19 16:25:20.829813+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
57	제가 어제 오피스를 구매했는데	4	2021-08-19 16:25:27.613521+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
58	쓸 일이 없어져서 환불하고 싶습니다.	4	2021-08-19 16:25:32.620581+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
59	쓸일이 없어져서 환불하고 싶습니다.	4	2021-08-19 16:25:36.861483+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
60	아직 한 번도 사용하지 않았어요	4	2021-08-19 16:25:47.573471+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
61	네 고객님. 환불 관련해서 문의 주셨네요.	2	2021-08-19 16:25:52.873785+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
62	사용 기록을 저희가 확인해서 바로 말씀드리겠습니다.	2	2021-08-19 16:26:03.814353+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
63	잠시만 기다려주세요	2	2021-08-19 16:26:07.038223+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
64	네 확인했습니다. 제품키 0000-1234-828934-123 맞으실까요?	2	2021-08-19 16:26:10.853127+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
65	네 맞아요	4	2021-08-19 16:26:14.493819+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
66	고객님 이 제품키로 문서 작성하신 흔적이 있습니다. 맞으실까요?	2	2021-08-19 16:26:17.973783+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
67	그거는 테스트용으로 그냥 켜본거였어요	4	2021-08-19 16:26:22.149017+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
68	혹시 학생이시거나 학습에 저희 제품을 이용하셨나요?	2	2021-08-19 16:26:27.773734+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
69	그런 증명을 아래에 저희 메일로 보내주시면 환불 처리 도와드리겠습니다! admin@toktoktok.com	2	2021-08-19 16:26:33.037008+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
70	네 감사합니다.	4	2021-08-19 16:26:37.709498+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	MSG
71		4	2021-08-19 16:26:42.621926+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	END
72		5	2021-08-19 16:27:41.126395+09	f	d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	JOIN
73		5	2021-08-19 16:27:41.218522+09	f	4bacc533-bfc9-4d13-8b4d-950d647129ee	JOIN
74	안녕하세요. 고객님	2	2021-08-19 16:27:59.878622+09	f	4bacc533-bfc9-4d13-8b4d-950d647129ee	MSG
75	노트북 관련하여 문의 주셨네요.	2	2021-08-19 16:28:04.470195+09	f	4bacc533-bfc9-4d13-8b4d-950d647129ee	MSG
76	어떤 문제인지 말씀해 주시겠어요?	2	2021-08-19 16:28:09.613793+09	f	4bacc533-bfc9-4d13-8b4d-950d647129ee	MSG
77	제가 노트북을 사려고 생각 중인데	2	2021-08-19 16:28:15.382227+09	f	4bacc533-bfc9-4d13-8b4d-950d647129ee	MSG
78		2	2021-08-19 16:28:30.053799+09	f	4bacc533-bfc9-4d13-8b4d-950d647129ee	END
79		5	2021-08-19 16:28:33.636373+09	f	4bacc533-bfc9-4d13-8b4d-950d647129ee	END
80		5	2021-08-19 16:28:41.336614+09	f	4bacc533-bfc9-4d13-8b4d-950d647129ee	JOIN
81		5	2021-08-19 16:28:41.413764+09	f	a139d2fd-f993-4c29-84ac-380124691c8a	JOIN
82	안녕하세요. 고객님	2	2021-08-19 16:28:50.838958+09	f	a139d2fd-f993-4c29-84ac-380124691c8a	MSG
83	노트북 관련하여 문의 주셨네요.	2	2021-08-19 16:28:54.789749+09	f	a139d2fd-f993-4c29-84ac-380124691c8a	MSG
84	어떤 문제인지 말씀해 주시겠어요?	2	2021-08-19 16:28:58.014214+09	f	a139d2fd-f993-4c29-84ac-380124691c8a	MSG
85	제가 노트북을 사려고 생각 중인데	5	2021-08-19 16:29:01.070179+09	f	a139d2fd-f993-4c29-84ac-380124691c8a	MSG
86	Surface Laptop은 어떤지 여쭤보려고요	5	2021-08-19 16:29:05.405444+09	f	a139d2fd-f993-4c29-84ac-380124691c8a	MSG
87		6	2021-08-19 16:31:22.263898+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	JOIN
88	안녕하세요. 고객님	1	2021-08-19 16:34:02.200121+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	MSG
89	어떤 문제가 발생하셨나요?	1	2021-08-19 16:34:07.063203+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	MSG
90	제가 지난주에 ms 마우스를 주문했는데 아직 안오네요..	6	2021-08-19 16:34:21.303342+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	MSG
91	언제쯤 받을 수 있나요?	6	2021-08-19 16:34:26.447236+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	MSG
92	네 조회 결과 오늘 출고되었습니다! 	1	2021-08-19 16:34:32.65628+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	MSG
93	오늘이나 내일 중으로 받으실 수 있습니다.	1	2021-08-19 16:34:36.134942+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	MSG
94	늦어져서 죄송합니다. 더 궁금하신거 있으실까요?	1	2021-08-19 16:34:41.271708+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	MSG
95	아니요 감사합니다	6	2021-08-19 16:34:53.294547+09	f	4ba497f4-4d04-44a3-ae6a-2e22f44cace5	MSG
96		5	2021-08-19 16:35:36.03629+09	f	112e168e-2103-4b54-8589-964dbd9829ad	JOIN
97	안녕하세요. 고객님	1	2021-08-19 16:35:44.184076+09	f	112e168e-2103-4b54-8589-964dbd9829ad	MSG
98	노트북 관련하여 문의 주셨네요.	1	2021-08-19 16:35:50.544362+09	f	112e168e-2103-4b54-8589-964dbd9829ad	MSG
99	어떤 문제인지 말씀해 주시겠어요?	1	2021-08-19 16:35:54.432828+09	f	112e168e-2103-4b54-8589-964dbd9829ad	MSG
100	제가 노트북을 사려고 생각 중인데	5	2021-08-19 16:36:00.287467+09	f	112e168e-2103-4b54-8589-964dbd9829ad	MSG
101	Surface Laptop은 어떤지 여쭤보려고요	5	2021-08-19 16:36:04.903596+09	f	112e168e-2103-4b54-8589-964dbd9829ad	MSG
102		4	2021-08-19 16:39:31.646194+09	f	6f790c61-f73b-4570-b910-b06c45626d52	JOIN
103	안녕하세요. 고객님	1	2021-08-19 16:39:43.724867+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
104	어떻게 도와드릴까요?	1	2021-08-19 16:39:47.362649+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
105	제가 어제 오피스를 구매했는데	4	2021-08-19 16:39:50.660213+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
106	쓸일이 없어져서 환불하고 싶습니다.	4	2021-08-19 16:39:57.354486+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
107	아직 한 번도 사용하지 않았어요	4	2021-08-19 16:40:04.378825+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
108	네 고객님. 환불 관련해서 문의 주셨네요.	1	2021-08-19 16:40:08.642841+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
109	사용 기록을 저희가 확인해서 바로 말씀드리겠습니다.	1	2021-08-19 16:40:12.254924+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
110	잠시만 기다려주세요	1	2021-08-19 16:40:15.594407+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
111	네 확인했습니다. 제품키 0000-1234-828934-123 맞으실까요?	1	2021-08-19 16:40:19.245959+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
112	네 맞아요	4	2021-08-19 16:40:23.493023+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
113	고객님 이 제품키로 문서 작성하신 흔적이 있습니다.	1	2021-08-19 16:40:29.084739+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
114	맞으실까요?	1	2021-08-19 16:40:32.271789+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
115	그거는 테스트용으로 그냥 켜본거였어요	4	2021-08-19 16:40:38.004912+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
116	혹시 학생이시거나 학습에 저희 제품을 이용하셨나요?	1	2021-08-19 16:40:43.441414+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
117	그런 증명을 아래에 저희 메일로 보내주시면 환불 처리 도와드리겠습니다!	1	2021-08-19 16:40:48.321987+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
118	admin@toktoktok.com	1	2021-08-19 16:40:52.018962+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
119	admin@toktoktok.com	4	2021-08-19 16:40:56.524634+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
120	네 감사합니다.	4	2021-08-19 16:41:03.708631+09	f	6f790c61-f73b-4570-b910-b06c45626d52	MSG
121		3	2021-08-19 16:41:36.758494+09	f	3eec70b4-513d-4091-8ffb-964e5aabe235	JOIN
122	안녕하세요. 고객님	1	2021-08-19 16:41:49.340593+09	f	3eec70b4-513d-4091-8ffb-964e5aabe235	MSG
123	Windows 비트 관련해서 질문 주신거 맞으실까요?	1	2021-08-19 16:41:56.431056+09	f	3eec70b4-513d-4091-8ffb-964e5aabe235	MSG
124	네	3	2021-08-19 16:42:00.291635+09	f	3eec70b4-513d-4091-8ffb-964e5aabe235	MSG
125	두 개 비트가 뭐가 다른지 잘 모르겠어요	3	2021-08-19 16:42:04.594824+09	f	3eec70b4-513d-4091-8ffb-964e5aabe235	MSG
126	뭘 사면 좋을까요	3	2021-08-19 16:42:08.602792+09	f	3eec70b4-513d-4091-8ffb-964e5aabe235	MSG
127		7	2021-08-19 16:44:03.030039+09	f	3bc0dd51-f4a7-4d2c-a2a3-f16401472d99	JOIN
128	안녕하세요. 고객님	1	2021-08-19 16:44:21.277942+09	f	3bc0dd51-f4a7-4d2c-a2a3-f16401472d99	MSG
129	어떤 문제를 도와드릴까요?	1	2021-08-19 16:44:25.089254+09	f	3bc0dd51-f4a7-4d2c-a2a3-f16401472d99	MSG
130	아 제가 지금 모니터 주문하려고 하는데 오늘 결제하면 언제쯤 도착하나요?	7	2021-08-19 16:44:29.908237+09	f	3bc0dd51-f4a7-4d2c-a2a3-f16401472d99	MSG
131		8	2021-08-19 16:46:12.301246+09	f	a79554b8-5cad-4264-863e-ebb7592859ab	JOIN
132	안녕하세요. 고객님	1	2021-08-19 16:46:25.830783+09	f	a79554b8-5cad-4264-863e-ebb7592859ab	MSG
133	제품키 관련하여 문의주신거 맞으신가요?	1	2021-08-19 16:46:43.407122+09	f	a79554b8-5cad-4264-863e-ebb7592859ab	MSG
134	제품키 맞게 입력했는데 다운로드가 안되네요.	8	2021-08-19 16:46:48.931623+09	f	a79554b8-5cad-4264-863e-ebb7592859ab	MSG
135	제품키 맞게 주신거 맞으시죠?	8	2021-08-19 16:46:52.859776+09	f	a79554b8-5cad-4264-863e-ebb7592859ab	MSG
136	네 고객님 제품키 한 번 더 조회해드리겠습니다.	1	2021-08-19 16:46:57.995881+09	f	a79554b8-5cad-4264-863e-ebb7592859ab	MSG
137		9	2021-08-19 16:48:05.763613+09	f	ea31c512-3ae5-40dd-b3f0-112bf8dccd82	JOIN
138	안녕하세요. 고객님	1	2021-08-19 16:48:12.758532+09	f	ea31c512-3ae5-40dd-b3f0-112bf8dccd82	MSG
139	제품키 관련 문의 맞으신가요?	1	2021-08-19 16:48:23.478462+09	f	ea31c512-3ae5-40dd-b3f0-112bf8dccd82	MSG
140	네 제품키가 올바르지 않다고만 떠요.	9	2021-08-19 16:48:33.036269+09	f	ea31c512-3ae5-40dd-b3f0-112bf8dccd82	MSG
141	저는 똑바로 입력했거든요	9	2021-08-19 16:48:40.93853+09	f	ea31c512-3ae5-40dd-b3f0-112bf8dccd82	MSG
142	네 고객님. 구매하신 제품키 조회해서 저희가 한 번 더 확인해보겠습니다.	1	2021-08-19 16:48:50.509511+09	f	ea31c512-3ae5-40dd-b3f0-112bf8dccd82	MSG
143	문제 최대한 빨리 해결해드리겠습니다.	1	2021-08-19 16:48:54.977808+09	f	ea31c512-3ae5-40dd-b3f0-112bf8dccd82	MSG
144		10	2021-08-19 16:49:46.011045+09	f	7f796863-6d35-45a3-8a95-a33a5f690e65	JOIN
145	안녕하세요. 고객님	1	2021-08-19 16:49:55.538046+09	f	7f796863-6d35-45a3-8a95-a33a5f690e65	MSG
146	Office 관련하여 문의주신거 맞으실까요?	1	2021-08-19 16:50:19.21554+09	f	7f796863-6d35-45a3-8a95-a33a5f690e65	MSG
147	네 오피스 실행하려고 하는데 안되네요	10	2021-08-19 16:50:27.110076+09	f	7f796863-6d35-45a3-8a95-a33a5f690e65	MSG
148	설치하고 처음 실행했는데 안돼요!!	10	2021-08-19 16:50:31.38436+09	f	7f796863-6d35-45a3-8a95-a33a5f690e65	MSG
149	네 고객님 한 번만 더 설치해보시겠어요?	1	2021-08-19 16:50:36.51163+09	f	7f796863-6d35-45a3-8a95-a33a5f690e65	MSG
150		11	2021-08-19 16:51:33.856553+09	f	30203c26-a925-4c09-917e-c0a38608b762	JOIN
151	안녕하세요. 고객님	1	2021-08-19 16:51:45.299273+09	f	30203c26-a925-4c09-917e-c0a38608b762	MSG
152	제품키 관련하여 문의주신거 맞으실까요?	1	2021-08-19 16:51:49.30056+09	f	30203c26-a925-4c09-917e-c0a38608b762	MSG
153	아니요 제품키가 기업용이랑 개인용으로 나뉘던데 어떤 차이가 있는 건가요?	11	2021-08-19 16:51:54.419582+09	f	30203c26-a925-4c09-917e-c0a38608b762	MSG
\.


--
-- Data for Name: chat_session; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".chat_session (session_id, created_at, fk_created_by_idx, fk_client_idx, fk_host_idx, fk_permission_idx, status, unread, qna_history) FROM stdin;
3bc0dd51-f4a7-4d2c-a2a3-f16401472d99	2021-08-19 16:44:02.914973+09	7	7	1	\N	LIVE	\N	전자기기 관련 문의|모니터
a79554b8-5cad-4264-863e-ebb7592859ab	2021-08-19 16:46:12.212693+09	8	8	1	\N	LIVE	\N	Winodws 관련 문의|🤔 Windows 제품 키는 어떻게 찾을 수 있나요
ea31c512-3ae5-40dd-b3f0-112bf8dccd82	2021-08-19 16:48:05.662122+09	9	9	1	\N	LIVE	\N	Winodws 관련 문의|🤔 Windows 제품 키는 어떻게 찾을 수 있나요
a58a2d5a-4887-44bc-ba1a-304410ba5604	2021-08-19 14:40:04.309201+09	2	2	1	\N	END	\N	Office 관련 문의|Office 제품 키는 어디서 찾을 수 있나요
7f796863-6d35-45a3-8a95-a33a5f690e65	2021-08-19 16:49:45.893689+09	10	10	1	\N	LIVE	\N	Office 관련 문의
30203c26-a925-4c09-917e-c0a38608b762	2021-08-19 16:51:33.750336+09	11	11	1	\N	LIVE	\N	Office 관련 문의|Office 제품 키는 어디서 찾을 수 있나요
96b6e8fb-0269-4beb-82f9-439f19447f4a	2021-08-19 14:41:25.953489+09	2	2	1	\N	END	\N	Office 관련 문의|Office 제품 키는 어디서 찾을 수 있나요
6c0346db-8f06-4fcb-8182-eafd77233e97	2021-08-19 14:44:42.974111+09	2	2	1	\N	END	\N	Office 관련 문의|Office 제품 키는 어디서 찾을 수 있나요
b617021e-feb1-4f3c-81f7-6ac94b28897e	2021-08-19 14:47:37.238196+09	2	2	1	\N	END	\N	Office 관련 문의|Office 제품 키는 어디서 찾을 수 있나요
d00cd24e-b8c4-45ab-b64e-5539942d184d	2021-08-19 16:23:13.515969+09	3	3	2	\N	LIVE	\N	Winodws 관련 문의|32비트/64 비트 차이를 모르겠어요
d98a0b9e-dbcc-482e-aaf7-5a829888e2d9	2021-08-19 16:25:08.183465+09	4	4	2	\N	END	\N	Office 관련 문의
4bacc533-bfc9-4d13-8b4d-950d647129ee	2021-08-19 16:27:41.128682+09	5	5	2	\N	END	\N	전자기기 관련 문의|노트북
a139d2fd-f993-4c29-84ac-380124691c8a	2021-08-19 16:28:41.339933+09	5	5	2	\N	LIVE	\N	전자기기 관련 문의|노트북
4ba497f4-4d04-44a3-ae6a-2e22f44cace5	2021-08-19 16:31:22.146227+09	6	6	1	\N	LIVE	\N	전자기기 관련 문의
112e168e-2103-4b54-8589-964dbd9829ad	2021-08-19 16:35:35.928652+09	5	5	1	\N	LIVE	\N	전자기기 관련 문의|노트북
6f790c61-f73b-4570-b910-b06c45626d52	2021-08-19 16:39:31.529165+09	4	4	1	\N	LIVE	\N	Office 관련 문의
3eec70b4-513d-4091-8ffb-964e5aabe235	2021-08-19 16:41:36.663046+09	3	3	1	\N	LIVE	\N	Winodws 관련 문의|32비트/64 비트 차이를 모르겠어요
\.


--
-- Data for Name: permission; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".permission (pk_idx, name, created, updated, fk_role_idx) FROM stdin;
\.


--
-- Data for Name: question; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".question (pk_idx, content, title) FROM stdin;
2	상담 종료	상담 종료
4	제품 키는 제품 포장 내부, Windows를 구매했음을 알려주는 확인 메일에서 찾을 수 있습니다.\n충분한 답변이 되셨나요?	Winodws 제품 키
7	Office 제품 키 카드를 구입한 경우 제품 키는 패키지 내부의 케이스 ⬅왼쪽에 있는 레이블에 있습니다.\n충분한 답변이 되셨나요?	Office 제품 키
1	😄 안녕하세요. 톡톡톡입니다. 어떤 문제가 있으신가요?	상담 시작
8	다운로드하는 동안 시작되는 화면 보호기가 있는 경우 다운로드가 완료될 때까지 일시적으로 사용하지 않도록 설정하세요.\n충분한 답변이 되셨나요?	Office 다운로드
11	전원 버튼을 40초간 🕗 길게 눌러보세요.\n충분한 답변이 되셨나요?	노트북 전원
6	Office 사용에 어떤 문제가 있으신가요?	Office
46	노트북 통풍구를 청소해보세요.\n\n충분한 답변이 되셨나요?	노트북 과열
9	어떤 전자기기에 문제가 있으신가요?	전자기기
5	🖥32비트 및 64비트라는 용어는 컴퓨터 프로세서(CPU라고도 함)가 정보를 처리하는 방식을 나타냅니다.\n충분한 답변이 되셨나요?	32비트/64 비트
10	노트북에 어떤 문제가 발생하셨나요?	노트북
3	Windows 사용에 어떤 문제가 있으신가요?	Windows
88	모니터에 어떤 문제가 발생하셨나요? 	모니터
90	모니터에 전원 공급이 원활하게 되고 있는지 확인해보세요. 충분한 답변이 되셨나요? 	모니터 화면불량
89	모니터 하단의 설정 버튼을 통해 원하는 밝기로 조정하세요. 충분한 답변이 되셨나요? 	모니터 어두움
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".role (pk_idx, name, created, updated, admin) FROM stdin;
\.


--
-- Data for Name: service_case; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".service_case (pk_idx, title, link, fk_charge_idx, fk_customer_idx, fk_session_id, feedback) FROM stdin;
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: SSAFY_PJT1; Owner: postgres
--

COPY "SSAFY_PJT1".user_roles (pk_idx, fk_user_idx, fk_role_idx) FROM stdin;
\.


--
-- Name: account_pk_idx_seq; Type: SEQUENCE SET; Schema: SSAFY_PJT1; Owner: postgres
--

SELECT pg_catalog.setval('"SSAFY_PJT1".account_pk_idx_seq', 11, true);


--
-- Name: answer_pk_idx_seq; Type: SEQUENCE SET; Schema: SSAFY_PJT1; Owner: postgres
--

SELECT pg_catalog.setval('"SSAFY_PJT1".answer_pk_idx_seq', 102, true);


--
-- Name: chat_message_pk_idx_seq; Type: SEQUENCE SET; Schema: SSAFY_PJT1; Owner: postgres
--

SELECT pg_catalog.setval('"SSAFY_PJT1".chat_message_pk_idx_seq', 153, true);


--
-- Name: permission_pk_idx_seq; Type: SEQUENCE SET; Schema: SSAFY_PJT1; Owner: postgres
--

SELECT pg_catalog.setval('"SSAFY_PJT1".permission_pk_idx_seq', 1, false);


--
-- Name: question_pk_idx_seq; Type: SEQUENCE SET; Schema: SSAFY_PJT1; Owner: postgres
--

SELECT pg_catalog.setval('"SSAFY_PJT1".question_pk_idx_seq', 107, true);


--
-- Name: role_pk_idx_seq; Type: SEQUENCE SET; Schema: SSAFY_PJT1; Owner: postgres
--

SELECT pg_catalog.setval('"SSAFY_PJT1".role_pk_idx_seq', 1, false);


--
-- Name: service_case_pk_idx_seq; Type: SEQUENCE SET; Schema: SSAFY_PJT1; Owner: postgres
--

SELECT pg_catalog.setval('"SSAFY_PJT1".service_case_pk_idx_seq', 1, false);


--
-- Name: user_roles_pk_idx_seq; Type: SEQUENCE SET; Schema: SSAFY_PJT1; Owner: postgres
--

SELECT pg_catalog.setval('"SSAFY_PJT1".user_roles_pk_idx_seq', 1, false);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".account
    ADD CONSTRAINT account_pkey PRIMARY KEY (pk_idx);


--
-- Name: answer answer_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".answer
    ADD CONSTRAINT answer_pkey PRIMARY KEY (pk_idx);


--
-- Name: chat_message chat_message_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_message
    ADD CONSTRAINT chat_message_pkey PRIMARY KEY (pk_idx);


--
-- Name: chat_session chat_session_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_session
    ADD CONSTRAINT chat_session_pkey PRIMARY KEY (session_id);


--
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (pk_idx);


--
-- Name: question question_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".question
    ADD CONSTRAINT question_pkey PRIMARY KEY (pk_idx);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".role
    ADD CONSTRAINT role_pkey PRIMARY KEY (pk_idx);


--
-- Name: service_case service_case_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".service_case
    ADD CONSTRAINT service_case_pkey PRIMARY KEY (pk_idx);


--
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (pk_idx);


--
-- Name: answer answer_fk_next_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".answer
    ADD CONSTRAINT answer_fk_next_idx_fkey FOREIGN KEY (fk_next_idx) REFERENCES "SSAFY_PJT1".question(pk_idx) ON DELETE SET NULL;


--
-- Name: answer answer_fk_previous_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".answer
    ADD CONSTRAINT answer_fk_previous_idx_fkey FOREIGN KEY (fk_previous_idx) REFERENCES "SSAFY_PJT1".question(pk_idx) ON DELETE SET NULL;


--
-- Name: chat_message chat_message_fk_author_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_message
    ADD CONSTRAINT chat_message_fk_author_idx_fkey FOREIGN KEY (fk_author_idx) REFERENCES "SSAFY_PJT1".account(pk_idx) NOT VALID;


--
-- Name: chat_message chat_message_fk_session_id_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_message
    ADD CONSTRAINT chat_message_fk_session_id_fkey FOREIGN KEY (fk_session_id) REFERENCES "SSAFY_PJT1".chat_session(session_id) NOT VALID;


--
-- Name: chat_session chat_session_fk_client_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_session
    ADD CONSTRAINT chat_session_fk_client_idx_fkey FOREIGN KEY (fk_client_idx) REFERENCES "SSAFY_PJT1".account(pk_idx) NOT VALID;


--
-- Name: chat_session chat_session_fk_created_by_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_session
    ADD CONSTRAINT chat_session_fk_created_by_idx_fkey FOREIGN KEY (fk_created_by_idx) REFERENCES "SSAFY_PJT1".account(pk_idx) NOT VALID;


--
-- Name: chat_session chat_session_fk_host_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_session
    ADD CONSTRAINT chat_session_fk_host_idx_fkey FOREIGN KEY (fk_host_idx) REFERENCES "SSAFY_PJT1".account(pk_idx) NOT VALID;


--
-- Name: chat_session chat_session_fk_permission_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".chat_session
    ADD CONSTRAINT chat_session_fk_permission_idx_fkey FOREIGN KEY (fk_permission_idx) REFERENCES "SSAFY_PJT1".permission(pk_idx) NOT VALID;


--
-- Name: permission permission_fk_role_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".permission
    ADD CONSTRAINT permission_fk_role_idx_fkey FOREIGN KEY (fk_role_idx) REFERENCES "SSAFY_PJT1".role(pk_idx) NOT VALID;


--
-- Name: service_case service_case_fk_charge_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".service_case
    ADD CONSTRAINT service_case_fk_charge_idx_fkey FOREIGN KEY (fk_charge_idx) REFERENCES "SSAFY_PJT1".account(pk_idx) NOT VALID;


--
-- Name: service_case service_case_fk_customer_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".service_case
    ADD CONSTRAINT service_case_fk_customer_idx_fkey FOREIGN KEY (fk_customer_idx) REFERENCES "SSAFY_PJT1".account(pk_idx) NOT VALID;


--
-- Name: service_case service_case_fk_session_id_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".service_case
    ADD CONSTRAINT service_case_fk_session_id_fkey FOREIGN KEY (fk_session_id) REFERENCES "SSAFY_PJT1".chat_session(session_id) NOT VALID;


--
-- Name: user_roles user_roles_fk_role_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".user_roles
    ADD CONSTRAINT user_roles_fk_role_idx_fkey FOREIGN KEY (fk_role_idx) REFERENCES "SSAFY_PJT1".role(pk_idx) NOT VALID;


--
-- Name: user_roles user_roles_fk_user_idx_fkey; Type: FK CONSTRAINT; Schema: SSAFY_PJT1; Owner: postgres
--

ALTER TABLE ONLY "SSAFY_PJT1".user_roles
    ADD CONSTRAINT user_roles_fk_user_idx_fkey FOREIGN KEY (fk_user_idx) REFERENCES "SSAFY_PJT1".account(pk_idx) NOT VALID;


--
-- Name: SCHEMA "SSAFY_PJT1"; Type: ACL; Schema: -; Owner: postgres
--

GRANT USAGE ON SCHEMA "SSAFY_PJT1" TO padmin;


--
-- Name: TABLE account; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".account TO padmin;


--
-- Name: SEQUENCE account_pk_idx_seq; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON SEQUENCE "SSAFY_PJT1".account_pk_idx_seq TO padmin;


--
-- Name: TABLE answer; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".answer TO padmin;


--
-- Name: SEQUENCE answer_pk_idx_seq; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON SEQUENCE "SSAFY_PJT1".answer_pk_idx_seq TO padmin;


--
-- Name: TABLE chat_message; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".chat_message TO padmin;


--
-- Name: SEQUENCE chat_message_pk_idx_seq; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON SEQUENCE "SSAFY_PJT1".chat_message_pk_idx_seq TO padmin;


--
-- Name: TABLE chat_session; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".chat_session TO padmin;


--
-- Name: TABLE permission; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".permission TO padmin;


--
-- Name: SEQUENCE permission_pk_idx_seq; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON SEQUENCE "SSAFY_PJT1".permission_pk_idx_seq TO padmin;


--
-- Name: TABLE question; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".question TO padmin;


--
-- Name: SEQUENCE question_pk_idx_seq; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON SEQUENCE "SSAFY_PJT1".question_pk_idx_seq TO padmin;


--
-- Name: TABLE role; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".role TO padmin;


--
-- Name: SEQUENCE role_pk_idx_seq; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON SEQUENCE "SSAFY_PJT1".role_pk_idx_seq TO padmin;


--
-- Name: TABLE service_case; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".service_case TO padmin;


--
-- Name: SEQUENCE service_case_pk_idx_seq; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON SEQUENCE "SSAFY_PJT1".service_case_pk_idx_seq TO padmin;


--
-- Name: TABLE user_roles; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON TABLE "SSAFY_PJT1".user_roles TO padmin;


--
-- Name: SEQUENCE user_roles_pk_idx_seq; Type: ACL; Schema: SSAFY_PJT1; Owner: postgres
--

GRANT ALL ON SEQUENCE "SSAFY_PJT1".user_roles_pk_idx_seq TO padmin;


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3 (Debian 13.3-1.pgdg100+1)
-- Dumped by pg_dump version 13.3 (Debian 13.3-1.pgdg100+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

