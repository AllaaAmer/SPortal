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
import javax.persistence.OneToOne;
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
@Table(name = "LESSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lesson.findAll", query = "SELECT l FROM Lesson l")
    , @NamedQuery(name = "Lesson.findById", query = "SELECT l FROM Lesson l WHERE l.id = :id")
    , @NamedQuery(name = "Lesson.findByLessonName", query = "SELECT l FROM Lesson l WHERE l.lessonName = :lessonName")
    , @NamedQuery(name = "Lesson.findByTopicId", query = "SELECT l FROM Lesson l WHERE l.topicId = :topicId")
    , @NamedQuery(name = "Lesson.findBySequence", query = "SELECT l FROM Lesson l WHERE l.sequence = :sequence")})
public class Lesson implements Serializable {

    @OneToMany(mappedBy = "lessonId")
    private List<LearnObjTrans> learnObjTransList;

    @Size(max = 10)
    @Column(name = "CREATED_AT")
    private String createdAt;
    @OneToMany(mappedBy = "lessonId")
    private List<LessonDir> lessonDirList;

    @OneToMany(mappedBy = "lessonId")
    private List<TransactionData> transactionDataList;

    @OneToMany(mappedBy = "lessonId")
    private List<LessonFolders> lessonFoldersList;

    @OneToOne(mappedBy = "lessonId")
    private LessonDir lessonDir;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "LESSON_NAME")
    private String lessonName;
    @Column(name = "SEQUENCE")
    private Integer sequence;
    @JoinColumn(name = "TOPIC_ID", referencedColumnName = "ID")
    @ManyToOne
    private Topic topicId;

    public Lesson() {
    }

    public Lesson(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Topic getTopicId() {
        return topicId;
    }

    public void setTopicId(Topic topicId) {
        this.topicId = topicId;
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
        if (!(object instanceof Lesson)) {
            return false;
        }
        Lesson other = (Lesson) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Lesson[ id=" + id + " ]";
    }

    public LessonDir getLessonDir() {
        return lessonDir;
    }

    public void setLessonDir(LessonDir lessonDir) {
        this.lessonDir = lessonDir;
    }

    @XmlTransient
    public List<LessonFolders> getLessonFoldersList() {
        return lessonFoldersList;
    }

    public void setLessonFoldersList(List<LessonFolders> lessonFoldersList) {
        this.lessonFoldersList = lessonFoldersList;
    }

    @XmlTransient
    public List<TransactionData> getTransactionDataList() {
        return transactionDataList;
    }

    public void setTransactionDataList(List<TransactionData> transactionDataList) {
        this.transactionDataList = transactionDataList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @XmlTransient
    public List<LessonDir> getLessonDirList() {
        return lessonDirList;
    }

    public void setLessonDirList(List<LessonDir> lessonDirList) {
        this.lessonDirList = lessonDirList;
    }

    @XmlTransient
    public List<LearnObjTrans> getLearnObjTransList() {
        return learnObjTransList;
    }

    public void setLearnObjTransList(List<LearnObjTrans> learnObjTransList) {
        this.learnObjTransList = learnObjTransList;
    }

}
