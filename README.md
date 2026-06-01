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

INSERIR PRINT AQUI

```md
![Cadastro de Aluno](docs/post-aluno.png)
```

---

## Cadastro de Professor

INSERIR PRINT AQUI

```md
![Cadastro de Professor](docs/post-professor.png)
```

---

## Cadastro de Disciplina

INSERIR PRINT AQUI

```md
![Cadastro de Disciplina](docs/post-disciplina.png)
```

---

## Matrícula de Aluno

INSERIR PRINT AQUI

```md
![Matrícula](docs/post-matricula.png)
```

---

## Atualização de Notas

INSERIR PRINT AQUI

```md
![Atualização de Notas](docs/patch-notas.png)
```

---

## Emissão de Histórico

INSERIR PRINT AQUI

```md
![Histórico](docs/historico.png)
```

---

# 🗄 Banco de Dados (DBeaver)

## Tabela Aluno

Inserir print da tabela aluno contendo os registros utilizados nos testes.

```md
![Tabela Aluno](docs/tabela-aluno.png)
```

---

## Tabela Professor

Inserir print da tabela professor contendo os registros utilizados nos testes.

```md
![Tabela Professor](docs/tabela-professor.png)
```

---

## Tabela Disciplina

Inserir print da tabela disciplina.

```md
![Tabela Disciplina](docs/tabela-disciplina.png)
```

---

## Tabela MatriculaAluno

Inserir print da tabela matrícula.

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
