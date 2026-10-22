
package org.sistemamedicamentos.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Medicamento {

    private String codigo;
    private String nome;
    private String descricao;
    private String principioAtivo;
    private LocalDate dataValidade;
    private int quantidadeEstoque;
    private BigDecimal preco;
    private boolean controlado;
    private Fornecedor fornecedor;

    public Medicamento(String codigo, String nome, String descricao,
                       String principioAtivo, LocalDate dataValidade,
                       int quantidadeEstoque, BigDecimal preco,
                       boolean controlado, Fornecedor fornecedor) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.principioAtivo = principioAtivo;
        this.dataValidade = dataValidade;
        this.quantidadeEstoque = quantidadeEstoque;
        this.preco = preco;
        this.controlado = controlado;
        this.fornecedor = fornecedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public boolean isControlado() {
        return controlado;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
