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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "LEARNING_STRATIGY_OBJECT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LearningStratigyObject.findAll", query = "SELECT l FROM LearningStratigyObject l")
    , @NamedQuery(name = "LearningStratigyObject.findById", query = "SELECT l FROM LearningStratigyObject l WHERE l.id = :id")})
public class LearningStratigyObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "LEARNING_OBJECT_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningObjectType learningObjectTypeId;
    @JoinColumn(name = "STRATIGIC_STYLE_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningStratigyStyle stratigicStyleId;

    public LearningStratigyObject() {
    }

    public LearningStratigyObject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LearningObjectType getLearningObjectTypeId() {
        return learningObjectTypeId;
    }

    public void setLearningObjectTypeId(LearningObjectType learningObjectTypeId) {
        this.learningObjectTypeId = learningObjectTypeId;
    }

    public LearningStratigyStyle getStratigicStyleId() {
        return stratigicStyleId;
    }

    public void setStratigicStyleId(LearningStratigyStyle stratigicStyleId) {
        this.stratigicStyleId = stratigicStyleId;
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
        if (!(object instanceof LearningStratigyObject)) {
            return false;
        }
        LearningStratigyObject other = (LearningStratigyObject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.LearningStratigyObject[ id=" + id + " ]";
    }

}
