# QUARKUS-FILTER-AUDITORIA

Exemplo de projeto feito em Quarkus para demonstrar a gravação em banco dos dados do request e do response para efeitos de auditoria usando filter.

## Dependências
- Quarkus 2.16.5.Final
- JDK 11
- PostgreSQL 12.2
- Docker 4.5.1

## Passos para execução do projeto

> **Passo 1: Execute o docker**
```bash
  docker compose up
```

> **Passo 2: Crie o schema public no postgres**

> **Passo 3: Habilite o flyway.** 
Abra o arquivo **application.properties** e altere a seguinte propriedade:
```bash
  myapp.flyway.migrate = true
```

> **Passo 4: Execute o projeto.** 
```bash
  mvnw quarkus:dev
```

> **Passo 5: Faça uma chamada à API**
```bash
  http://localhost:8080/contratos
```
