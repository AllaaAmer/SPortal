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
@Table(name = "LEARNING_OBJECT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LearningObject.findAll", query = "SELECT l FROM LearningObject l")
    , @NamedQuery(name = "LearningObject.findById", query = "SELECT l FROM LearningObject l WHERE l.id = :id")
    , @NamedQuery(name = "LearningObject.findByFileName", query = "SELECT l FROM LearningObject l WHERE l.fileName = :fileName")
    , @NamedQuery(name = "LearningObject.findByFileDesc", query = "SELECT l FROM LearningObject l WHERE l.fileDesc = :fileDesc")})
public class LearningObject implements Serializable {

    @OneToMany(mappedBy = "learningObjId")
    private List<LearnObjTrans> learnObjTransList;

    @Size(max = 10)
    @Column(name = "CREATED_AT")
    private String createdAt;

    @JoinColumn(name = "FOLDER_ID", referencedColumnName = "ID")
    @OneToOne
    private LessonFolders folderId;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;  
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_DESC")
    private String fileDesc;
    @JoinColumn(name = "LEARNING_OBJ_TY_ID", referencedColumnName = "ID")
    @ManyToOne
    private LearningObjectType learningObjTyId;
    @Column(name = "DIR_PATH")
    private String dirPath;
    @Column(name = "TEXT")
    private String text;

    public LearningObject() {
    }

    public LearningObject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public LearningObjectType getLearningObjTyId() {
        return learningObjTyId;
    }

    public void setLearningObjTyId(LearningObjectType learningObjTyId) {
        this.learningObjTyId = learningObjTyId;
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
        if (!(object instanceof LearningObject)) {
            return false;
        }
        LearningObject other = (LearningObject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.LearningObject[ id=" + id + " ]";
    }

    public LessonFolders getFolderId() {
        return folderId;
    }

    public void setFolderId(LessonFolders folderId) {
        this.folderId = folderId;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @XmlTransient
    public List<LearnObjTrans> getLearnObjTransList() {
        return learnObjTransList;
    }

    public void setLearnObjTransList(List<LearnObjTrans> learnObjTransList) {
        this.learnObjTransList = learnObjTransList;
    }
}
