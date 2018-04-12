/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.converter.CourseConv;
import com.converter.SemConv;
import com.dlayer.Dao;
import com.dlayer.entities.Course;
import com.dlayer.entities.Instructor;
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
public class CourseBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    List<Instructor> insList = new ArrayList<>();
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    RolePriviledge rulePriv;
    String entityId;
    List<Course> coursesList = new ArrayList<>();
    SemConv semConv = new SemConv();
    Course course = new Course();
    Users user = new Users();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
    boolean editFlag = false;
    List<Semester> semList = new ArrayList<>();
    Semester sem = new Semester();

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

            //get all instructor
            coursesList = dao.getAllCourses();

            semList = dao.getAllSemesters();
            semConv.list = semList;
            if (reqId != null) {
                course.setId(Integer.valueOf(reqId));
                course = dao.getCourseById(course);
                editFlag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processCourse() {
        try {
            dao.saveCourse(course);
            if (course.getId() == null) {
                File theDir = new File(dao.getSettingByDesc("LEARN_OBJ_FILE").getValue() + course.getCourseCode() + "_"
                        + course.getFullName());
                if (!theDir.exists()) {
                    theDir.mkdir();
                }
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Course processed succefully ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while processing", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    public void deleteCourse(Course course) {
        try {
            File myFile = new File(dao.getSettingByDesc("LEARN_OBJ_FILE").getValue() + course.getCourseCode() + "_"
                    + course.getFullName());
            if (myFile.delete()) {
                dao.deleteCourse(course);
                searchCourse();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Course deleted successfully", null);
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

    public void searchCourse() {
        coursesList = dao.searchCourse(course);
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

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public SemConv getSemConv() {
        return semConv;
    }

    public void setSemConv(SemConv semConv) {
        this.semConv = semConv;
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

}
