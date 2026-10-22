# Relatório do Projeto: Sistema de Gerenciamento de Medicamentos

Disciplina: Programação Orientada a Objetos III - UNIFAN
Professor: Daniel Corrêa da Silva
Aluno(a): Carlos Eduardo Ribeiro Dourado
Curso: Engenharia de Software e Segurança da Informação

## 1. Funcionalidades implementadas

- **Cadastro** de medicamentos com todos os dados e os dados do fornecedor.
- **Exclusão** do medicamento selecionado na tabela, com confirmação.
- **Consulta** por código ou nome, exibindo o resultado na tabela.
- **Listagem** de todos os medicamentos cadastrados (botão "Atualizar lista").
- **Persistência** em arquivo `medicamentos.csv`, com campos separados por ponto e vírgula. Os dados são carregados ao iniciar e gravados após cada cadastro ou exclusão.
- **Validação** de código (7 caracteres alfanuméricos), nome (mínimo de 3 caracteres), data de validade (não pode ser passada), quantidade (não negativa), preço (maior que zero) e CNPJ (formato e dígitos verificadores).
- **Validação do fornecedor:** razão social obrigatória, e-mail em formato válido e UF com 2 letras.
- **Relatórios com Stream API**, exibidos em uma janela:
  - medicamentos que vencem nos próximos 30 dias (`filter` e `map`);
  - medicamentos com estoque baixo, abaixo de 5 unidades (`filter` e `map`);
  - valor total do estoque por fornecedor (`groupingBy`, `mapping` e `reducing`);
  - medicamentos controlados x não controlados (`partitioningBy`).

## 2. Decisões de design

- **Arquitetura em camadas simples:** `model` (Medicamento e Fornecedor), `controller` (eventos da interface), `utils` (validação e acesso ao CSV) e `view` (FXML). Cada classe tem uma responsabilidade.
- **Composição:** Medicamento possui um Fornecedor, conforme o enunciado.
- **Classe Validador:** concentra as regras de validação e lança `IllegalArgumentException` com mensagens claras, que o controller mostra ao usuário em alertas.
- **Classe MedicamentoRepository:** isola a leitura e a gravação do CSV, de modo que o controller não precisa conhecer o formato do arquivo.
- **Tratamento de exceções:** erros de entrada (`IllegalArgumentException`) aparecem como aviso, e erros de arquivo (`IOException`) são convertidos em `RuntimeException` e exibidos como erro.
- **Proteção do CSV:** se o arquivo não puder ser lido, cadastro e exclusão ficam desativados para não sobrescrever os dados.
- **Código único:** o sistema impede o cadastro de dois medicamentos com o mesmo código.
- **Relatório por CNPJ:** o total por fornecedor agrupa pelo CNPJ, que é o identificador único do fornecedor.
- **Valores monetários com `BigDecimal`:** evita erros de arredondamento nos cálculos de preço e de total do estoque.
- **Colunas da tabela com lambdas:** em vez de `PropertyValueFactory`, o que evita depender de reflexão.
- **Caractere `;` nos textos:** na gravação do CSV, o ponto e vírgula digitado pelo usuário é trocado por vírgula, para não quebrar as colunas.
- **Interface:** formulário e tabela dentro de um `ScrollPane`, para a tela funcionar em monitores menores.

## 3. Como executar

Veja o arquivo `README.md`.