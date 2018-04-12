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
@Table(name = "LEARNING_STRATIGY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LearningStratigy.findAll", query = "SELECT l FROM LearningStratigy l")
    , @NamedQuery(name = "LearningStratigy.findById", query = "SELECT l FROM LearningStratigy l WHERE l.id = :id")
    , @NamedQuery(name = "LearningStratigy.findByStratigicName", query = "SELECT l FROM LearningStratigy l WHERE l.stratigicName = :stratigicName")})
public class LearningStratigy implements Serializable {

    @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Course courseId;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "STRATIGIC_NAME")
    private String stratigicName;
    @OneToMany(mappedBy = "learningStratigyId")
    private List<LearningStratigyStyle> learningStratigyStyleList;

    public LearningStratigy() {
    }

    public LearningStratigy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStratigicName() {
        return stratigicName;
    }

    public void setStratigicName(String stratigicName) {
        this.stratigicName = stratigicName;
    }

    @XmlTransient
    public List<LearningStratigyStyle> getLearningStratigyStyleList() {
        return learningStratigyStyleList;
    }

    public void setLearningStratigyStyleList(List<LearningStratigyStyle> learningStratigyStyleList) {
        this.learningStratigyStyleList = learningStratigyStyleList;
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
        if (!(object instanceof LearningStratigy)) {
            return false;
        }
        LearningStratigy other = (LearningStratigy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.LearningStratigy[ id=" + id + " ]";
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

}
