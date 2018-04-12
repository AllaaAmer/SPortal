/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "OWL_RULES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OwlRules.findAll", query = "SELECT o FROM OwlRules o")
    , @NamedQuery(name = "OwlRules.findById", query = "SELECT o FROM OwlRules o WHERE o.id = :id")
    , @NamedQuery(name = "OwlRules.findByCriteria", query = "SELECT o FROM OwlRules o WHERE o.criteriaId.criteria in :criList")
    , @NamedQuery(name = "OwlRules.findByDescription", query = "SELECT o FROM OwlRules o WHERE o.description = :description")
    , @NamedQuery(name = "OwlRules.findByDesCrLo", query = "SELECT o FROM OwlRules o WHERE o.description = :description and o.criteriaId = :criteria and o.learningObjectTypeId = :lo")
})
public class OwlRules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "CRITERIA_ID", referencedColumnName = "ID")
    @ManyToOne
    private Criteria criteriaId;
    @JoinColumn(name = "LEARNING_OBJECT_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningObjectType learningObjectTypeId;
    @Column(name = "VALUE")
    private float value;

    public OwlRules() {
    }

    public OwlRules(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Criteria getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Criteria criteriaId) {
        this.criteriaId = criteriaId;
    }

    public LearningObjectType getLearningObjectTypeId() {
        return learningObjectTypeId;
    }

    public void setLearningObjectTypeId(LearningObjectType learningObjectTypeId) {
        this.learningObjectTypeId = learningObjectTypeId;
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
        if (!(object instanceof OwlRules)) {
            return false;
        }
        OwlRules other = (OwlRules) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.OwlRules[ id=" + id + " ]";
    }

}
