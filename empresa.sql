-- Table: "Unidade"

-- DROP TABLE "Unidade";

CREATE TABLE "Unidade"
(
  id bigserial NOT NULL,
  numero bigint,
  CONSTRAINT "Unidade_pkey" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Unidade"
  OWNER TO postgres;


-- Table: "Setor"

-- DROP TABLE "Setor";

CREATE TABLE "Setor"
(
  id bigserial NOT NULL,
  nome character varying(128),
  unidade bigint,
  CONSTRAINT "Setor_pkey" PRIMARY KEY (id),
  CONSTRAINT "Setor_unidade_fkey" FOREIGN KEY (unidade)
      REFERENCES "Unidade" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Setor"
  OWNER TO postgres;


/*
--Database empresa

-- Table: "Funcionario"

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
  setor bigint,
  CONSTRAINT "Funcionario_pkey" PRIMARY KEY (id),
  CONSTRAINT "Funcionario_setor_fkey" FOREIGN KEY (setor)
      REFERENCES "Setor" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
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
