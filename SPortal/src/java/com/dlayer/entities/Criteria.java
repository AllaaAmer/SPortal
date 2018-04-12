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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "CRITERIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Criteria.findAll", query = "SELECT c FROM Criteria c")
    , @NamedQuery(name = "Criteria.findById", query = "SELECT c FROM Criteria c WHERE c.id = :id")
    , @NamedQuery(name = "Criteria.findByCriteria", query = "SELECT c FROM Criteria c WHERE c.criteria LIKE :criteria")})
public class Criteria implements Serializable {

    @OneToMany(mappedBy = "criteriaId")
    private List<LearnObjCritPres> learnObjCritPresList;

    @OneToMany(mappedBy = "criteriaId")
    private List<OwlRules> owlRulesList;

    @JoinColumn(name = "DIMENTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Dimention dimentionId;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CRITERIA")
    private String criteria;
    @OneToMany(mappedBy = "criteriaID")
    private List<QuestionairAnswer> QuesAnsList;

    public Criteria() {
    }

    public List<QuestionairAnswer> getQuesAnsList() {
        return QuesAnsList;
    }

    public void setQuesAnsList(List<QuestionairAnswer> QuesAnsList) {
        this.QuesAnsList = QuesAnsList;
    }

    public Criteria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
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
        if (!(object instanceof Criteria)) {
            return false;
        }
        Criteria other = (Criteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Criteria[ id=" + id + " ]";
    }

    public Dimention getDimentionId() {
        return dimentionId;
    }

    public void setDimentionId(Dimention dimentionId) {
        this.dimentionId = dimentionId;
    }

    @XmlTransient
    public List<OwlRules> getOwlRulesList() {
        return owlRulesList;
    }

    public void setOwlRulesList(List<OwlRules> owlRulesList) {
        this.owlRulesList = owlRulesList;
    }

    @XmlTransient
    public List<LearnObjCritPres> getLearnObjCritPresList() {
        return learnObjCritPresList;
    }

    public void setLearnObjCritPresList(List<LearnObjCritPres> learnObjCritPresList) {
        this.learnObjCritPresList = learnObjCritPresList;
    }

}
