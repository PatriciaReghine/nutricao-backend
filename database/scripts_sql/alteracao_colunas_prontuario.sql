-- Ajuste da tabela PRONTUARIO para campos do tipo TEXT,
-- permitindo armazenar descrições maiores sem limitação, de tamanho previamente definida

ALTER TABLE prontuario
ALTER COLUMN informacoes_clinicas TYPE TEXT;

ALTER TABLE prontuario
ALTER COLUMN objetivo TYPE TEXT;

ALTER TABLE prontuario
ALTER COLUMN restricao_alimentar TYPE TEXT;