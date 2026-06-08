-- Remoção da constraint UNIQUE que impedia
-- múltiplas avaliações físicas associadas ao prontuário.

ALTER TABLE avaliacao_fisica
DROP CONSTRAINT uklgbb5danctk4lsm06p68kbyaq;