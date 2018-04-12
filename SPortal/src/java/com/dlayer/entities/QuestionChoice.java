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
@Table(name = "QUESTION_CHOICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionChoice.findAll", query = "SELECT q FROM QuestionChoice q")
    , @NamedQuery(name = "QuestionChoice.findById", query = "SELECT q FROM QuestionChoice q WHERE q.id = :id")
    , @NamedQuery(name = "QuestionChoice.findByQuestionChoices", query = "SELECT q FROM QuestionChoice q WHERE q.questionChoices = :questionChoices")
    , @NamedQuery(name = "QuestionChoice.findByAnswer", query = "SELECT q FROM QuestionChoice q WHERE q.answer = :answer")})
public class QuestionChoice implements Serializable {

    @OneToMany(mappedBy = "questionChoiceId")
    private List<StudentAnswers> studentAnswersList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "QUESTION_CHOICES")
    private String questionChoices;
    @Size(max = 100)
    @Column(name = "ANSWER")
    private String answer;
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Question questionId;

    public QuestionChoice() {
    }

    public QuestionChoice(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionChoices() {
        return questionChoices;
    }

    public void setQuestionChoices(String questionChoices) {
        this.questionChoices = questionChoices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
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
        if (!(object instanceof QuestionChoice)) {
            return false;
        }
        QuestionChoice other = (QuestionChoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.QuestionChoice[ id=" + id + " ]";
    }

    @XmlTransient
    public List<StudentAnswers> getStudentAnswersList() {
        return studentAnswersList;
    }

    public void setStudentAnswersList(List<StudentAnswers> studentAnswersList) {
        this.studentAnswersList = studentAnswersList;
    }
    
}
