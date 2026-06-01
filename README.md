# Sistema Acadêmico - API REST com Spring Boot

## 📖 Explicação do Projeto

Este projeto consiste em uma API REST desenvolvida utilizando Spring Boot para gerenciamento acadêmico.

O sistema permite:

- Cadastro de alunos
- Cadastro de professores
- Cadastro de disciplinas
- Matrícula de alunos em disciplinas
- Atualização de notas
- Trancamento de matrícula
- Emissão de histórico escolar

Além das operações básicas de CRUD, o sistema utiliza relacionamentos entre entidades, DTOs para transferência de dados, Enum para controle de status e regras de negócio para aprovação e reprovação de alunos.

---

# 🛠 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- Insomnia
- DBeaver

---

# 📂 Detalhamento do Código

## Model

As classes da camada Model representam as entidades do banco de dados.

### Aluno

Representa os alunos cadastrados no sistema.

Principais atributos:

```java
private Long id;
private String nomeCompleto;
private String email;
private String cpf;
```

A anotação `@Entity` indica que a classe representa uma tabela no banco de dados.

---

### Professor

Representa os professores cadastrados.

Possui os mesmos atributos da entidade Aluno.

---

### Disciplina

Representa as disciplinas oferecidas pela instituição.

```java
@ManyToOne
@JoinColumn(name = "professor_id")
private Professor professor;
```

Esse relacionamento indica que várias disciplinas podem estar associadas a um mesmo professor.

---

### MatriculaAluno

Representa a matrícula de um aluno em uma disciplina.

```java
@ManyToOne
@JoinColumn(name = "aluno_id")
private Aluno aluno;

@ManyToOne
@JoinColumn(name = "disciplina_id")
private Disciplina disciplina;
```

A matrícula também possui:

```java
private Double nota1;
private Double nota2;
private MatriculaAlunoStatusEnum status;
```

---

## Repository

Responsável pelo acesso ao banco de dados.

Exemplo:

```java
public interface AlunoRepository extends JpaRepository<Aluno, Long>
```

Através do JPA são disponibilizados métodos prontos como:

- save()
- findAll()
- findById()
- deleteById()

Sem necessidade de escrever SQL manualmente.

---

## Service

Responsável pelas regras de negócio da aplicação.

### Criação de Matrícula

Ao criar uma matrícula, o sistema define automaticamente o status:

```java
MATRICULADO
```

---

### Atualização de Notas

Ao atualizar as notas, o sistema calcula automaticamente a média:

```java
media = (nota1 + nota2) / 2
```

Caso a média seja maior ou igual a 7:

```java
APROVADO
```

Caso contrário:

```java
REPROVADO
```

---

### Trancamento de Matrícula

O sistema permite o trancamento apenas quando a matrícula está com status:

```java
MATRICULADO
```

Caso contrário é lançada uma exceção utilizando:

```java
ResponseStatusException
```

---

### Emissão de Histórico

O histórico é gerado através da busca das matrículas de um aluno e montagem de um DTO contendo:

- Dados do aluno
- Disciplinas cursadas
- Notas
- Média
- Status

---

## DTOs

O projeto utiliza DTOs (Data Transfer Objects) para transferência de dados.

### AtualizarNotasRequestDTO

Utilizado para receber apenas as notas que serão atualizadas.

### DisciplinasAlunoResponseDTO

Utilizado para retornar informações das disciplinas cursadas.

### HistoricoAlunoResponseDTO

Utilizado para retornar o histórico completo do aluno.

---

## Enum

O sistema utiliza:

```java
MatriculaAlunoStatusEnum
```

Possíveis valores:

- MATRICULADO
- APROVADO
- REPROVADO
- TRANCADO
- DESLIGADO

---

## Controller

Responsável por receber as requisições HTTP e retornar respostas ao cliente.

Principais endpoints:

### Alunos

| Método | Endpoint |
|----------|----------|
| POST | /alunos |
| GET | /alunos |
| GET | /alunos/{id} |
| PUT | /alunos/{id} |
| DELETE | /alunos/{id} |

---

### Professores

| Método | Endpoint |
|----------|----------|
| POST | /professores |
| GET | /professores |
| GET | /professores/{id} |
| PUT | /professores/{id} |
| DELETE | /professores/{id} |

---

### Disciplinas

| Método | Endpoint |
|----------|----------|
| POST | /disciplinas |
| GET | /disciplinas |

---

### Matrículas

| Método | Endpoint |
|----------|----------|
| POST | /matriculas |
| PATCH | /matriculas/trancar/{id} |
| PATCH | /matriculas/atualizar-notas/{id} |
| GET | /matriculas/emitir-historico/{alunoId} |

---

# 🏗 Arquitetura Utilizada

O projeto utiliza arquitetura em camadas.

```text
Cliente (Insomnia)
        ↓
Controller
        ↓
Service
        ↓
Repository
        ↓
PostgreSQL
```

## Controller

Recebe as requisições HTTP.

## Service

Aplica as regras de negócio.

## Repository

Realiza acesso ao banco de dados.

## Model

Representa as entidades persistidas.

Essa arquitetura permite melhor organização, manutenção e escalabilidade do sistema.

---

# 📸 Requisições Realizadas no Insomnia

## Cadastro de Aluno

<img width="773" height="390" alt="image" src="https://github.com/user-attachments/assets/106d0898-9e43-4853-a1e8-d863bd9a91cf" />


```md
![Cadastro de Aluno](docs/post-aluno.png)
```

---

## Cadastro de Professor

<img width="777" height="355" alt="image" src="https://github.com/user-attachments/assets/fa013d9b-c861-4489-a276-bd38006254d3" />


```md
![Cadastro de Professor](docs/post-professor.png)
```

---

## Cadastro de Disciplina

<img width="798" height="408" alt="image" src="https://github.com/user-attachments/assets/d2f95d1d-d6c0-4c9d-8a2c-6be78ab64cc9" />


```md
![Cadastro de Disciplina](docs/post-disciplina.png)
```

---

## Matrícula de Aluno

<img width="760" height="440" alt="image" src="https://github.com/user-attachments/assets/323e13cf-6aed-4213-8e68-b8831630ce6c" />


```md
![Matrícula](docs/post-matricula.png)
```

---

## Atualização de Notas

<img width="780" height="400" alt="image" src="https://github.com/user-attachments/assets/a0ef66e9-0386-4623-ae0c-0d5a2f3d4fcb" />


```md
![Atualização de Notas](docs/patch-notas.png)
```

---

## Emissão de Histórico

<img width="806" height="599" alt="image" src="https://github.com/user-attachments/assets/798d61d5-9a0b-4646-9950-17c3fd176613" />


```md
![Histórico](docs/historico.png)
```

---

# 🗄 Banco de Dados (DBeaver)

## Tabela Aluno

<img width="688" height="484" alt="image" src="https://github.com/user-attachments/assets/f5e8f551-ad91-413b-b1b7-7da9a5bac6ef" />


```md
![Tabela Aluno](docs/tabela-aluno.png)
```

---

## Tabela Professor

<img width="744" height="377" alt="image" src="https://github.com/user-attachments/assets/d98cde84-4b7d-4f50-a906-e73f92c15f97" />


```md
![Tabela Professor](docs/tabela-professor.png)
```

---

## Tabela Disciplina

<img width="734" height="344" alt="image" src="https://github.com/user-attachments/assets/987483d7-cb8e-4851-8a64-a9222cc149a8" />


```md
![Tabela Disciplina](docs/tabela-disciplina.png)
```

---

## Tabela MatriculaAluno

<img width="906" height="377" alt="image" src="https://github.com/user-attachments/assets/294ff7fa-cd8f-4d7b-9439-346e787c2771" />


```md
![Tabela Matricula](docs/tabela-matricula.png)
```

---

# 🚀 Execução do Projeto

1. Clonar o repositório

```bash
git clone URL_DO_REPOSITORIO
```

2. Configurar o PostgreSQL

3. Configurar o arquivo:

```properties
application.properties
```

4. Executar a aplicação

```bash
mvn spring-boot:run
```

5. Testar os endpoints utilizando o Insomnia.
