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
@Table(name = "COURSE_INSTRUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseInstruct.findAll", query = "SELECT c FROM CourseInstruct c")
    , @NamedQuery(name = "CourseInstruct.findById", query = "SELECT c FROM CourseInstruct c WHERE c.id = :id")})
public class CourseInstruct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Course courseId;
    @JoinColumn(name = "INSTRSUCTOR_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Instructor instrsuctorId;

    public CourseInstruct() {
    }

    public CourseInstruct(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    public Instructor getInstrsuctorId() {
        return instrsuctorId;
    }

    public void setInstrsuctorId(Instructor instrsuctorId) {
        this.instrsuctorId = instrsuctorId;
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
        if (!(object instanceof CourseInstruct)) {
            return false;
        }
        CourseInstruct other = (CourseInstruct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.CourseInstruct[ id=" + id + " ]";
    }
    
}
