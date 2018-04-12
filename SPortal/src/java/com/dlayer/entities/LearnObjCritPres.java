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
 * @author alaa
 */
@Entity
@Table(name = "LEARN_OBJ_CRIT_PRES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LearnObjCritPres.findAll", query = "SELECT l FROM LearnObjCritPres l")
    , @NamedQuery(name = "LearnObjCritPres.findById", query = "SELECT l FROM LearnObjCritPres l WHERE l.id = :id")
    , @NamedQuery(name = "LearnObjCritPres.findByLearningObj", query = "SELECT l FROM LearnObjCritPres l WHERE l.learningObgId.id = :id")
    , @NamedQuery(name = "LearnObjCritPres.findByPrecentage", query = "SELECT l FROM LearnObjCritPres l WHERE l.precentage = :precentage")})
public class LearnObjCritPres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "PRECENTAGE")
    private Integer precentage;
    @JoinColumn(name = "CRITERIA_ID", referencedColumnName = "ID")
    @ManyToOne
    private Criteria criteriaId;
    @JoinColumn(name = "LEARNING_OBG_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningObject learningObgId;
    @JoinColumn(name = "LESSONID", referencedColumnName = "ID")
    @ManyToOne
    private Lesson lessonid;

    public LearnObjCritPres() {
    }

    public LearnObjCritPres(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrecentage() {
        return precentage;
    }

    public void setPrecentage(Integer precentage) {
        this.precentage = precentage;
    }

    public Criteria getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Criteria criteriaId) {
        this.criteriaId = criteriaId;
    }

    public LearningObject getLearningObgId() {
        return learningObgId;
    }

    public void setLearningObgId(LearningObject learningObgId) {
        this.learningObgId = learningObgId;
    }

    public Lesson getLessonid() {
        return lessonid;
    }

    public void setLessonid(Lesson lessonid) {
        this.lessonid = lessonid;
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
        if (!(object instanceof LearnObjCritPres)) {
            return false;
        }
        LearnObjCritPres other = (LearnObjCritPres) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LearnObjCritPres{" + "id=" + id + ", precentage=" + precentage + ", criteriaId=" + criteriaId + ", learningObgId=" + learningObgId + ", lessonid=" + lessonid + '}';
    }

}
