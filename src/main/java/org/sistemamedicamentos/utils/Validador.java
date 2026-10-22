package org.sistemamedicamentos.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Validador {

    public static void validarCodigo(String codigo) {
        if (codigo == null || !codigo.matches("[A-Za-z0-9]{7}"))
            throw new IllegalArgumentException("Código deve ter 7 caracteres alfanuméricos.");
    }

    public static void validarNome(String nome) {
        if (nome == null || nome.trim().length() < 3)
            throw new IllegalArgumentException("Nome deve ter no mínimo 3 caracteres.");
    }

    public static void validarData(LocalDate data) {
        if (data == null || data.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Data de validade não pode ser vazia ou passada.");
    }

    public static void validarQuantidade(int quantidade) {
        if (quantidade < 0)
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
    }

    public static void validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
    }

    public static void validarCnpj(String cnpj) {
        String n = cnpj == null ? "" : cnpj.replaceAll("\\D", "");
        if (n.length() != 14 || n.chars().distinct().count() == 1
                || n.charAt(12) - '0' != digito(n, 12)
                || n.charAt(13) - '0' != digito(n, 13))
            throw new IllegalArgumentException("CNPJ inválido.");
    }

    public static void validarFornecedor(String razaoSocial, String email, String uf) {
        if (razaoSocial == null || razaoSocial.trim().isEmpty())
            throw new IllegalArgumentException("Razão social não pode ser vazia.");
        if (email == null || !email.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new IllegalArgumentException("E-mail inválido.");
        if (uf == null || !uf.trim().matches("[A-Za-z]{2}"))
            throw new IllegalArgumentException("Estado deve ter 2 letras (UF).");
    }

    private static int digito(String n, int tamanho) {
        int soma = 0, peso = tamanho - 7;
        for (int i = 0; i < tamanho; i++) {
            soma += (n.charAt(i) - '0') * peso--;
            if (peso < 2) peso = 9;
        }
        int r = soma % 11;
        return r < 2 ? 0 : 11 - r;
    }
}