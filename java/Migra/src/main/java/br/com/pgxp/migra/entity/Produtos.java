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
@Table(name = "produtos")
@NamedQueries({
    @NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p"),
    @NamedQuery(name = "Produtos.findById", query = "SELECT p FROM Produtos p WHERE p.id = :id"),
    @NamedQuery(name = "Produtos.findByIdgrupo", query = "SELECT p FROM Produtos p WHERE p.idgrupo = :idgrupo"),
    @NamedQuery(name = "Produtos.findByIdproduto", query = "SELECT p FROM Produtos p WHERE p.idproduto = :idproduto"),
    @NamedQuery(name = "Produtos.findByCodigo", query = "SELECT p FROM Produtos p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Produtos.findByEmbalagem", query = "SELECT p FROM Produtos p WHERE p.embalagem = :embalagem"),
    @NamedQuery(name = "Produtos.findByMedida", query = "SELECT p FROM Produtos p WHERE p.medida = :medida"),
    @NamedQuery(name = "Produtos.findByNome", query = "SELECT p FROM Produtos p WHERE p.nome = :nome")})
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "idgrupo")
    private int idgrupo;
    @Basic(optional = false)
    @Column(name = "idproduto")
    private int idproduto;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "embalagem")
    private String embalagem;
    @Basic(optional = false)
    @Column(name = "medida")
    private String medida;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;

    public Produtos() {
    }

    public Produtos(Integer id) {
        this.id = id;
    }

    public Produtos(Integer id, int idgrupo, int idproduto, String codigo, String embalagem, String medida, String nome) {
        this.id = id;
        this.idgrupo = idgrupo;
        this.idproduto = idproduto;
        this.codigo = codigo;
        this.embalagem = embalagem;
        this.medida = medida;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(object instanceof Produtos)) {
            return false;
        }
        Produtos other = (Produtos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.pgxp.migra.entity.Produtos[ id=" + id + " ]";
    }
    
}
