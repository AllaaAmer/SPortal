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
@Table(name = "GROUP_DISCUTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupDiscution.findAll", query = "SELECT g FROM GroupDiscution g")
    , @NamedQuery(name = "GroupDiscution.findById", query = "SELECT g FROM GroupDiscution g WHERE g.id = :id")
    , @NamedQuery(name = "GroupDiscution.findByTitle", query = "SELECT g FROM GroupDiscution g WHERE g.title = :title")})
public class GroupDiscution implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "TITLE")
    private String title;
    @OneToMany(mappedBy = "groupDescutionId")
    private List<Comment> commentList;
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Course courseId;

    public GroupDiscution() {
    }

    public GroupDiscution(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
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
        if (!(object instanceof GroupDiscution)) {
            return false;
        }
        GroupDiscution other = (GroupDiscution) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.GroupDiscution[ id=" + id + " ]";
    }
    
}
