package org.sistemamedicamentos.utils;

import org.sistemamedicamentos.model.Fornecedor;
import org.sistemamedicamentos.model.Medicamento;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MedicamentoRepository {

    private final Path arquivo = Path.of("medicamentos.csv");

    public List<Medicamento> carregar() {
        List<Medicamento> lista = new ArrayList<>();
        if (!Files.exists(arquivo)) return lista;
        try {
            for (String linha : Files.readAllLines(arquivo)) {
                if (linha.isBlank()) continue;
                String[] c = linha.split(";", -1);
                if (c.length < 14) continue;
                Fornecedor f = new Fornecedor(c[8], c[9], c[10], c[11], c[12], c[13]);
                lista.add(new Medicamento(c[0], c[1], c[2], c[3], LocalDate.parse(c[4]),
                        Integer.parseInt(c[5]), new BigDecimal(c[6]),
                        Boolean.parseBoolean(c[7]), f));
            }
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Erro ao ler o arquivo CSV: " + e.getMessage(), e);
        }
        return lista;
    }

    public void salvar(List<Medicamento> lista) {
        List<String> linhas = lista.stream().map(m -> String.join(";",
                limpa(m.getCodigo()), limpa(m.getNome()), limpa(m.getDescricao()),
                limpa(m.getPrincipioAtivo()), m.getDataValidade().toString(),
                String.valueOf(m.getQuantidadeEstoque()), m.getPreco().toPlainString(),
                String.valueOf(m.isControlado()),
                limpa(m.getFornecedor().getCnpj()), limpa(m.getFornecedor().getRazaoSocial()),
                limpa(m.getFornecedor().getTelefone()), limpa(m.getFornecedor().getEmail()),
                limpa(m.getFornecedor().getCidade()), limpa(m.getFornecedor().getEstado())
        )).collect(Collectors.toList());
        try {
            Files.write(arquivo, linhas);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar o arquivo CSV: " + e.getMessage(), e);
        }
    }

    private String limpa(String s) {
        return s == null ? "" : s.replace(";", ",");
    }
}