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
 * @author yaraYaseen
 */
@Entity
@Table(name = "QUESTIONAIR_ANSWER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionairAnswer.findAll", query = "SELECT q FROM QuestionairAnswer q")
    , @NamedQuery(name = "QuestionairAnswer.findById", query = "SELECT q FROM QuestionairAnswer q WHERE q.id = :id")
    , @NamedQuery(name = "QuestionairAnswer.findByChoice", query = "SELECT q FROM QuestionairAnswer q WHERE q.choice = :choice")
})
public class QuestionairAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CHOICE")
    private String choice;
    @JoinColumn(name = "CRITERIA_ID", referencedColumnName = "ID")
    @ManyToOne
    private Criteria criteriaID;
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Questionaire questionId;

    public QuestionairAnswer() {
    }

    public QuestionairAnswer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Criteria getCriteriaID() {
        return criteriaID;
    }

    public void setCriteriaID(Criteria criteriaID) {
        this.criteriaID = criteriaID;
    }

    public Questionaire getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Questionaire questionId) {
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
        if (!(object instanceof QuestionairAnswer)) {
            return false;
        }
        QuestionairAnswer other = (QuestionairAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.QuestionairAnswer[ id=" + id + " ]";
    }

}
