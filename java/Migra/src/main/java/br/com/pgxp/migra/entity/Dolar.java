/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author desktop
 */
@Entity
@Table(name = "dolar")
@NamedQueries({
    @NamedQuery(name = "Dolar.findAll", query = "SELECT d FROM Dolar d"),
    @NamedQuery(name = "Dolar.findById", query = "SELECT d FROM Dolar d WHERE d.id = :id"),
    @NamedQuery(name = "Dolar.findByData", query = "SELECT d FROM Dolar d WHERE d.data = :data"),
    @NamedQuery(name = "Dolar.findByAno", query = "SELECT d FROM Dolar d WHERE d.ano = :ano"),
    @NamedQuery(name = "Dolar.findByMes", query = "SELECT d FROM Dolar d WHERE d.mes = :mes"),
    @NamedQuery(name = "Dolar.findByDia", query = "SELECT d FROM Dolar d WHERE d.dia = :dia"),
    @NamedQuery(name = "Dolar.findBySemana", query = "SELECT d FROM Dolar d WHERE d.semana = :semana"),
    @NamedQuery(name = "Dolar.findBySemanaano", query = "SELECT d FROM Dolar d WHERE d.semanaano = :semanaano"),
    @NamedQuery(name = "Dolar.findByDiaano", query = "SELECT d FROM Dolar d WHERE d.diaano = :diaano"),
    @NamedQuery(name = "Dolar.findByValor", query = "SELECT d FROM Dolar d WHERE d.valor = :valor")})
public class Dolar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "dia")
    private Integer dia;
    @Column(name = "semana")
    private Integer semana;
    @Column(name = "semanaano")
    private Integer semanaano;
    @Column(name = "diaano")
    private Integer diaano;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;

    public Dolar() {
    }

    public Dolar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dolar)) {
            return false;
        }
        Dolar other = (Dolar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.pgxp.migra.entity.Dolar[ id=" + id + " ]";
    }
    
}
