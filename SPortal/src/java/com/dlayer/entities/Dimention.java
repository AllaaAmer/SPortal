/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "DIMENTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dimention.findAll", query = "SELECT d FROM Dimention d")
    , @NamedQuery(name = "Dimention.findById", query = "SELECT d FROM Dimention d WHERE d.id = :id")
    , @NamedQuery(name = "Dimention.findByDimention", query = "SELECT d FROM Dimention d WHERE d.dimention = :dimention")})
public class Dimention implements Serializable {

    @OneToMany(mappedBy = "dimentionId")
    private List<Questionaire> questionaireList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "DIMENTION")
    private String dimention;
    @OneToMany(mappedBy = "dimentionId")
    private List<Criteria> criteriaList;

    public Dimention() {
    }

    public Dimention(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDimention() {
        return dimention;
    }

    public void setDimention(String dimention) {
        this.dimention = dimention;
    }

    @XmlTransient
    public List<Criteria> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
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
        if (!(object instanceof Dimention)) {
            return false;
        }
        Dimention other = (Dimention) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Dimention[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Questionaire> getQuestionaireList() {
        return questionaireList;
    }

    public void setQuestionaireList(List<Questionaire> questionaireList) {
        this.questionaireList = questionaireList;
    }
    
}
