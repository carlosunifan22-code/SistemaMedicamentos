
package org.sistemamedicamentos.model;

public class Fornecedor {

    private String cnpj;
    private String razaoSocial;
    private String telefone;
    private String email;
    private String cidade;
    private String estado;

    public Fornecedor(String cnpj, String razaoSocial, String telefone,
                      String email, String cidade, String estado) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }
}
