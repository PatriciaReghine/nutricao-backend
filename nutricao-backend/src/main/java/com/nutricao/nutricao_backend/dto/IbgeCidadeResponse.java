package com.nutricao.nutricao_backend.dto;

public class IbgeCidadeResponse {
        private String nome;
        private Microrregiao microrregiao;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Microrregiao getMicrorregiao() {
            return microrregiao;
        }

        public void setMicrorregiao(Microrregiao microrregiao) {
            this.microrregiao = microrregiao;
        }

        public static class Microrregiao {
            private Mesorregiao mesorregiao;

            public Mesorregiao getMesorregiao() {
                return mesorregiao;
            }

            public void setMesorregiao(Mesorregiao mesorregiao) {
                this.mesorregiao = mesorregiao;
            }
        }

        public static class Mesorregiao {
            private UF UF;

            public UF getUF() {
                return UF;
            }

            public void setUF(UF UF) {
                this.UF = UF;
            }
        }

        public static class UF {
            private String sigla;

            public String getSigla() {
                return sigla;
            }

            public void setSigla(String sigla) {
                this.sigla = sigla;
            }
        }
    }

