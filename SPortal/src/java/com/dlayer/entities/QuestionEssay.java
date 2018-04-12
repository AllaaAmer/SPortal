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
@Table(name = "QUESTION_ESSAY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionEssay.findAll", query = "SELECT q FROM QuestionEssay q")
    , @NamedQuery(name = "QuestionEssay.findById", query = "SELECT q FROM QuestionEssay q WHERE q.id = :id")
    , @NamedQuery(name = "QuestionEssay.findByAnswer", query = "SELECT q FROM QuestionEssay q WHERE q.answer = :answer")
    , @NamedQuery(name = "QuestionEssay.findByNotes", query = "SELECT q FROM QuestionEssay q WHERE q.notes = :notes")
    , @NamedQuery(name = "QuestionEssay.findByFinishDuration", query = "SELECT q FROM QuestionEssay q WHERE q.finishDuration = :finishDuration")})
public class QuestionEssay implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ANSWER")
    private String answer;
    @Column(name = "NOTES")
    private String notes;
    @Column(name = "FINISH_DURATION")
    private Date finishDuration;
    @JoinColumn(name = "INSTRUCTOR_ID", referencedColumnName = "ID")
    @ManyToOne
    private Instructor instructorId;
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Question questionId;
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Student studentId;

    public QuestionEssay() {
    }

    public QuestionEssay(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getFinishDuration() {
        return finishDuration;
    }

    public void setFinishDuration(Date finishDuration) {
        this.finishDuration = finishDuration;
    }

    public Instructor getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Instructor instructorId) {
        this.instructorId = instructorId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
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
        if (!(object instanceof QuestionEssay)) {
            return false;
        }
        QuestionEssay other = (QuestionEssay) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.QuestionEssay[ id=" + id + " ]";
    }

}
