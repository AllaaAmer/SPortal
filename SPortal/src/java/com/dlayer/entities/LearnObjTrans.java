/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "LEARN_OBJ_TRANS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LearnObjTrans.findAll", query = "SELECT l FROM LearnObjTrans l")
    , @NamedQuery(name = "LearnObjTrans.findById", query = "SELECT l FROM LearnObjTrans l WHERE l.id = :id")
    , @NamedQuery(name = "LearnObjTrans.findByCreatedAt", query = "SELECT l FROM LearnObjTrans l WHERE l.createdAt = :createdAt")
    , @NamedQuery(name = "LearnObjTrans.findByUser", query = "SELECT l FROM LearnObjTrans l WHERE l.topicId.id = :topicId and l.userId.id = :userId and l.lessonSequence = (select max(lo.lessonSequence) from LearnObjTrans lo where lo.topicId.id = :ltopicId and lo.userId.id = :luserId)")
    , @NamedQuery(name = "LearnObjTrans.findByLesson", query = "SELECT l FROM LearnObjTrans l WHERE l.topicId.id = :topicId and l.userId.id = :userId and l.lessonId.id = :lessonId")
    , @NamedQuery(name = "LearnObjTrans.findByPercentage", query = "SELECT l FROM LearnObjTrans l WHERE l.percentage = :percentage")})
public class LearnObjTrans implements Serializable {

    @Column(name = "CREATED_AT")
    private Date createdAt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PERCENTAGE")
    private float percentage;
    @Column(name = "LESSON_SEQUENCE")
    private Integer lessonSequence;
    @JoinColumn(name = "TOPIC_ID", referencedColumnName = "ID")
    @ManyToOne
    private Topic topicId;
    @JoinColumn(name = "LEARNING_OBJ_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningObjectType learningObjTypeId;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "LEARNING_OBJ_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningObject learningObjId;
    @JoinColumn(name = "LESSON_ID", referencedColumnName = "ID")
    @ManyToOne
    private Lesson lessonId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Users userId;

    public LearnObjTrans() {
    }

    public LearnObjTrans(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public LearningObject getLearningObjId() {
        return learningObjId;
    }

    public void setLearningObjId(LearningObject learningObjId) {
        this.learningObjId = learningObjId;
    }

    public Lesson getLessonId() {
        return lessonId;
    }

    public void setLessonId(Lesson lessonId) {
        this.lessonId = lessonId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof LearnObjTrans)) {
            return false;
        }
        LearnObjTrans other = (LearnObjTrans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.LearnObjTrans[ id=" + id + " ]";
    }

    public LearningObjectType getLearningObjTypeId() {
        return learningObjTypeId;
    }

    public void setLearningObjTypeId(LearningObjectType learningObjTypeId) {
        this.learningObjTypeId = learningObjTypeId;
    }

    public Integer getLessonSequence() {
        return lessonSequence;
    }

    public void setLessonSequence(Integer lessonSequence) {
        this.lessonSequence = lessonSequence;
    }

    public Topic getTopicId() {
        return topicId;
    }

    public void setTopicId(Topic topicId) {
        this.topicId = topicId;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

}
