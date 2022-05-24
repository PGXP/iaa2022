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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author desktop
 */
@Entity
@Table(name = "clima")
@NamedQueries({
    @NamedQuery(name = "Clima.findAll", query = "SELECT c FROM Clima c"),
    @NamedQuery(name = "Clima.findById", query = "SELECT c FROM Clima c WHERE c.id = :id"),
    @NamedQuery(name = "Clima.findByAno", query = "SELECT c FROM Clima c WHERE c.ano = :ano"),
    @NamedQuery(name = "Clima.findByMes", query = "SELECT c FROM Clima c WHERE c.mes = :mes"),
    @NamedQuery(name = "Clima.findByDia", query = "SELECT c FROM Clima c WHERE c.dia = :dia"),
    @NamedQuery(name = "Clima.findBySemana", query = "SELECT c FROM Clima c WHERE c.semana = :semana"),
    @NamedQuery(name = "Clima.findBySemanaano", query = "SELECT c FROM Clima c WHERE c.semanaano = :semanaano"),
    @NamedQuery(name = "Clima.findByDiaano", query = "SELECT c FROM Clima c WHERE c.diaano = :diaano"),
    @NamedQuery(name = "Clima.findByTempmax", query = "SELECT c FROM Clima c WHERE c.tempmax = :tempmax"),
    @NamedQuery(name = "Clima.findByTempmin", query = "SELECT c FROM Clima c WHERE c.tempmin = :tempmin"),
    @NamedQuery(name = "Clima.findByUmidademax", query = "SELECT c FROM Clima c WHERE c.umidademax = :umidademax"),
    @NamedQuery(name = "Clima.findByUmidademin", query = "SELECT c FROM Clima c WHERE c.umidademin = :umidademin")})
public class Clima implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @Column(name = "tempmax")
    private Float tempmax;
    @Column(name = "tempmin")
    private Float tempmin;
    @Column(name = "umidademax")
    private Float umidademax;
    @Column(name = "umidademin")
    private Float umidademin;

    public Clima() {
    }

    public Clima(Integer id) {
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

    public Float getTempmax() {
        return tempmax;
    }

    public void setTempmax(Float tempmax) {
        this.tempmax = tempmax;
    }

    public Float getTempmin() {
        return tempmin;
    }

    public void setTempmin(Float tempmin) {
        this.tempmin = tempmin;
    }

    public Float getUmidademax() {
        return umidademax;
    }

    public void setUmidademax(Float umidademax) {
        this.umidademax = umidademax;
    }

    public Float getUmidademin() {
        return umidademin;
    }

    public void setUmidademin(Float umidademin) {
        this.umidademin = umidademin;
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
        if (!(object instanceof Clima)) {
            return false;
        }
        Clima other = (Clima) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.pgxp.migra.entity.Clima[ id=" + id + " ]";
    }
    
}
