/*
--Database empresa

--CREATE DATABASE "empresa";

﻿-- Table: "Funcionario"

-- DROP TABLE "Funcionario";
*/
CREATE TABLE "Funcionario"
(
  id bigserial NOT NULL,
  nome character varying(255),
  cpf character varying(16),
  rg character varying(16),
  telefone character varying(24),
  endereco character varying(255),
  CONSTRAINT "Funcionario_pkey" PRIMARY KEY (id),
  CONSTRAINT "Funcionario_cpf_rg_key" UNIQUE (cpf, rg)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Funcionario"
  OWNER TO postgres;

-- Table: "Gerente"

-- DROP TABLE "Gerente";

CREATE TABLE "Gerente"
(
  id bigserial NOT NULL,
-- Inherited from table "Funcionario":  cpf character varying(16),
-- Inherited from table "Funcionario":  rg character varying(16),
-- Inherited from table "Funcionario":  telefone character varying(24),
-- Inherited from table "Funcionario":  endereco character varying(255),
  codigo character varying(32),
  CONSTRAINT "Gerente_pkey" PRIMARY KEY (id),
  CONSTRAINT "Gerente_codigo_key" UNIQUE (codigo)
)
INHERITS ("Funcionario")
WITH (
  OIDS=FALSE
);
ALTER TABLE "Gerente"
  OWNER TO postgres;
