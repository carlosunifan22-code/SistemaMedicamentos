# Sistema de Gerenciamento de Medicamentos

Sistema em Java com interface JavaFX para gerenciar o estoque de medicamentos de uma farmácia.

Disciplina: Programação Orientada a Objetos III - UNIFAN

## Requisitos

- JDK 21
- Maven (ou o Maven Wrapper `mvnw`, que já vem no projeto)

## Como executar

### Pelo IntelliJ

1. Abra o projeto (File > Open > pasta do projeto).
2. Aguarde o Maven baixar as dependências.
3. Abra `src/main/java/org/sistemamedicamentos/Launcher.java` e clique em Run.

### Pelo terminal

```
mvnw.cmd javafx:run
```

## Funcionalidades

- Cadastrar, excluir, consultar por código e listar medicamentos
- Persistência em `medicamentos.csv` (separado por ponto e vírgula), criado na pasta raiz do projeto
- Validação de código, nome, data de validade, quantidade, preço e CNPJ
- Relatórios com Stream API: vencimento em 30 dias, estoque baixo, valor total por fornecedor e controlados x não controlados

## Estrutura

- `model`: classes Medicamento e Fornecedor
- `controller`: MedicamentosController (eventos da tela)
- `utils`: Validador e MedicamentoRepository (CSV)
- `resources/.../view`: medicamentos-view.fxml