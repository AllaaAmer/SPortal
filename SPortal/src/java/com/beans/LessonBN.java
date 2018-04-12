/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.converter.CourseConv;
import com.converter.TopicConv;
import com.dlayer.Dao;
import com.dlayer.entities.Course;
import com.dlayer.entities.LearningObjectType;
import com.dlayer.entities.LearningStratigyObject;
import com.dlayer.entities.LearningStratigyStyle;
import com.dlayer.entities.Lesson;
import com.dlayer.entities.LessonDir;
import com.dlayer.entities.LessonFolders;
import com.dlayer.entities.RolePriviledge;
import com.dlayer.entities.Topic;
import com.dlayer.entities.Users;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yaraYaseen
 */
@ViewScoped
@ManagedBean
public class LessonBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    List<Lesson> lessonList = new ArrayList<>();
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    RolePriviledge rulePriv;
    String entityId;
    String instName;
    List<Topic> topicList = new ArrayList<>();
    TopicConv toConv = new TopicConv();
    Lesson lesson = new Lesson();
    Users user = new Users();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
    boolean editFlag = false;
    Course selectedCourse = new Course();
    CourseConv corCov = new CourseConv();
    List<Course> courseList = new ArrayList<>();
    String folderName;
    List<LessonFolders> lessonDirList = new ArrayList<>();

    //methods---------------------------------------------------------------------------------------------
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

            courseList = dao.getAllCourses();
            corCov.list = courseList;

            if (reqId != null) {
                lesson.setId(Integer.valueOf(reqId));
                lesson = dao.getLessonById(lesson).get(0);
                editFlag = true;
                lessonDirList = lesson.getLessonFoldersList();
                selectedCourse = lesson.getTopicId().getCourseId();
                topicList = dao.getTopicById(lesson.getTopicId());
                toConv.list = topicList;
            }
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

    public void processLesson() {
        try {
            lesson.setLessonFoldersList(lessonDirList);
            Lesson les = dao.saveLesson(lesson, editFlag);
            File theDir = new File(dao.getSettingByDesc("LEARN_OBJ_FILE").getValue() + lesson.getTopicId().getCourseId().getCourseCode() + "_"
                    + lesson.getTopicId().getCourseId().getFullName() + "\\" + lesson.getTopicId().getTopicName() + "\\" + lesson.getLessonName());
            LessonDir lessonDir = new LessonDir();
            lessonDir.setDirectoryName(theDir.getAbsolutePath());
            lessonDir.setLessonId(les);
            dao.saveLessonDir(lessonDir);
            if (!theDir.exists()) {
                theDir.mkdir();
            }

            for (LessonFolders lfs : lesson.getLessonFoldersList()) {
                File theDirdir = new File(dao.getSettingByDesc("LEARN_OBJ_FILE").getValue() + lesson.getTopicId().getCourseId().getCourseCode() + "_"
                        + lesson.getTopicId().getCourseId().getFullName() + "\\" + lesson.getTopicId().getTopicName() + "\\" + lesson.getLessonName()
                        + "\\" + lfs.getFolderName());
                if (!theDirdir.exists()) {
                    theDirdir.mkdir();
                }
            }

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lessosn processed succefully ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while processing", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    public void deleteLesson(Lesson lesson) {
        try {
            File myFile = new File(dao.getSettingByDesc("LEARN_OBJ_FILE").getValue() + lesson.getTopicId().getCourseId().getCourseCode() + "_"
                    + lesson.getTopicId().getCourseId().getFullName() + "\\" + lesson.getTopicId().getTopicName() + "\\" + lesson.getLessonName());
            if (myFile.delete()) {
                dao.deleteLesson(lesson);
                searchLesson();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lesson deleted successfully", null);
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

    public void searchLesson() {
        lessonList = dao.searchLesson(lesson);
    }

    public void addDir() {
        LessonFolders lf = new LessonFolders();
        lf.setFolderName(folderName);
        lessonDirList.add(lf);
    }

    public void removeDir(LessonFolders dirObj) {
        try {
            for (LessonFolders ob : lessonDirList) {
                if (ob.getFolderName().equals(dirObj.getFolderName())) {
                    lessonDirList.remove(ob);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //setters and getters----------------------------------------------------------------------------------
    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Users getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(Users userAcc) {
        this.userAcc = userAcc;
    }

    public List<RolePriviledge> getRolePrivList() {
        return rolePrivList;
    }

    public void setRolePrivList(List<RolePriviledge> rolePrivList) {
        this.rolePrivList = rolePrivList;
    }

    public RolePriviledge getRulePriv() {
        return rulePriv;
    }

    public void setRulePriv(RolePriviledge rulePriv) {
        this.rulePriv = rulePriv;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
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

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
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

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<LessonFolders> getLessonDirList() {
        return lessonDirList;
    }

    public void setLessonDirList(List<LessonFolders> lessonDirList) {
        this.lessonDirList = lessonDirList;
    }

}
