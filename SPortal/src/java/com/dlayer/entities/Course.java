/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "COURSE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c")
    , @NamedQuery(name = "Course.findById", query = "SELECT c FROM Course c WHERE c.id = :id")
    , @NamedQuery(name = "Course.findByCourseCode", query = "SELECT c FROM Course c WHERE c.courseCode = :courseCode")
    , @NamedQuery(name = "Course.findByFullName", query = "SELECT c FROM Course c WHERE c.fullName = :fullName")
    , @NamedQuery(name = "Course.findByShortName", query = "SELECT c FROM Course c WHERE c.shortName = :shortName")
    , @NamedQuery(name = "Course.findByCreditHours", query = "SELECT c FROM Course c WHERE c.creditHours = :creditHours")
    , @NamedQuery(name = "Course.findByDescription", query = "SELECT c FROM Course c WHERE c.description = :description")})
public class Course implements Serializable {

    @Size(max = 10)
    @Column(name = "CREATED_AT")
    private String createdAt;
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "ID")
    @ManyToOne
    private Instructor createdBy;
    @OneToMany(mappedBy = "courseId")
    private List<Assessment> assessmentList;

    @OneToMany(mappedBy = "courseId")
    private List<LearningStratigy> learningStratigyList;

    @OneToMany(mappedBy = "courseId")
    private List<Topic> topicList;
    @JoinTable(name = "COURSE_INSTRUCT", joinColumns = {
        @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "INSTRSUCTOR_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<Instructor> instructorList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "COURSE_CODE")
    private String courseCode;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "SHORT_NAME")
    private String shortName;
    @Column(name = "CREDIT_HOURS")
    private Integer creditHours;
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "SEMESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Semester semesterId;

    public Course() {
    }

    public Course(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(Integer creditHours) {
        this.creditHours = creditHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Semester getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Semester semesterId) {
        this.semesterId = semesterId;
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
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Course[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Instructor> getInstructorList() {
        return instructorList;
    }

    public void setInstructorList(List<Instructor> instructorList) {
        this.instructorList = instructorList;
    }

    @XmlTransient
    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    @XmlTransient
    public List<LearningStratigy> getLearningStratigyList() {
        return learningStratigyList;
    }

    public void setLearningStratigyList(List<LearningStratigy> learningStratigyList) {
        this.learningStratigyList = learningStratigyList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Instructor getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Instructor createdBy) {
        this.createdBy = createdBy;
    }

    @XmlTransient
    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

}
