/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "STUDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
    , @NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id")
    , @NamedQuery(name = "Student.findByUserID", query = "SELECT s FROM Student s WHERE s.userId.id = :userid")
    , @NamedQuery(name = "Student.findBySemester", query = "SELECT s FROM Student s WHERE s.semester = :semester")})
public class Student implements Serializable {

    @Column(name = "QUESTIONAIRE_FLAG")
    private boolean questionaireFlag;
    @Column(name = "CREATED_AT")
    private Date createdAt;
    @OneToMany(mappedBy = "studentId")
    private List<StudentAnswers> studentAnswersList;

    @JoinColumn(name = "SEMESTER", referencedColumnName = "ID")
    @ManyToOne
    private Semester semester;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @OneToMany(mappedBy = "studentId")
    private List<QuestionEssay> questionEssayList;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @OneToOne
    private Users userId;
    @OneToMany(mappedBy = "studentId")
    private List<Grades> gradesList;

    public Student() {
    }

    public boolean isQuestionaireFlag() {
        return questionaireFlag;
    }

    public void setQuestionaireFlag(boolean questionaireFlag) {
        this.questionaireFlag = questionaireFlag;
    }

    public Student(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<QuestionEssay> getQuestionEssayList() {
        return questionEssayList;
    }

    public void setQuestionEssayList(List<QuestionEssay> questionEssayList) {
        this.questionEssayList = questionEssayList;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @XmlTransient
    public List<Grades> getGradesList() {
        return gradesList;
    }

    public void setGradesList(List<Grades> gradesList) {
        this.gradesList = gradesList;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Student[ id=" + id + " ]";
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @XmlTransient
    public List<StudentAnswers> getStudentAnswersList() {
        return studentAnswersList;
    }

    public void setStudentAnswersList(List<StudentAnswers> studentAnswersList) {
        this.studentAnswersList = studentAnswersList;
    }

}
