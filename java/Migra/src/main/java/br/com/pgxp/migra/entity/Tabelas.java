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
@Table(name = "tabelas")
@NamedQueries({
    @NamedQuery(name = "Tabelas.findAll", query = "SELECT t FROM Tabelas t"),
    @NamedQuery(name = "Tabelas.findById", query = "SELECT t FROM Tabelas t WHERE t.id = :id"),
    @NamedQuery(name = "Tabelas.findByAno", query = "SELECT t FROM Tabelas t WHERE t.ano = :ano"),
    @NamedQuery(name = "Tabelas.findByMes", query = "SELECT t FROM Tabelas t WHERE t.mes = :mes"),
    @NamedQuery(name = "Tabelas.findByDia", query = "SELECT t FROM Tabelas t WHERE t.dia = :dia"),
    @NamedQuery(name = "Tabelas.findByIdbairro", query = "SELECT t FROM Tabelas t WHERE t.idbairro = :idbairro"),
    @NamedQuery(name = "Tabelas.findByIdfilial", query = "SELECT t FROM Tabelas t WHERE t.idfilial = :idfilial"),
    @NamedQuery(name = "Tabelas.findByIdlocal", query = "SELECT t FROM Tabelas t WHERE t.idlocal = :idlocal"),
    @NamedQuery(name = "Tabelas.findByIdgrupo", query = "SELECT t FROM Tabelas t WHERE t.idgrupo = :idgrupo"),
    @NamedQuery(name = "Tabelas.findByIdproduto", query = "SELECT t FROM Tabelas t WHERE t.idproduto = :idproduto"),
    @NamedQuery(name = "Tabelas.findByValor", query = "SELECT t FROM Tabelas t WHERE t.valor = :valor"),
    @NamedQuery(name = "Tabelas.findBySemana", query = "SELECT t FROM Tabelas t WHERE t.semana = :semana"),
    @NamedQuery(name = "Tabelas.findBySemanaano", query = "SELECT t FROM Tabelas t WHERE t.semanaano = :semanaano"),
    @NamedQuery(name = "Tabelas.findByDiaano", query = "SELECT t FROM Tabelas t WHERE t.diaano = :diaano"),
    @NamedQuery(name = "Tabelas.findByDolar", query = "SELECT t FROM Tabelas t WHERE t.dolar = :dolar"),
    @NamedQuery(name = "Tabelas.findByIpca", query = "SELECT t FROM Tabelas t WHERE t.ipca = :ipca")})
public class Tabelas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ano")
    private short ano;
    @Basic(optional = false)
    @Column(name = "mes")
    private short mes;
    @Basic(optional = false)
    @Column(name = "dia")
    private short dia;
    @Basic(optional = false)
    @Column(name = "idbairro")
    private int idbairro;
    @Basic(optional = false)
    @Column(name = "idfilial")
    private int idfilial;
    @Basic(optional = false)
    @Column(name = "idlocal")
    private int idlocal;
    @Basic(optional = false)
    @Column(name = "idgrupo")
    private int idgrupo;
    @Basic(optional = false)
    @Column(name = "idproduto")
    private int idproduto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "semana")
    private Integer semana;
    @Column(name = "semanaano")
    private Integer semanaano;
    @Column(name = "diaano")
    private Integer diaano;
    @Column(name = "dolar")
    private Double dolar;
    @Column(name = "ipca")
    private Double ipca;

    public Tabelas() {
    }

    public Tabelas(Integer id) {
        this.id = id;
    }

    public Tabelas(Integer id, short ano, short mes, short dia, int idbairro, int idfilial, int idlocal, int idgrupo, int idproduto) {
        this.id = id;
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.idbairro = idbairro;
        this.idfilial = idfilial;
        this.idlocal = idlocal;
        this.idgrupo = idgrupo;
        this.idproduto = idproduto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getAno() {
        return ano;
    }

    public void setAno(short ano) {
        this.ano = ano;
    }

    public short getMes() {
        return mes;
    }

    public void setMes(short mes) {
        this.mes = mes;
    }

    public short getDia() {
        return dia;
    }

    public void setDia(short dia) {
        this.dia = dia;
    }

    public int getIdbairro() {
        return idbairro;
    }

    public void setIdbairro(int idbairro) {
        this.idbairro = idbairro;
    }

    public int getIdfilial() {
        return idfilial;
    }

    public void setIdfilial(int idfilial) {
        this.idfilial = idfilial;
    }

    public int getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }

    public int getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(int idgrupo) {
        this.idgrupo = idgrupo;
    }

    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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

    public Double getDolar() {
        return dolar;
    }

    public void setDolar(Double dolar) {
        this.dolar = dolar;
    }

    public Double getIpca() {
        return ipca;
    }

    public void setIpca(Double ipca) {
        this.ipca = ipca;
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
        if (!(object instanceof Tabelas)) {
            return false;
        }
        Tabelas other = (Tabelas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tabelas{" + "id=" + id + ", ano=" + ano + ", mes=" + mes + ", dia=" + dia + ", idbairro=" + idbairro + ", idfilial=" + idfilial + ", idlocal=" + idlocal + ", idgrupo=" + idgrupo + ", idproduto=" + idproduto + ", valor=" + valor + ", semana=" + semana + ", semanaano=" + semanaano + ", diaano=" + diaano + ", dolar=" + dolar + ", ipca=" + ipca + '}';
    }

}
