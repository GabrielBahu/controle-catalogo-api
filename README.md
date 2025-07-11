# 📦 Controle de Catálogo API

Este projeto é uma API REST desenvolvida em Java 17 com foco na gestão de catálogos de produtos. A aplicação foi criada com base no desafio técnico [new-test-backend-nodejs](https://github.com/githubanotaai/new-test-backend-nodejs), mas implementada com tecnologias robustas do ecossistema Java, seguindo boas práticas de arquitetura e qualidade de código.

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5
- MongoDB
- Arquitetura Hexagonal (Ports and Adapters)
- Testes unitários com JUnit 5 e Mockito
- Swagger/OpenAPI (springdoc-openapi)
- SonarQube para análise estática e cobertura de código
- Lombok
- MapStruct

---

## 🧱 Arquitetura

A API segue o padrão de **Arquitetura Hexagonal**, separando bem as responsabilidades em camadas de:

- **Domain**: Regras de negócio
- **Application**: Casos de uso
- **Adapters (in/out)**: Interfaces de entrada (controllers) e saída (repositórios, integrações)
- **Configuration**: Beans, configurações gerais

Esse modelo garante testabilidade, flexibilidade e manutenção mais eficiente do código.

---

## 🚀 Como executar o projeto

### Pré-requisitos

- Java 17
- Maven 3.x
- MongoDB
- (Opcional) SonarQube rodando localmente

### Passos

```bash
# Clone o repositório
git clone https://github.com/GabrielBahu/controle-catalogo-api.git

# Acesse o diretório
cd controle-catalogo-api

# Compile e execute o projeto
./mvnw spring-boot:run
```

A aplicação será iniciada na porta padrão `http://localhost:8080`.

---

## 🧪 Executando os testes

```bash
./mvnw test
```

Os testes unitários utilizam **Mockito** e cobrem os principais fluxos da aplicação.

---

## 📊 Análise com SonarQube

A análise de qualidade de código e cobertura é feita com SonarQube.

```bash
./mvnw clean verify sonar:sonar
```


---

## 📘 Documentação da API

Após subir o projeto, acesse:

```
http://localhost:8080/swagger-ui.html
```
ou
```
http://localhost:8080/swagger-ui/index.html
```

---

## 📂 Estrutura de diretórios

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── gabrielbahu/
│   │           └── catalogo/
│   │               ├── application/
│   │               ├── domain/
│   │               ├── infrastructure/
│   │               └── config/
│   └── resources/
└── test/
```

---

## 👨‍💻 Autor

Desenvolvido por **Gabriel Bahu**  
📧 [gabrielbahu@gmail.com](mailto:gabrielbahu@gmail.com)  
📄 [LinkedIn](https://www.linkedin.com/in/gabrielbahu/)  
🚀 Portfólio completo: [github.com/GabrielBahu](https://github.com/GabrielBahu)

---

## 📌 Observações

- Projeto construído com foco em boas práticas de clean code e arquitetura limpa.
- Adaptado de um desafio técnico originalmente proposto em Node.js.