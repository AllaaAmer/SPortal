/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.converter.CourseConv;
import com.dlayer.Dao;
import com.dlayer.entities.Course;
import com.dlayer.entities.Instructor;
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
public class TopicBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    List<Topic> topicList = new ArrayList<>();
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    RolePriviledge rulePriv;
    String entityId;
    String instName;
    List<Course> coursesList = new ArrayList<>();
    CourseConv coConv = new CourseConv();
    Topic topic = new Topic();
    Users user = new Users();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
    boolean editFlag = false;

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

            //get all instructor
            coursesList = dao.getAllCourses();
            coConv.list = coursesList;

            if (reqId != null) {
                topic.setId(Integer.valueOf(reqId));
                topic = dao.getTopicById(topic).get(0);
                editFlag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processTopic() {
        try {
            dao.saveTopic(topic);
            if (topic.getId() == null) {
                File theDir = new File(dao.getSettingByDesc("LEARN_OBJ_FILE").getValue() + topic.getCourseId().getCourseCode() + "_"
                        + topic.getCourseId().getFullName() + "\\" + topic.getTopicName());
                if (!theDir.exists()) {
                    theDir.mkdir();
                }
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Topic processed succefully ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while processing", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    public void deleteTopic(Topic topic) {
        try {
            File myFile = new File(dao.getSettingByDesc("LEARN_OBJ_FILE").getValue() + topic.getCourseId().getCourseCode() + "_"
                    + topic.getCourseId().getFullName() + "\\" + topic.getTopicName());
            if (myFile.delete()) {
                dao.deleteTopic(topic);
                searchTopic();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Topic deleted successfully", null);
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

    public void searchTopic() {
        topicList = dao.searchTopic(topic);
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

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public CourseConv getCoConv() {
        return coConv;
    }

    public void setCoConv(CourseConv coConv) {
        this.coConv = coConv;
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

}
