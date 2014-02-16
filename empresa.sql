--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.11
-- Dumped by pg_dump version 9.1.11
-- Started on 2014-02-15 23:36:14 AMST

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 169 (class 3079 OID 11680)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1937 (class 0 OID 0)
-- Dependencies: 169
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 164 (class 1259 OID 16862)
-- Dependencies: 5
-- Name: Funcionario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Funcionario" (
    id bigint NOT NULL,
    nome character varying(255)
);


ALTER TABLE public."Funcionario" OWNER TO postgres;

--
-- TOC entry 163 (class 1259 OID 16860)
-- Dependencies: 164 5
-- Name: Funcionario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Funcionario_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Funcionario_id_seq" OWNER TO postgres;

--
-- TOC entry 1938 (class 0 OID 0)
-- Dependencies: 163
-- Name: Funcionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Funcionario_id_seq" OWNED BY "Funcionario".id;


--
-- TOC entry 166 (class 1259 OID 16878)
-- Dependencies: 164 5
-- Name: Gerente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Gerente" (
    id bigint,
    codigo character varying(32)
)
INHERITS ("Funcionario");


ALTER TABLE public."Gerente" OWNER TO postgres;

--
-- TOC entry 165 (class 1259 OID 16876)
-- Dependencies: 166 5
-- Name: Gerente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Gerente_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Gerente_id_seq" OWNER TO postgres;

--
-- TOC entry 1939 (class 0 OID 0)
-- Dependencies: 165
-- Name: Gerente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Gerente_id_seq" OWNED BY "Gerente".id;


--
-- TOC entry 162 (class 1259 OID 16854)
-- Dependencies: 5
-- Name: Setor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Setor" (
    id bigint NOT NULL,
    nome character varying(128),
    unidade bigint,
    secretario bigint
);


ALTER TABLE public."Setor" OWNER TO postgres;

--
-- TOC entry 161 (class 1259 OID 16852)
-- Dependencies: 162 5
-- Name: Setor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Setor_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Setor_id_seq" OWNER TO postgres;

--
-- TOC entry 1940 (class 0 OID 0)
-- Dependencies: 161
-- Name: Setor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Setor_id_seq" OWNED BY "Setor".id;


--
-- TOC entry 168 (class 1259 OID 16892)
-- Dependencies: 5
-- Name: Unidade; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Unidade" (
    id bigint NOT NULL,
    numero bigint
);


ALTER TABLE public."Unidade" OWNER TO postgres;

--
-- TOC entry 167 (class 1259 OID 16890)
-- Dependencies: 5 168
-- Name: Unidade_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Unidade_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Unidade_id_seq" OWNER TO postgres;

--
-- TOC entry 1941 (class 0 OID 0)
-- Dependencies: 167
-- Name: Unidade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Unidade_id_seq" OWNED BY "Unidade".id;


--
-- TOC entry 1808 (class 2604 OID 16865)
-- Dependencies: 164 163 164
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Funcionario" ALTER COLUMN id SET DEFAULT nextval('"Funcionario_id_seq"'::regclass);


--
-- TOC entry 1809 (class 2604 OID 16881)
-- Dependencies: 166 165 166
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Gerente" ALTER COLUMN id SET DEFAULT nextval('"Gerente_id_seq"'::regclass);


--
-- TOC entry 1807 (class 2604 OID 16857)
-- Dependencies: 161 162 162
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Setor" ALTER COLUMN id SET DEFAULT nextval('"Setor_id_seq"'::regclass);


--
-- TOC entry 1810 (class 2604 OID 16895)
-- Dependencies: 168 167 168
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Unidade" ALTER COLUMN id SET DEFAULT nextval('"Unidade_id_seq"'::regclass);


--
-- TOC entry 1925 (class 0 OID 16862)
-- Dependencies: 164 1930
-- Data for Name: Funcionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "Funcionario" (id, nome) FROM stdin;
\.


--
-- TOC entry 1942 (class 0 OID 0)
-- Dependencies: 163
-- Name: Funcionario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"Funcionario_id_seq"', 10, true);


--
-- TOC entry 1927 (class 0 OID 16878)
-- Dependencies: 166 1930
-- Data for Name: Gerente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "Gerente" (id, nome, codigo) FROM stdin;
\.


--
-- TOC entry 1943 (class 0 OID 0)
-- Dependencies: 165
-- Name: Gerente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"Gerente_id_seq"', 58, true);


--
-- TOC entry 1923 (class 0 OID 16854)
-- Dependencies: 162 1930
-- Data for Name: Setor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "Setor" (id, nome, unidade, secretario) FROM stdin;
\.


--
-- TOC entry 1944 (class 0 OID 0)
-- Dependencies: 161
-- Name: Setor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"Setor_id_seq"', 85, true);


--
-- TOC entry 1929 (class 0 OID 16892)
-- Dependencies: 168 1930
-- Data for Name: Unidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "Unidade" (id, numero) FROM stdin;
\.


--
-- TOC entry 1945 (class 0 OID 0)
-- Dependencies: 167
-- Name: Unidade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"Unidade_id_seq"', 6, true);


--
-- TOC entry 1814 (class 2606 OID 16870)
-- Dependencies: 164 164 1931
-- Name: Funcionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Funcionario"
    ADD CONSTRAINT "Funcionario_pkey" PRIMARY KEY (id);


--
-- TOC entry 1816 (class 2606 OID 16886)
-- Dependencies: 166 166 1931
-- Name: Gerente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Gerente"
    ADD CONSTRAINT "Gerente_pkey" PRIMARY KEY (id);


--
-- TOC entry 1812 (class 2606 OID 16859)
-- Dependencies: 162 162 1931
-- Name: Setor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Setor"
    ADD CONSTRAINT "Setor_pkey" PRIMARY KEY (id);


--
-- TOC entry 1818 (class 2606 OID 16897)
-- Dependencies: 168 168 1931
-- Name: Unidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Unidade"
    ADD CONSTRAINT "Unidade_pkey" PRIMARY KEY (id);


--
-- TOC entry 1820 (class 2606 OID 16908)
-- Dependencies: 164 162 1813 1931
-- Name: Setor_secretario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Setor"
    ADD CONSTRAINT "Setor_secretario_fkey" FOREIGN KEY (secretario) REFERENCES "Funcionario"(id);


--
-- TOC entry 1819 (class 2606 OID 16903)
-- Dependencies: 1817 168 162 1931
-- Name: Setor_unidade_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Setor"
    ADD CONSTRAINT "Setor_unidade_fkey" FOREIGN KEY (unidade) REFERENCES "Unidade"(id);


--
-- TOC entry 1936 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-02-15 23:36:14 AMST

--
-- PostgreSQL database dump complete
--

