# 🩺 Appointment History Service

[![Java](https://img.shields.io/badge/Java-21-red?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![GraphQL](https://img.shields.io/badge/API-GraphQL-blueviolet?logo=graphql)](https://graphql.org/)
[![MongoDB](https://img.shields.io/badge/Database-MongoDB-green?logo=mongodb)](https://www.mongodb.com/)
[![RabbitMQ](https://img.shields.io/badge/Queue-RabbitMQ-orange?logo=rabbitmq)](https://www.rabbitmq.com/)
[![Docker](https://img.shields.io/badge/Docker-ready-blue?logo=docker)](https://www.docker.com/)

Serviço de histórico de agendamentos desenvolvido em **Java 21** com **Spring Boot 3.5.6**, **MongoDB**, **RabbitMQ** e **GraphQL**.  
Faz parte do **Tech Challenge - Fase 3 (FIAP)** e é responsável por armazenar e expor o histórico de agendamentos realizados no sistema.

---

## 🧩 Visão Geral

O serviço segue uma arquitetura baseada em eventos.  
Ele consome mensagens de agendamentos publicadas em uma **fila RabbitMQ**, converte-as em objetos de domínio e persiste os dados em **MongoDB**.  
A leitura dos dados é feita por meio de uma **API GraphQL**, que fornece consultas dinâmicas e performáticas.

Fluxo simplificado:

[Producer Service] → [RabbitMQ Exchange] → [Appointment History Service] → [MongoDB]
→ [GraphQL API]

---

## ⚙️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
    - Spring Data MongoDB
    - Spring AMQP (RabbitMQ)
    - Spring GraphQL
    - Spring WebFlux
- **MongoDB**
- **RabbitMQ**
- **GraphQL**
- **MapStruct**
- **Lombok**
- **Docker / Docker Compose** 

---

## 🧩 Arquitetura

O **Appointment History Service** foi desenvolvido seguindo os princípios da **Arquitetura Hexagonal (Ports and Adapters)**.

A decisão de utilizar essa abordagem veio do objetivo de **experimentar um novo estilo arquitetural** e, ao mesmo tempo, manter o projeto **leve e de fácil manutenção**.  
Como o escopo do serviço é relativamente pequeno, a arquitetura hexagonal trouxe um bom equilíbrio entre **organização e baixo acoplamento**, sem a complexidade adicional de uma Clean Architecture completa.

## 🧱 Estrutura do Projeto

```
appointment_history_service/
├── src/
│   ├── main/
│   │   ├── java/com/fiap/techchallenge/appointment_history_service/
│   │   │   ├── adapters/                # Camada de entrada e saída (controladores, mensageria, etc.)
│   │   │   │   ├── in/                  # Pontos de entrada — GraphQL resolvers, controllers, consumers
│   │   │   │   └── out/                 # Portas de saída — integrações externas (RabbitMQ, Mongo, etc.)
│   │   │   ├── application/             # Regras de negócio e casos de uso
│   │   │   │   ├── dto/                 # Objetos de transferência de dados
│   │   │   │   ├── mapper/              # Conversores (MapStruct)
│   │   │   │   └── usecase/             # Casos de uso e lógica de aplicação
│   │   │   ├── config/                  # Configurações globais (Beans, conexões, etc.)
│   │   │   ├── domain/                  # Entidades e regras de domínio
│   │   │   │   ├── auth/                # Objetos relacionados à autorização e autenticação
│   │   │   │   ├── model/               # Modelos de domínio principais (AppointmentHistoryDomain, etc.)
│   │   │   │   ├── out/                 # Interfaces das portas de saída
│   │   │   │   └── util/                # Utilitários e helpers
│   │   │   ├── exception/               # Exceções personalizadas e tratamento de erros
│   │   │   └── AppointmentHistoryServiceApplication.java  # Classe principal
│   │   └── resources/
│   │       ├── application.yml          # Configurações do Spring Boot
│   │       └── graphql/                 # Schemas e queries do GraphQL
│   │           ├── schema.graphqls
│   │           └── query.graphqls
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .env
└── README.md
```
---

## 🧰 Configuração do Ambiente

### Pré-requisitos

- **Java 21**
- **Maven 3.9+**
- **Docker e Docker Compose**

### Clone o repositório:
```text
   git clone https://github.com/fiap-g4-adjt-schedule-management/appointment_history_service
```

### Instale as dependências:
```bash
 ./mvnw clean install
```
## ⚙️ Variáveis de Ambiente

Por segurança, o arquivo `.env` **não é versionado no repositório** (está listado no `.gitignore`).  
Antes de executar o projeto, você precisa **criar manualmente o arquivo `.env`** na raiz do projeto.

### 📝 Como criar o arquivo `.env`

Na raiz do projeto (`appointment_history_service/`), crie um arquivo chamado `.env` com o conteúdo abaixo:

```env
# MongoDB
MONGO_URI=mongodb://mongo:27017/appointment_db
MONGO_PORT=27017

# RabbitMQ
RABBIT_HOST=tech-challenge-rabbit
RABBIT_PORT=5672
RABBIT_USER=user
RABBIT_PASSWORD=password

# Serviço de Autenticação (ms-schedule)
AUTH_CLIENT_BASE_URL=http://ms-schedule:8080
AUTH_CLIENT_PATH=/auth/token-validator
AUTH_CLIENT_TIMEOUT=5000
```

> ⚠️ Importante: Quando executar localmente, sem o Docker Compose, substitua o nome dos serviços 
por localhost (ex.: mongo, tech-challenge-rabbit, ms-schedule).


---

## 🐳 Execução com Docker

Para iniciar todos os serviços (MongoDB + RabbitMQ + aplicação):

####  Execute:
```bash
 docker-compose up -d
```

É possivel acessar o banco de dados e o painel do RabbitMQ pelos links:

#### MongoDB:
```text
    mongodb://localhost:27017/appointment_db
```

#### RabbitMQ:
```text
    http://localhost:15672
    usuário: user
    senha: password
```

####  Parar os containers:
```bash
 docker-compose down
```

> Dica: se você também vai executar o ms-schedule com o Docker, rode primeiro o compose do History (com RabbitMQ e Mongo), e depois o compose do `ms-schedule`.

---

## 💻 Execução Local (sem Docker)

Para rodar apenas o serviço localmente:

### Exceute:
```bash
 ./mvnw clean install
 ./mvn spring-boot:run
```

A aplicação será iniciada em:

```text
http://localhost:8080/graphql
```

---

## 🔗 Integração com o serviço `ms-schedule`

### Visão geral do fluxo
**ms-schedule → RabbitMQ → Appointment History Service → MongoDB**

- O `ms-schedule` publica eventos de agendamento no **RabbitMQ**.
- O **Appointment History Service** consome esses eventos e persiste no **MongoDB**.
- Para autenticação/validação de acesso aos dados, o History chama o `ms-schedule` no endpoint de validação de token.

### ⚠️ IMPORTANTE - Ordem de inicialização
1) **Suba primeiro o Appointment History Service** (pois o broker **RabbitMQ** sobe junto dessa stack).
2) Depois **suba o `ms-schedule`** para publicar eventos no RabbitMQ já disponível.


### Rede Docker compartilhada
Crie uma rede Docker externa e conecte **ambos** os projetos nela:

```bash
  docker network create tech-net
```

### Subida recomendada
No repo do History (onde está RabbitMQ e Mongo)

#### Execute:
```bash
  docker network create tech-net        # uma única vez
  docker-compose up -d                  # sobe rabbit + mongo + history
```
No repo do ms-schedule

#### Execute:
```bash
  docker-compose up -d                 # sobe o produtor na mesma rede
```

---

## 🔍 Endpoints GraphQL

- **URL (local):** `http://localhost:8085/graphql`
- **Schema:** `src/main/resources/graphql/schema.graphqls`
- **Método HTTP:** `POST`
- **Header:** `Authorization: Bearer <token>`

---

### 📚 Queries disponíveis

#### 1) `appointmentHistoryById`
Retorna o histórico de um agendamento específico.

#### Exemplo de query:

```graphql
query AppointmentHistoryById {
    appointmentHistoryById(appointmentId: id) {
      appointmentId
      status
      createdAt
      scheduledAt
      ingestedAt
      observation
    }
  }
```

#### 2) `appointmentsByDoctorAndDate`

Lista histórico de um médico em um intervalo de datas.

#### Exemplo de query:

```graphql
query AppointmentHistoryById {
  appointmentsByDoctorAndDate(doctorId: id, startDate: date, endDate: date) {
    appointmentId
    status
    createdAt
    scheduledAt
    ingestedAt
    observation
  }
}
```

#### 3) `appointmentsByPatientAndDate`

Lista histórico de um paciente em um intervalo de datas.

#### Exemplo de query:

```graphql
query AppointmentsByPatientAndDate {
  appointmentsByPatientAndDate(patientId: id, startDate: date, endDate: date) {
    appointmentId
    status
    createdAt
    scheduledAt
    ingestedAt
    observation
  }
}
```
---

### ✏️ Mutation

#### 1) `updateObservationHistoryById`

Atualiza a observação de um histórico específico (restrito a médicos).

#### Exemplo de mutation:

```graphql
mutation UpdateObservationHistoryById {
  updateObservationHistoryById(appointmentId: id, observation: text) {
    appointmentId
    status
    createdAt
    scheduledAt
    ingestedAt
    observation
  }
}
```
---
### 🧪 Exemplo de chamada HTTP (cURL)

```curl
curl -X POST http://localhost:8085/graphql \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <token>" \
-d '{"query":"query($id:ID!){ appointmentHistoryById(appointmentId:$id){ appointmentId status observation }}","variables":{"id":"12345"}}'
```

---

### 🔐 Perfis e Permissões

| Perfil       | Leitura              |  Atualizar observações |
|---------------|----------------------|--------------------|
| **Médico**     | ✅                    |  ✅              |
| **Enfermeiro** | ✅                    | ❌                |
| **Paciente**   | ✅ (apenas o próprio) | ❌                 |

- Médico e Enfermeiro: podem consultar qualquer histórico.
- Paciente: só visualiza seus próprios históricos.
- Somente Médicos podem executar a mutation updateObservationHistoryById.

---

## 👩‍💻 Autoria

 - Desenvolvido por Mayara Bomfim, Matheus Braga, Webber Chagas e Raysse Cutrim
 - Módulo: Fase 3
 - Serviço: Appointment History Service
 - Versão: 0.0.1-SNAPSHOT
 - Stack: Java + Spring Boot + GraphQL + RabbitMQ + MongoDB