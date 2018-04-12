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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "GRADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grades.findAll", query = "SELECT g FROM Grades g")
    , @NamedQuery(name = "Grades.findById", query = "SELECT g FROM Grades g WHERE g.id = :id")
    , @NamedQuery(name = "Grades.findByGrades", query = "SELECT g FROM Grades g WHERE g.grades = :grades")
    , @NamedQuery(name = "Grades.findBySuccessRate", query = "SELECT g FROM Grades g WHERE g.successRate = :successRate")})
public class Grades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "GRADES")
    private Integer grades;
    @Column(name = "SUCCESS_RATE")
    private Integer successRate;
    @JoinColumn(name = "ASSESSMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Assessment assessmentId;
    @JoinColumn(name = "ASSIGNMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Assignment assignmentId;
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Student studentId;

    public Grades() {
    }

    public Grades(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrades() {
        return grades;
    }

    public void setGrades(Integer grades) {
        this.grades = grades;
    }

    public Integer getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Integer successRate) {
        this.successRate = successRate;
    }

    public Assessment getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Assessment assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Assignment getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Assignment assignmentId) {
        this.assignmentId = assignmentId;
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
        if (!(object instanceof Grades)) {
            return false;
        }
        Grades other = (Grades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Grades[ id=" + id + " ]";
    }
    
}
