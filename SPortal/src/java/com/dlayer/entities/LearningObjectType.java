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
@Table(name = "LEARNING_OBJECT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LearningObjectType.findAll", query = "SELECT l FROM LearningObjectType l")
    , @NamedQuery(name = "LearningObjectType.findById", query = "SELECT l FROM LearningObjectType l WHERE l.id = :id")
    , @NamedQuery(name = "LearningObjectType.findByLearningObjectType", query = "SELECT l FROM LearningObjectType l WHERE l.learningObjectType LIKE :learningObjectType")})
public class LearningObjectType implements Serializable {

    @OneToMany(mappedBy = "learningObjTypeId")
    private List<LearnObjTrans> learnObjTransList;

    @OneToMany(mappedBy = "learningObjectTypeId")
    private List<TransactionData> transactionDataList;

    @OneToMany(mappedBy = "learningObjectTypeId")
    private List<LearningStratigyObject> learningStratigyObjectList;

    @OneToMany(mappedBy = "learningObjectTypeId")
    private List<OwlRules> owlRulesList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "LEARNING_OBJECT_TYPE")
    private String learningObjectType;
    @OneToMany(mappedBy = "learningObjTyId")
    private List<LearningObject> learningObjectList;

    public LearningObjectType() {
    }

    public LearningObjectType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLearningObjectType() {
        return learningObjectType;
    }

    public void setLearningObjectType(String learningObjectType) {
        this.learningObjectType = learningObjectType;
    }

    @XmlTransient
    public List<LearningObject> getLearningObjectList() {
        return learningObjectList;
    }

    public void setLearningObjectList(List<LearningObject> learningObjectList) {
        this.learningObjectList = learningObjectList;
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
        if (!(object instanceof LearningObjectType)) {
            return false;
        }
        LearningObjectType other = (LearningObjectType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.LearningObjectType[ id=" + id + " ]";
    }

    @XmlTransient
    public List<OwlRules> getOwlRulesList() {
        return owlRulesList;
    }

    public void setOwlRulesList(List<OwlRules> owlRulesList) {
        this.owlRulesList = owlRulesList;
    }

    @XmlTransient
    public List<LearningStratigyObject> getLearningStratigyObjectList() {
        return learningStratigyObjectList;
    }

    public void setLearningStratigyObjectList(List<LearningStratigyObject> learningStratigyObjectList) {
        this.learningStratigyObjectList = learningStratigyObjectList;
    }

    @XmlTransient
    public List<TransactionData> getTransactionDataList() {
        return transactionDataList;
    }

    public void setTransactionDataList(List<TransactionData> transactionDataList) {
        this.transactionDataList = transactionDataList;
    }

    @XmlTransient
    public List<LearnObjTrans> getLearnObjTransList() {
        return learnObjTransList;
    }

    public void setLearnObjTransList(List<LearnObjTrans> learnObjTransList) {
        this.learnObjTransList = learnObjTransList;
    }

}
