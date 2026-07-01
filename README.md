# 🥗 API REST - Sistema de Gestão para Clínica de Nutrição

API REST desenvolvida como Trabalho de Conclusão de Curso (TCC) do curso de Análise e Desenvolvimento de Sistemas.

O sistema foi desenvolvido para gerenciar os processos de uma clínica de nutrição, oferecendo controle de usuários, pacientes, agendas, consultas, prontuários, avaliações físicas e relatórios, seguindo uma arquitetura em camadas e boas práticas de desenvolvimento.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- JWT (JSON Web Token)
- BCrypt

---

## 📁 Estrutura do Projeto

```
src
├── dto
├── entidades
├── enums
├── repositories
├── resources
├── security
├── services
└── config
```

---

## 🔐 Segurança

A API implementa autenticação e autorização utilizando:

- JWT
- Spring Security
- Criptografia de senhas com BCrypt
- Controle de acesso baseado em perfis

### Perfis disponíveis

- Administrador
- Secretária
- Nutricionista

---

## 📋 Funcionalidades

### Usuários

- Cadastro de usuários
- Login
- Atualização de dados
- Alteração de senha
- Bloqueio e desbloqueio
- Controle de perfis
- Validação de e-mail único

### Pacientes

- Cadastro
- Atualização
- Pesquisa por nome e CPF
- Validação de CPF único
- Geração automática de prontuário

### Prontuários

- Criação automática
- Atualização de informações clínicas
- Registro de objetivo nutricional
- Restrições alimentares
- Associação ao usuário responsável pelo cadastro

### Agendas

- Abertura de agenda
- Alteração de status
- Consulta da agenda diária

### Agendamentos

- Agendamento de consultas
- Cancelamento
- Alteração de status
- Controle de conflito de horários
- Liberação automática de horários cancelados

### Consultas

- Registro de consultas
- Tipos de consulta
- Resumos
- Observações

### Avaliações físicas

- Cadastro
- Histórico
- Associação ao paciente

### Dashboard

- Quantidade de pacientes
- Quantidade de nutricionistas
- Consultas do dia
- Últimos pacientes cadastrados
- Nutricionistas com maior número de consultas

### Relatórios

- Relatório de Consultas
- Relatório de Agendas
- Relatório de Usuários
- Relatório de Pacientes

Todos os relatórios possuem filtros para facilitar consultas e integração com o frontend.

---

## 📌 Principais regras de negócio

- Não permite usuários com e-mail duplicado.
- Não permite pacientes com CPF duplicado.
- Cada paciente possui um único prontuário.
- O prontuário é criado automaticamente ao cadastrar um paciente.
- O prontuário registra o usuário responsável pelo cadastro.
- Não permite dois agendamentos ativos no mesmo horário para a mesma nutricionista.
- Horários cancelados podem ser reutilizados.
- Controle de acesso baseado em perfis.

---

## ▶️ Como executar

### Clone o projeto

```bash
git clone https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
```

### Configure o banco de dados

No arquivo:

```
application.properties
```

configure:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nutricao
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
```

### Execute a aplicação

```bash
mvn spring-boot:run
```

ou execute pela IDE (IntelliJ IDEA).

---

## 📖 Objetivo

Este projeto foi desenvolvido como Trabalho de Conclusão de Curso (TCC), aplicando conceitos de:

- Arquitetura em Camadas
- APIs REST
- Spring Boot
- Segurança com JWT
- Spring Security
- Persistência de Dados com JPA/Hibernate
- Modelagem de Banco de Dados
- Regras de Negócio
- Boas práticas de desenvolvimento

---

## 👩‍💻 Desenvolvedora

**Patrícia Reghine Schimanski**

Desenvolvimento do Backend utilizando Java, Spring Boot e PostgreSQL.

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- JWT (JSON Web Token)
- BCrypt

---

## 📁 Estrutura do Projeto

```
src
├── dto
├── entidades
├── enums
├── repositories
├── resources
├── security
├── services
└── config
```

---

## 🔐 Segurança

A API implementa autenticação e autorização utilizando:

- JWT
- Spring Security
- Criptografia de senhas com BCrypt
- Controle de acesso baseado em perfis

### Perfis disponíveis

- Administrador
- Secretária
- Nutricionista

---

## 📋 Funcionalidades

### Usuários

- Cadastro de usuários
- Login
- Atualização de dados
- Alteração de senha
- Bloqueio e desbloqueio
- Controle de perfis
- Validação de e-mail único

### Pacientes

- Cadastro
- Atualização
- Pesquisa por nome e CPF
- Validação de CPF único
- Geração automática de prontuário

### Prontuários

- Criação automática
- Atualização de informações clínicas
- Registro de objetivo nutricional
- Restrições alimentares
- Associação ao usuário responsável pelo cadastro

### Agendas

- Abertura de agenda
- Alteração de status
- Consulta da agenda diária

### Agendamentos

- Agendamento de consultas
- Cancelamento
- Alteração de status
- Controle de conflito de horários
- Liberação automática de horários cancelados

### Consultas

- Registro de consultas
- Tipos de consulta
- Resumos
- Observações

### Avaliações físicas

- Cadastro
- Histórico
- Associação ao paciente

### Dashboard

- Quantidade de pacientes
- Quantidade de nutricionistas
- Consultas do dia
- Últimos pacientes cadastrados
- Nutricionistas com maior número de consultas

### Relatórios

- Relatório de Consultas
- Relatório de Agendas
- Relatório de Usuários
- Relatório de Pacientes

Todos os relatórios possuem filtros para facilitar consultas e integração com o frontend.

---

## 📌 Principais regras de negócio

- Não permite usuários com e-mail duplicado.
- Não permite pacientes com CPF duplicado.
- Cada paciente possui um único prontuário.
- O prontuário é criado automaticamente ao cadastrar um paciente.
- O prontuário registra o usuário responsável pelo cadastro.
- Não permite dois agendamentos ativos no mesmo horário para a mesma nutricionista.
- Horários cancelados podem ser reutilizados.
- Controle de acesso baseado em perfis.

---

## ▶️ Como executar

### Clone o projeto

```bash
git clone https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
```

### Configure o banco de dados

No arquivo:

```
application.properties
```

configure:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nutricao
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
```

### Execute a aplicação

```bash
mvn spring-boot:run
```

ou execute pela IDE (IntelliJ IDEA).

---

## 📖 Objetivo

Este projeto foi desenvolvido como Trabalho de Conclusão de Curso (TCC), aplicando conceitos de:

- Arquitetura em Camadas
- APIs REST
- Spring Boot
- Segurança com JWT
- Spring Security
- Persistência de Dados com JPA/Hibernate
- Modelagem de Banco de Dados
- Regras de Negócio
- Boas práticas de desenvolvimento

---

## 👩‍💻 Desenvolvedora

**Patrícia Reghine Schimanski**

Desenvolvimento do Backend utilizando Java, Spring Boot e PostgreSQL.

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.
