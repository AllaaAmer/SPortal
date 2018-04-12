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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "QUESTIONAIRE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questionaire.findAll", query = "SELECT q FROM Questionaire q")
    , @NamedQuery(name = "Questionaire.findById", query = "SELECT q FROM Questionaire q WHERE q.id = :id")
    , @NamedQuery(name = "Questionaire.findByQuestion", query = "SELECT q FROM Questionaire q WHERE q.question = :question")
    , @NamedQuery(name = "Questionaire.findBySequenceInGroup", query = "SELECT q FROM Questionaire q WHERE q.sequenceInGroup = :sequenceInGroup")
    , @NamedQuery(name = "Questionaire.findBySequence", query = "SELECT q FROM Questionaire q WHERE q.sequence = :sequence")
    , @NamedQuery(name = "Questionaire.findByCreatedAt", query = "SELECT q FROM Questionaire q WHERE q.createdAt = :createdAt")
    , @NamedQuery(name = "Questionaire.findByModifiedAt", query = "SELECT q FROM Questionaire q WHERE q.modifiedAt = :modifiedAt")})
public class Questionaire implements Serializable {

    @JoinColumn(name = "DIMENTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Dimention dimentionId;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "QUESTION")
    private String question;
    @Column(name = "SEQUENCE_IN_GROUP")
    private Integer sequenceInGroup;
    @Column(name = "SEQUENCE")
    private Integer sequence;
    @Column(name = "CREATED_AT")
    private String createdAt;
    @Column(name = "MODIFIED_AT")
    private String modifiedAt;
    @OneToMany(mappedBy = "relatedQuesId")
    private List<Questionaire> questionaireList;
    @JoinColumn(name = "RELATED_QUES_ID", referencedColumnName = "ID")
    @ManyToOne
    private Questionaire relatedQuesId;
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "ID")
    @ManyToOne
    private Users createdBy;
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "ID")
    @ManyToOne
    private Users modifiedBy;
    @OneToMany(mappedBy = "questionId")
    private List<QuestionairAnswer> questionairAnswerList;

    public Questionaire() {
    }

    public Questionaire(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getSequenceInGroup() {
        return sequenceInGroup;
    }

    public void setSequenceInGroup(Integer sequenceInGroup) {
        this.sequenceInGroup = sequenceInGroup;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @XmlTransient
    public List<Questionaire> getQuestionaireList() {
        return questionaireList;
    }

    public void setQuestionaireList(List<Questionaire> questionaireList) {
        this.questionaireList = questionaireList;
    }

    public Questionaire getRelatedQuesId() {
        return relatedQuesId;
    }

    public void setRelatedQuesId(Questionaire relatedQuesId) {
        this.relatedQuesId = relatedQuesId;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Users getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Users modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @XmlTransient
    public List<QuestionairAnswer> getQuestionairAnswerList() {
        return questionairAnswerList;
    }

    public void setQuestionairAnswerList(List<QuestionairAnswer> questionairAnswerList) {
        this.questionairAnswerList = questionairAnswerList;
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
        if (!(object instanceof Questionaire)) {
            return false;
        }
        Questionaire other = (Questionaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Questionaire[ id=" + id + " ]";
    }

    public Dimention getDimentionId() {
        return dimentionId;
    }

    public void setDimentionId(Dimention dimentionId) {
        this.dimentionId = dimentionId;
    }

}
