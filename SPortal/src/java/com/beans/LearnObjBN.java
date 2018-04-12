/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.converter.CourseConv;
import com.converter.FolderConv;
import com.converter.LearnObjTypeCon;
import com.converter.LessonConv;
import com.converter.TopicConv;
import com.dlayer.Dao;
import com.dlayer.entities.Course;
import com.dlayer.entities.Criteria;
import com.dlayer.entities.Instructor;
import com.dlayer.entities.LearnObjCritPres;
import com.dlayer.entities.LearningObject;
import com.dlayer.entities.LearningObjectType;
import com.dlayer.entities.Lesson;
import com.dlayer.entities.LessonFolders;
import com.dlayer.entities.RolePriviledge;
import com.dlayer.entities.Topic;
import com.dlayer.entities.Users;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author yaraYaseen
 */
@ViewScoped
@ManagedBean
public class LearnObjBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    Users user = new Users();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
    boolean editFlag = false;
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    RolePriviledge rulePriv;
    String entityId;
    List<LearningObject> learnObjList = new ArrayList<>();
    LearningObject learnObj = new LearningObject();
    LearnObjTypeCon learnObjTypCon = new LearnObjTypeCon();
    List<LearningObjectType> learnObjTypeList = new ArrayList<>();
    List<Course> coursesList = new ArrayList<>();

    List<Criteria> criteriaList = new ArrayList<>();
    List<LearnObjCritPres> learnObjCriPerList = new ArrayList<>();
    Course selectedCourse = new Course();
    CourseConv corCov = new CourseConv();
    List<Topic> topicList = new ArrayList<>();
    TopicConv toConv = new TopicConv();
    Topic selectedTopic = new Topic();
    List<Lesson> lessonList = new ArrayList<>();
    LessonConv lessonConv = new LessonConv();
    Lesson selectedLesson = new Lesson();
    UploadedFile file;
    List<LessonFolders> folderList = new ArrayList<>();
    FolderConv foldConv = new FolderConv();
    String pathDir;
    int percentageValue;

    //methods------------------------------------------------------------------------------
    @PostConstruct
    public void prerpMethod() {
        try {
            if (userAcc != null) {
                rolePrivList = dao.getPagesByRule(userAcc.getUserRole().getRoleId());
            }

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            entityId = request.getParameter("EId");
            if (entityId != null) {
                rulePriv = dao.getRolePrivlageByUserIdEntityId(userAcc.getUserRole().getRoleId().getId(), entityId);
            }

            learnObjTypeList = dao.getAllLearnObjectType();
            learnObjTypCon.list = learnObjTypeList;

            coursesList = dao.getAllCourses();
            corCov.list = coursesList;

            criteriaList = dao.getAllCriteria();

//            if (reqId != null) {
//                lesson.setId(Integer.valueOf(reqId));
//                lesson = dao.getLessonById(lesson).get(0);
//                editFlag = true;
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fillTopics() {
        Topic topic = new Topic();
        topic.setCourseId(selectedCourse);
        topicList = dao.searchTopic(topic);
        toConv.list = topicList;
    }

    public void fillLesson() {
        Lesson lesson = new Lesson();
        lesson.setTopicId(selectedTopic);
        lessonList = dao.searchLesson(lesson);
        lessonConv.list = lessonList;
    }

    public void fillFolder() {
        folderList = dao.getFoldersByLessonId(selectedLesson);
        foldConv.list = folderList;
    }

    public void processLearnObj() {
        try {
            String path = selectedLesson.getLessonDir().getDirectoryName() + "/" + learnObj.getFolderId().getFolderName();
            File f = new File(path);
//            if (f.list().length > 0) {
//                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "This folder contains a learning object, choose another", null);
//                FacesContext.getCurrentInstance().addMessage(null, msg);
//            } else {
                //upload file in file disk
                String fileName = "";
                fileName = readLoadedFile();
                learnObj.setFileName(fileName);
                learnObj.setDirPath(pathDir);
                learnObj = dao.saveLearnOBj(learnObj);

                // adding the percentages
                for (LearnObjCritPres learnObjCritPres : learnObjCriPerList) {
                    learnObjCritPres.setLearningObgId(learnObj);
                    learnObjCritPres = dao.addLearningObjectPersentage(learnObjCritPres);
                }

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Learn Object processed succefully ", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
//            }
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while processing", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    public void deleteLearnObj(LearningObject learnObj) {
        try {
            File myFile = new File(learnObj.getFileName());
            if (myFile.delete()) {
                dao.deleteLearnObj(learnObj);
                searchLearnObj();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Learn Object deleted successfully", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while deleting", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while deleting", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            e.printStackTrace();
        }
    }

    public void searchLearnObj() {
        learnObjList = dao.searchLearnObj(learnObj, selectedLesson, selectedTopic, selectedCourse);
    }

    public String readLoadedFile() {
        try {
            pathDir = selectedLesson.getLessonDir().getDirectoryName() + "\\" + learnObj.getFolderId().getFolderName();
            Path folder = Paths.get(pathDir);
            String filename = FilenameUtils.getBaseName(file.getFileName());
            String extension = FilenameUtils.getExtension(file.getFileName());
            Path fily = Files.createTempFile(folder, filename + "-", "." + extension);

            try (InputStream input = file.getInputstream()) {
                Files.copy(input, fily, StandardCopyOption.REPLACE_EXISTING);
            }

            return fily.getFileName().toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void sendValues(Criteria c) {
        LearnObjCritPres loCP = new LearnObjCritPres();
        loCP.setCriteriaId(c);
        loCP.setPrecentage(percentageValue);
        loCP.setLessonid(selectedLesson);
        System.out.println("LOCP: " + loCP);
        learnObjCriPerList.add(loCP);
    }

    //setters and getters------------------------------------------------------------------
    public List<LearningObject> getLearnObjList() {
        return learnObjList;
    }

    public void setLearnObjList(List<LearningObject> learnObjList) {
        this.learnObjList = learnObjList;
    }

    public LearningObject getLearnObj() {
        return learnObj;
    }

    public void setLearnObj(LearningObject learnObj) {
        this.learnObj = learnObj;
    }

    public LearnObjTypeCon getLearnObjTypCon() {
        return learnObjTypCon;
    }

    public void setLearnObjTypCon(LearnObjTypeCon learnObjTypCon) {
        this.learnObjTypCon = learnObjTypCon;
    }

    public List<LearningObjectType> getLearnObjTypeList() {
        return learnObjTypeList;
    }

    public void setLearnObjTypeList(List<LearningObjectType> learnObjTypeList) {
        this.learnObjTypeList = learnObjTypeList;
    }

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public CourseConv getCorCov() {
        return corCov;
    }

    public void setCorCov(CourseConv corCov) {
        this.corCov = corCov;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public TopicConv getToConv() {
        return toConv;
    }

    public void setToConv(TopicConv toConv) {
        this.toConv = toConv;
    }

    public Topic getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(Topic selectedTopic) {
        this.selectedTopic = selectedTopic;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    public LessonConv getLessonConv() {
        return lessonConv;
    }

    public void setLessonConv(LessonConv lessonConv) {
        this.lessonConv = lessonConv;
    }

    public Lesson getSelectedLesson() {
        return selectedLesson;
    }

    public void setSelectedLesson(Lesson selectedLesson) {
        this.selectedLesson = selectedLesson;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public RolePriviledge getRulePriv() {
        return rulePriv;
    }

    public void setRulePriv(RolePriviledge rulePriv) {
        this.rulePriv = rulePriv;
    }

    public List<LessonFolders> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<LessonFolders> folderList) {
        this.folderList = folderList;
    }

    public FolderConv getFoldConv() {
        return foldConv;
    }

    public void setFoldConv(FolderConv foldConv) {
        this.foldConv = foldConv;
    }

    public List<Criteria> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public int getPercentageValue() {
        return percentageValue;
    }

    public void setPercentageValue(int percentageValue) {
        this.percentageValue = percentageValue;
    }

}
