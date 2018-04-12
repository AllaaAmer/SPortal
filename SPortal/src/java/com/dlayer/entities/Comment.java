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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "COMMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")
    , @NamedQuery(name = "Comment.findById", query = "SELECT c FROM Comment c WHERE c.id = :id")})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "comment1")
    private Comment comment;
    @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Comment comment1;
    @JoinColumn(name = "COMMENT_THREAD_ID", referencedColumnName = "ID")
    @ManyToOne
    private CommentThread commentThreadId;
    @JoinColumn(name = "COMMENT_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private CommentType commentTypeId;
    @JoinColumn(name = "GROUP_DESCUTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private GroupDiscution groupDescutionId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Users userId;
    @OneToMany(mappedBy = "commentId")
    private List<CommentThread> commentThreadList;

    public Comment() {
    }

    public Comment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment1() {
        return comment1;
    }

    public void setComment1(Comment comment1) {
        this.comment1 = comment1;
    }

    public CommentThread getCommentThreadId() {
        return commentThreadId;
    }

    public void setCommentThreadId(CommentThread commentThreadId) {
        this.commentThreadId = commentThreadId;
    }

    public CommentType getCommentTypeId() {
        return commentTypeId;
    }

    public void setCommentTypeId(CommentType commentTypeId) {
        this.commentTypeId = commentTypeId;
    }

    public GroupDiscution getGroupDescutionId() {
        return groupDescutionId;
    }

    public void setGroupDescutionId(GroupDiscution groupDescutionId) {
        this.groupDescutionId = groupDescutionId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @XmlTransient
    public List<CommentThread> getCommentThreadList() {
        return commentThreadList;
    }

    public void setCommentThreadList(List<CommentThread> commentThreadList) {
        this.commentThreadList = commentThreadList;
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
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Comment[ id=" + id + " ]";
    }
    
}
