package org.sistemamedicamentos.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.sistemamedicamentos.model.Fornecedor;
import org.sistemamedicamentos.model.Medicamento;
import org.sistemamedicamentos.utils.MedicamentoRepository;
import org.sistemamedicamentos.utils.Validador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MedicamentosController {

    @FXML private TextField txtNome, txtCodigo, txtDescricao, txtPrincipioAtivo,
            txtQuantidade, txtPreco, txtPesquisa,
            txtCnpj, txtRazaoSocial, txtTelefone, txtEmail, txtCidade, txtEstado;
    @FXML private DatePicker dpValidade;
    @FXML private CheckBox chkControlado;

    @FXML private Button btnCadastrar, btnExcluir;

    @FXML private TableView<Medicamento> tabelaMedicamentos;
    @FXML private TableColumn<Medicamento, String> colCodigo, colNome, colPrincipioAtivo;
    @FXML private TableColumn<Medicamento, LocalDate> colValidade;
    @FXML private TableColumn<Medicamento, Integer> colQuantidade;
    @FXML private TableColumn<Medicamento, BigDecimal> colPreco;
    @FXML private TableColumn<Medicamento, Boolean> colControlado;

    private final MedicamentoRepository repositorio = new MedicamentoRepository();
    private final List<Medicamento> medicamentos = new ArrayList<>();
    private final ObservableList<Medicamento> dadosTabela = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colPrincipioAtivo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrincipioAtivo()));
        colValidade.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDataValidade()));
        colQuantidade.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getQuantidadeEstoque()));
        colPreco.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getPreco()));
        colControlado.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().isControlado()));
        tabelaMedicamentos.setItems(dadosTabela);

        try {
            medicamentos.addAll(repositorio.carregar());
        } catch (RuntimeException e) {
            alerta(Alert.AlertType.ERROR, e.getMessage()
                    + "\nO cadastro e a exclusão foram desativados para não sobrescrever o arquivo.");
            btnCadastrar.setDisable(true);
            btnExcluir.setDisable(true);
        }
        atualizarTabela();
    }

    @FXML
    private void onCadastrar() {
        try {
            String codigo = txtCodigo.getText().trim();
            Validador.validarCodigo(codigo);
            Validador.validarNome(txtNome.getText());
            Validador.validarData(dpValidade.getValue());

            int quantidade;
            try {
                quantidade = Integer.parseInt(txtQuantidade.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantidade deve ser um número inteiro.");
            }
            Validador.validarQuantidade(quantidade);

            BigDecimal preco;
            try {
                preco = new BigDecimal(txtPreco.getText().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Preço inválido.");
            }
            Validador.validarPreco(preco);
            Validador.validarCnpj(txtCnpj.getText());
            Validador.validarFornecedor(txtRazaoSocial.getText(), txtEmail.getText(), txtEstado.getText());

            if (medicamentos.stream().anyMatch(m -> m.getCodigo().equalsIgnoreCase(codigo)))
                throw new IllegalArgumentException("Já existe um medicamento com esse código.");

            Fornecedor f = new Fornecedor(txtCnpj.getText().replaceAll("\\D", ""),
                    txtRazaoSocial.getText().trim(), txtTelefone.getText().trim(),
                    txtEmail.getText().trim(), txtCidade.getText().trim(),
                    txtEstado.getText().trim().toUpperCase());

            medicamentos.add(new Medicamento(codigo, txtNome.getText().trim(),
                    txtDescricao.getText().trim(), txtPrincipioAtivo.getText().trim(),
                    dpValidade.getValue(), quantidade, preco, chkControlado.isSelected(), f));

            repositorio.salvar(medicamentos);
            atualizarTabela();
            onLimpar();
            alerta(Alert.AlertType.INFORMATION, "Medicamento cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            alerta(Alert.AlertType.WARNING, e.getMessage());
        } catch (RuntimeException e) {
            alerta(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void onLimpar() {
        txtNome.clear(); txtCodigo.clear(); txtDescricao.clear(); txtPrincipioAtivo.clear();
        txtQuantidade.clear(); txtPreco.clear(); txtPesquisa.clear();
        txtCnpj.clear(); txtRazaoSocial.clear(); txtTelefone.clear();
        txtEmail.clear(); txtCidade.clear(); txtEstado.clear();
        dpValidade.setValue(null);
        chkControlado.setSelected(false);
    }

    @FXML
    private void onPesquisar() {
        String termo = txtPesquisa.getText().trim().toLowerCase();
        if (termo.isEmpty()) {
            atualizarTabela();
            return;
        }
        List<Medicamento> achados = medicamentos.stream()
                .filter(m -> m.getCodigo().toLowerCase().contains(termo)
                        || m.getNome().toLowerCase().contains(termo))
                .collect(Collectors.toList());
        if (achados.isEmpty()) {
            alerta(Alert.AlertType.INFORMATION, "Nenhum medicamento encontrado para: " + termo);
        }
        dadosTabela.setAll(achados);
    }

    @FXML
    private void onExcluir() {
        Medicamento selecionado = tabelaMedicamentos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            alerta(Alert.AlertType.WARNING, "Selecione um medicamento na tabela.");
            return;
        }
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir " + selecionado.getNome() + "?", ButtonType.YES, ButtonType.NO);
        if (confirmacao.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                medicamentos.remove(selecionado);
                repositorio.salvar(medicamentos);
                atualizarTabela();
            } catch (RuntimeException e) {
                alerta(Alert.AlertType.ERROR, e.getMessage());
            }
        }
    }

    @FXML
    private void onAtualizar() {
        txtPesquisa.clear();
        atualizarTabela();
    }

    @FXML
    private void onRelatorios() {
        LocalDate hoje = LocalDate.now();
        StringBuilder sb = new StringBuilder();

        sb.append("=== Vencendo nos próximos 30 dias ===\n");
        medicamentos.stream()
                .filter(m -> !m.getDataValidade().isBefore(hoje)
                        && !m.getDataValidade().isAfter(hoje.plusDays(30)))
                .map(m -> m.getCodigo() + " - " + m.getNome() + " (vence " + m.getDataValidade() + ")")
                .forEach(l -> sb.append(l).append("\n"));

        sb.append("\n=== Estoque baixo (menos de 5 unidades) ===\n");
        medicamentos.stream()
                .filter(m -> m.getQuantidadeEstoque() < 5)
                .map(m -> m.getCodigo() + " - " + m.getNome() + " (" + m.getQuantidadeEstoque() + " un.)")
                .forEach(l -> sb.append(l).append("\n"));

        sb.append("\n=== Valor total do estoque por fornecedor (CNPJ) ===\n");
        Map<String, BigDecimal> totais = medicamentos.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getFornecedor().getCnpj(),
                        TreeMap::new,
                        Collectors.mapping(
                                m -> m.getPreco().multiply(BigDecimal.valueOf(m.getQuantidadeEstoque())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        totais.forEach((cnpj, total) -> sb.append("CNPJ ").append(cnpj)
                .append(": R$ ").append(total).append("\n"));

        sb.append("\n=== Controlados x Não controlados ===\n");
        Map<Boolean, List<Medicamento>> part = medicamentos.stream()
                .collect(Collectors.partitioningBy(Medicamento::isControlado));
        sb.append("Controlados: ").append(part.get(true).stream().map(Medicamento::getNome)
                .collect(Collectors.joining(", "))).append("\n");
        sb.append("Não controlados: ").append(part.get(false).stream().map(Medicamento::getNome)
                .collect(Collectors.joining(", "))).append("\n");

        TextArea area = new TextArea(sb.toString());
        area.setEditable(false);
        area.setPrefSize(600, 400);
        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Relatórios");
        dialogo.setHeaderText("Relatórios do estoque");
        dialogo.getDialogPane().setContent(area);
        dialogo.showAndWait();
    }

    private void atualizarTabela() {
        dadosTabela.setAll(medicamentos);
    }

    private void alerta(Alert.AlertType tipo, String mensagem) {
        new Alert(tipo, mensagem).showAndWait();
    }
}