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
@Table(name = "TOPIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Topic.findAll", query = "SELECT t FROM Topic t")
    , @NamedQuery(name = "Topic.findById", query = "SELECT t FROM Topic t WHERE t.id = :id")
    , @NamedQuery(name = "Topic.findByTopicName", query = "SELECT t FROM Topic t WHERE t.topicName = :topicName")
    , @NamedQuery(name = "Topic.findByTopicSequence", query = "SELECT t FROM Topic t WHERE t.topicSequence = :topicSequence")})
public class Topic implements Serializable {

    @OneToMany(mappedBy = "topicId")
    private List<LearnObjTrans> learnObjTransList;

    @Size(max = 10)
    @Column(name = "CREATED_AT")
    private String createdAt;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TOPIC_NAME")
    private String topicName;
    @Column(name = "TOPIC_SEQUENCE")
    private Integer topicSequence;
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Course courseId;
    @OneToMany(mappedBy = "topicId")
    private List<Assignment> assignmentList;
    @OneToMany(mappedBy = "topicId")
    private List<Lesson> lessonList;

    public Topic() {
    }

    public Topic(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getTopicSequence() {
        return topicSequence;
    }

    public void setTopicSequence(Integer topicSequence) {
        this.topicSequence = topicSequence;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    @XmlTransient
    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @XmlTransient
    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
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
        if (!(object instanceof Topic)) {
            return false;
        }
        Topic other = (Topic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Topic[ id=" + id + " ]";
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @XmlTransient
    public List<LearnObjTrans> getLearnObjTransList() {
        return learnObjTransList;
    }

    public void setLearnObjTransList(List<LearnObjTrans> learnObjTransList) {
        this.learnObjTransList = learnObjTransList;
    }

}
