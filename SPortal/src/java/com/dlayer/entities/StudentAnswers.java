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
@Table(name = "STUDENT_ANSWERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentAnswers.findAll", query = "SELECT s FROM StudentAnswers s")
    , @NamedQuery(name = "StudentAnswers.findById", query = "SELECT s FROM StudentAnswers s WHERE s.id = :id")
    , @NamedQuery(name = "StudentAnswers.findByCreatedAt", query = "SELECT s FROM StudentAnswers s WHERE s.createdAt = :createdAt")})
public class StudentAnswers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 10)
    @Column(name = "CREATED_AT")
    private String createdAt;
    @JoinColumn(name = "QUESTION_CHOICE_ID", referencedColumnName = "ID")
    @ManyToOne
    private QuestionChoice questionChoiceId;
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Student studentId;

    public StudentAnswers() {
    }

    public StudentAnswers(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public QuestionChoice getQuestionChoiceId() {
        return questionChoiceId;
    }

    public void setQuestionChoiceId(QuestionChoice questionChoiceId) {
        this.questionChoiceId = questionChoiceId;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
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
        if (!(object instanceof StudentAnswers)) {
            return false;
        }
        StudentAnswers other = (StudentAnswers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.StudentAnswers[ id=" + id + " ]";
    }
    
}
