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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "LEARNING_STRATIGY_STYLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LearningStratigyStyle.findAll", query = "SELECT l FROM LearningStratigyStyle l")
    , @NamedQuery(name = "LearningStratigyStyle.findById", query = "SELECT l FROM LearningStratigyStyle l WHERE l.id = :id")
    , @NamedQuery(name = "LearningStratigyStyle.findByStratigicStyleName", query = "SELECT l FROM LearningStratigyStyle l WHERE l.stratigicStyleName = :stratigicStyleName")})
public class LearningStratigyStyle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "STRATIGIC_STYLE_NAME")
    private String stratigicStyleName;
    @OneToMany(mappedBy = "stratigicStyleId")
    private List<LearningStratigyObject> learningStratigyObjectList;
    @JoinColumn(name = "LEARNING_STRATIGY_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningStratigy learningStratigyId;
    @Column(name = "SEQUENCE")
    private Integer Sequence;

    public LearningStratigyStyle() {
    }

    public LearningStratigyStyle(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStratigicStyleName() {
        return stratigicStyleName;
    }

    public void setStratigicStyleName(String stratigicStyleName) {
        this.stratigicStyleName = stratigicStyleName;
    }

    @XmlTransient
    public List<LearningStratigyObject> getLearningStratigyObjectList() {
        return learningStratigyObjectList;
    }

    public void setLearningStratigyObjectList(List<LearningStratigyObject> learningStratigyObjectList) {
        this.learningStratigyObjectList = learningStratigyObjectList;
    }

    public LearningStratigy getLearningStratigyId() {
        return learningStratigyId;
    }

    public void setLearningStratigyId(LearningStratigy learningStratigyId) {
        this.learningStratigyId = learningStratigyId;
    }

    public Integer getSequence() {
        return Sequence;
    }

    public void setSequence(Integer Sequence) {
        this.Sequence = Sequence;
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
        if (!(object instanceof LearningStratigyStyle)) {
            return false;
        }
        LearningStratigyStyle other = (LearningStratigyStyle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.LearningStratigyStyle[ id=" + id + " ]";
    }

}
