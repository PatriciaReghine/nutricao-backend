-- Ajuste da tabela CONSULTA
-- Inclusão do campo VISIVEL para permitir ocultação
-- lógica de consultas sem remoção dos dados do banco.
-- O campo é utilizado para controle do histórico de
-- consultas exibido ao usuário.

ALTER TABLE consulta
ADD COLUMN visivel BOOLEAN NOT NULL DEFAULT TRUE;