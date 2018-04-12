/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.converter.CourseConv;
import com.converter.LearnObjTypeCon;
import com.converter.SemConv;
import com.dlayer.Dao;
import com.dlayer.entities.Course;
import com.dlayer.entities.Instructor;
import com.dlayer.entities.LearningObjectType;
import com.dlayer.entities.LearningStratigy;
import com.dlayer.entities.LearningStratigyObject;
import com.dlayer.entities.LearningStratigyStyle;
import com.dlayer.entities.RolePriviledge;
import com.dlayer.entities.Semester;
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
public class LearningStBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    List<Instructor> insList = new ArrayList<>();
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    RolePriviledge rulePriv;
    String entityId;
    List<LearningStratigy> learnStraList = new ArrayList<>();
    Course course = new Course();
    Users user = new Users();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
    boolean editFlag = false;
    List<Semester> semList = new ArrayList<>();
    Semester sem = new Semester();
    LearningStratigy ls = new LearningStratigy();
    LearningStratigyStyle learnStSt = new LearningStratigyStyle();
    List<LearningObjectType> objectsTypeList = new ArrayList<>();
    List<LearningObjectType> selectedObjectsTypeList = new ArrayList<>();
    LearnObjTypeCon learnObjTConv = new LearnObjTypeCon();
    List<LearningStratigyStyle> styleList = new ArrayList<>();
    CourseConv courseConv = new CourseConv();
    List<Course> courseList = new ArrayList<>();

    //Methods--------------------------------------------------------------------
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

            learnStraList = dao.searchLearnObj(null, null);

            objectsTypeList = dao.getAllLearnObjectType();
            learnObjTConv.list = objectsTypeList;

            if (reqId != null) {
                ls.setId(Integer.valueOf(reqId));
                ls = dao.getLearnStById(ls);
                editFlag = true;
                styleList = ls.getLearningStratigyStyleList();
            }
            courseList = dao.getAllCourses();
            courseConv.list = courseList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processLearnStratigy() {
        try {
            ls.setLearningStratigyStyleList(styleList);
            dao.saveStratigy(ls, editFlag);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Stratigy processed succefully ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while processing", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    public void removeStratigyStyle(LearningStratigyStyle style) {
        try {
            for (LearningStratigyStyle ob : styleList) {
                if (ob.getStratigicStyleName().equals(style.getStratigicStyleName())) {
                    styleList.remove(ob);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void searchLearningSt() {
        learnStraList = dao.searchLearnObj(ls, course);
    }

    public void addStyle() {
        List<LearningStratigyObject> learnObjList = new ArrayList<>();
        for (LearningObjectType ot : selectedObjectsTypeList) {
            LearningStratigyObject so = new LearningStratigyObject();
            so.setLearningObjectTypeId(ot);
            learnObjList.add(so);
        }
        learnStSt.setLearningStratigyObjectList(learnObjList);
        LearningStratigyStyle ss = new LearningStratigyStyle();
        ss.setLearningStratigyObjectList(learnStSt.getLearningStratigyObjectList());
        ss.setSequence(learnStSt.getSequence());
        ss.setStratigicStyleName(learnStSt.getStratigicStyleName());
        styleList.add(ss);
    }

    public void deleteLearningStratigy(LearningStratigy st) {
        try {
            dao.deleteLearnStObj(st);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Stratigy deleted succefully ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            learnStraList = dao.searchLearnObj(null, null);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while deleting", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    //Setters and getters-------------------------------------------------------------------------------
    public List<Instructor> getInsList() {
        return insList;
    }

    public void setInsList(List<Instructor> insList) {
        this.insList = insList;
    }

    public Users getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(Users userAcc) {
        this.userAcc = userAcc;
    }

    public RolePriviledge getRulePriv() {
        return rulePriv;
    }

    public void setRulePriv(RolePriviledge rulePriv) {
        this.rulePriv = rulePriv;
    }

    public List<LearningStratigy> getLearnStraList() {
        return learnStraList;
    }

    public void setLearnStraList(List<LearningStratigy> learnStraList) {
        this.learnStraList = learnStraList;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public List<Semester> getSemList() {
        return semList;
    }

    public void setSemList(List<Semester> semList) {
        this.semList = semList;
    }

    public LearningStratigy getLs() {
        return ls;
    }

    public void setLs(LearningStratigy ls) {
        this.ls = ls;
    }

    public LearningStratigyStyle getLearnStSt() {
        return learnStSt;
    }

    public void setLearnStSt(LearningStratigyStyle learnStSt) {
        this.learnStSt = learnStSt;
    }

    public List<LearningObjectType> getObjectsTypeList() {
        return objectsTypeList;
    }

    public void setObjectsTypeList(List<LearningObjectType> objectsTypeList) {
        this.objectsTypeList = objectsTypeList;
    }

    public List<LearningObjectType> getSelectedObjectsTypeList() {
        return selectedObjectsTypeList;
    }

    public void setSelectedObjectsTypeList(List<LearningObjectType> selectedObjectsTypeList) {
        this.selectedObjectsTypeList = selectedObjectsTypeList;
    }

    public LearnObjTypeCon getLearnObjTConv() {
        return learnObjTConv;
    }

    public void setLearnObjTConv(LearnObjTypeCon learnObjTConv) {
        this.learnObjTConv = learnObjTConv;
    }

    public List<LearningStratigyStyle> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<LearningStratigyStyle> styleList) {
        this.styleList = styleList;
    }

    public CourseConv getCourseConv() {
        return courseConv;
    }

    public void setCourseConv(CourseConv courseConv) {
        this.courseConv = courseConv;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

}
