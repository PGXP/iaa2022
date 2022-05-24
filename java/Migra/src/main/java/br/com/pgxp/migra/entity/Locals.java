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
@Table(name = "locals")
@NamedQueries({
    @NamedQuery(name = "Locals.findAll", query = "SELECT l FROM Locals l"),
    @NamedQuery(name = "Locals.findById", query = "SELECT l FROM Locals l WHERE l.id = :id"),
    @NamedQuery(name = "Locals.findByIdbairro", query = "SELECT l FROM Locals l WHERE l.idbairro = :idbairro"),
    @NamedQuery(name = "Locals.findByIdfilial", query = "SELECT l FROM Locals l WHERE l.idfilial = :idfilial"),
    @NamedQuery(name = "Locals.findByIdlocal", query = "SELECT l FROM Locals l WHERE l.idlocal = :idlocal"),
    @NamedQuery(name = "Locals.findByNome", query = "SELECT l FROM Locals l WHERE l.nome = :nome")})
public class Locals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @Column(name = "nome")
    private String nome;

    public Locals() {
    }

    public Locals(Integer id) {
        this.id = id;
    }

    public Locals(Integer id, int idbairro, int idfilial, int idlocal, String nome) {
        this.id = id;
        this.idbairro = idbairro;
        this.idfilial = idfilial;
        this.idlocal = idlocal;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Locals)) {
            return false;
        }
        Locals other = (Locals) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.pgxp.migra.entity.Locals[ id=" + id + " ]";
    }
    
}
