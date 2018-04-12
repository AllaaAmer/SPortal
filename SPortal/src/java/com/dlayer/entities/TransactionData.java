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
@Table(name = "TRANSACTION_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionData.findAll", query = "SELECT t FROM TransactionData t")
    , @NamedQuery(name = "TransactionData.findById", query = "SELECT t FROM TransactionData t WHERE t.id = :id")
    , @NamedQuery(name = "TransactionData.findByTimeSpent", query = "SELECT t FROM TransactionData t WHERE t.timeSpent = :timeSpent")
    , @NamedQuery(name = "TransactionData.findByUserID", query = "SELECT t FROM TransactionData t WHERE t.userId.id = :userID")
    , @NamedQuery(name = "TransactionData.findByCreatedAt", query = "SELECT t FROM TransactionData t WHERE t.createdAt = :createdAt")})
public class TransactionData implements Serializable {

    @Column(name = "TIME_SPENT")
    private long timeSpent;
    @Column(name = "CREATED_AT")
    private Date createdAt;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "LEARNING_OBJECT_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningObjectType learningObjectTypeId;
    @JoinColumn(name = "LESSON_ID", referencedColumnName = "ID")
    @ManyToOne
    private Lesson lessonId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Users userId;

    public TransactionData() {
    }

    public TransactionData(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public LearningObjectType getLearningObjectTypeId() {
        return learningObjectTypeId;
    }

    public void setLearningObjectTypeId(LearningObjectType learningObjectTypeId) {
        this.learningObjectTypeId = learningObjectTypeId;
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
        if (!(object instanceof TransactionData)) {
            return false;
        }
        TransactionData other = (TransactionData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.TransactionData[ id=" + id + " ]";
    }

}
