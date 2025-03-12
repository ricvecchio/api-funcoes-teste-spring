package com.transportadora.management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Clientes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @NotBlank
    @NotNull
    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 20, nullable = false)
    private String cpfCnpj;

    @Column(length = 100, nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contatosAdicionais;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String tipoPgto;

    @Column(nullable = false)
    private String infoPagamento;

    @Column(nullable = false)
    private String cepEntrega;

    @Column(nullable = false)
    private String logradouroEntrega;

    @Column(nullable = false)
    private String numeroEntrega;

    @Column(nullable = false)
    private String complementoEntrega;

    @Column(nullable = false)
    private String bairroEntrega;

    @Column(nullable = false)
    private String cidadeEntrega;

    @Column(nullable = false)
    private String estadoEntrega;

    @Column(nullable = false)
    private String sfobras;

    @Column(nullable = false)
    private String cno;

    @Column(nullable = false)
    private String ie;

    @Column(length = 15, nullable = false)
    private String mangueira;

    @Column(length = 12, nullable = false)
    private String precoCx5;

    @Column(length = 12, nullable = false)
    private String precoCx10;

    @Column(length = 12, nullable = false)
    private String precoCx15;

    @Column(length = 12, nullable = false)
    private String precoLv5;

    @Column(length = 12, nullable = false)
    private String precoLv10;

    @Column(length = 12, nullable = false)
    private String precoLv15;

    @Column(nullable = false)
    private String observacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacaoCliente;

    public Cliente() {
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContatosAdicionais() {
        return contatosAdicionais;
    }

    public void setContatosAdicionais(String contatosAdicionais) {
        this.contatosAdicionais = contatosAdicionais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoPgto() {
        return tipoPgto;
    }

    public void setTipoPgto(String tipoPgto) {
        this.tipoPgto = tipoPgto;
    }

    public String getInfoPagamento() {
        return infoPagamento;
    }

    public void setInfoPagamento(String infoPagamento) {
        this.infoPagamento = infoPagamento;
    }

    public String getCepEntrega() {
        return cepEntrega;
    }

    public void setCepEntrega(String cepEntrega) {
        this.cepEntrega = cepEntrega;
    }

    public String getLogradouroEntrega() {
        return logradouroEntrega;
    }

    public void setLogradouroEntrega(String logradouroEntrega) {
        this.logradouroEntrega = logradouroEntrega;
    }

    public String getNumeroEntrega() {
        return numeroEntrega;
    }

    public void setNumeroEntrega(String numeroEntrega) {
        this.numeroEntrega = numeroEntrega;
    }

    public String getComplementoEntrega() {
        return complementoEntrega;
    }

    public void setComplementoEntrega(String complementoEntrega) {
        this.complementoEntrega = complementoEntrega;
    }

    public String getBairroEntrega() {
        return bairroEntrega;
    }

    public void setBairroEntrega(String bairroEntrega) {
        this.bairroEntrega = bairroEntrega;
    }

    public String getCidadeEntrega() {
        return cidadeEntrega;
    }

    public void setCidadeEntrega(String cidadeEntrega) {
        this.cidadeEntrega = cidadeEntrega;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public String getSfobras() {
        return sfobras;
    }

    public void setSfobras(String sfobras) {
        this.sfobras = sfobras;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getMangueira() {
        return mangueira;
    }

    public void setMangueira(String mangueira) {
        this.mangueira = mangueira;
    }

    public String getPrecoCx5() {
        return precoCx5;
    }

    public void setPrecoCx5(String precoCx5) {
        this.precoCx5 = precoCx5;
    }

    public String getPrecoCx10() {
        return precoCx10;
    }

    public void setPrecoCx10(String precoCx10) {
        this.precoCx10 = precoCx10;
    }

    public String getPrecoCx15() {
        return precoCx15;
    }

    public void setPrecoCx15(String precoCx15) {
        this.precoCx15 = precoCx15;
    }

    public String getPrecoLv5() {
        return precoLv5;
    }

    public void setPrecoLv5(String precoLv5) {
        this.precoLv5 = precoLv5;
    }

    public String getPrecoLv10() {
        return precoLv10;
    }

    public void setPrecoLv10(String precoLv10) {
        this.precoLv10 = precoLv10;
    }

    public String getPrecoLv15() {
        return precoLv15;
    }

    public void setPrecoLv15(String precoLv15) {
        this.precoLv15 = precoLv15;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDateTime getDataAtualizacaoCliente() {
        return dataAtualizacaoCliente;
    }

    public void setDataAtualizacaoCliente(LocalDateTime dataAtualizacaoCliente) {
        this.dataAtualizacaoCliente = dataAtualizacaoCliente;
    }
}
