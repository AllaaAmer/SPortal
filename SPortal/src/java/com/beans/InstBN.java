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
import com.dlayer.entities.Role;
import com.dlayer.entities.RolePriviledge;
import com.dlayer.entities.UserRole;
import com.dlayer.entities.Users;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Date;
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
public class InstBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    List<Instructor> insList = new ArrayList<>();
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    RolePriviledge rulePriv;
    String entityId;
    String instName;
    Course selectedCourse;
    List<Course> coursesList = new ArrayList<>();
    CourseConv coConv = new CourseConv();
    Instructor instructor = new Instructor();
    Users user = new Users();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
    boolean editFlag = false;
    List<Course> selectedCourseList = new ArrayList<>();

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
            insList = dao.getAllInstructor();

            coursesList = dao.getAllCourses();
            coConv.list = coursesList;
            if (reqId != null) {
                instructor.setId(Integer.valueOf(reqId));
                instructor = dao.getInstructorById(instructor).get(0);
                editFlag = true;
            } else {
                instructor.setUserId(user);
                instructor.setCourseList(selectedCourseList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processInst() {
        try {
            if (instructor.getId() == null) {
                instructor.getUserId().setPassword(dao.hashWithMD5(instructor.getUserId().getPassword()));
                instructor.setCreatedAt(new Date());
            }

            dao.saveInstructor(instructor);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Instrustor processed succefully ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while processing", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    public void deleteInst(Instructor ins) {
        try {
            dao.deleteInst(ins);
            dao.deleteUser(ins.getUserId());
            searchInst();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Instructor deleted successfully", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while deleting", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            e.printStackTrace();
        }
    }

    public void searchInst() {
        insList = dao.searchInstructor(instName, selectedCourse);
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

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public List<Course> getSelectedCourseList() {
        return selectedCourseList;
    }

    public void setSelectedCourseList(List<Course> selectedCourseList) {
        this.selectedCourseList = selectedCourseList;
    }

}
