/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author desktop
 */
@Entity
@Table(name = "grupo15")
public class Grupo15 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ano")
    private Integer ano;
    @Basic(optional = false)
    @Column(name = "mes")
    private Integer mes;
    @Basic(optional = false)
    @Column(name = "dia")
    private Integer dia;
    @Column(name = "semana")
    private Integer semana;
    @Column(name = "semanaano")
    private Integer semanaano;
    @Column(name = "diaano")
    private Integer diaano;

    @Column(name = "mercado")
    private String mercado;

    @Column(name = "produto")
    private String produto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;

    @Column(name = "status")
    private String status;

    public Grupo15() {
    }

    public Grupo15(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public Integer getSemanaano() {
        return semanaano;
    }

    public void setSemanaano(Integer semanaano) {
        this.semanaano = semanaano;
    }

    public Integer getDiaano() {
        return diaano;
    }

    public void setDiaano(Integer diaano) {
        this.diaano = diaano;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Grupo15{" + "id=" + id + ", ano=" + ano + ", mes=" + mes + ", dia=" + dia + ", semana=" + semana + ", semanaano=" + semanaano + ", diaano=" + diaano + ", mercado=" + mercado + ", produto=" + produto + ", valor=" + valor + ", status=" + status + '}';
    }

}
