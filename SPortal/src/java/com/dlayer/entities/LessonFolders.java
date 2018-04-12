/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "LESSON_FOLDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonFolders.findAll", query = "SELECT l FROM LessonFolders l")
    , @NamedQuery(name = "LessonFolders.findById", query = "SELECT l FROM LessonFolders l WHERE l.id = :id")
    , @NamedQuery(name = "LessonFolders.findByLessonId", query = "SELECT l FROM LessonFolders l WHERE l.lessonId.id = :lesson")
    , @NamedQuery(name = "LessonFolders.findByLessonIFol", query = "SELECT l FROM LessonFolders l WHERE l.lessonId.id = :lesson and l.folderName = :folderName")
    , @NamedQuery(name = "LessonFolders.findByFolderName", query = "SELECT l FROM LessonFolders l WHERE l.folderName = :folderName")})
public class LessonFolders implements Serializable {

    @OneToMany(mappedBy = "folderId")
    private Collection<LearningObject> learningObjectCollection;

    @OneToOne(mappedBy = "folderId")
    private LearningObject learningObjectList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FOLDER_NAME")
    private String folderName;
    @JoinColumn(name = "LESSON_ID", referencedColumnName = "ID")
    @ManyToOne
    private Lesson lessonId;

    public LessonFolders() {
    }

    public LessonFolders(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Lesson getLessonId() {
        return lessonId;
    }

    public void setLessonId(Lesson lessonId) {
        this.lessonId = lessonId;
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
        if (!(object instanceof LessonFolders)) {
            return false;
        }
        LessonFolders other = (LessonFolders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.LessonFolders[ id=" + id + " ]";
    }

    public LearningObject getLearningObjectList() {
        return learningObjectList;
    }

    public void setLearningObjectList(LearningObject learningObjectList) {
        this.learningObjectList = learningObjectList;
    }

    @XmlTransient
    public Collection<LearningObject> getLearningObjectCollection() {
        return learningObjectCollection;
    }

    public void setLearningObjectCollection(Collection<LearningObject> learningObjectCollection) {
        this.learningObjectCollection = learningObjectCollection;
    }

}
