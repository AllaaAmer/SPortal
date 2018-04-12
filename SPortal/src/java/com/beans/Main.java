package com.beans;

import com.utils.PasswordValid;
import com.utils.EmailValidator;
import com.converter.SemConv;
import com.dlayer.Dao;
import com.dlayer.entities.Role;
import com.dlayer.entities.RolePriviledge;
import com.dlayer.entities.Semester;
import com.dlayer.entities.Student;
import com.dlayer.entities.UserRole;
import com.dlayer.entities.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yaraYaseen
 */
@ViewScoped
@ManagedBean
public class Main {

    String confirmPass;
    Dao dao = new Dao();
    EmailValidator ev = new EmailValidator();
    Student student = new Student();
    List<Semester> semList = new ArrayList<>();
    Users user = new Users();
    SemConv semCon = new SemConv();
    PasswordValid pv = new PasswordValid();
    String emial;
    String password;
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    boolean errorFlag = false;

    //Methods----------------------------------
    @PostConstruct
    public void prerpMethod() {
        student.setUserId(user);
        semList = dao.getAllSemesters();

        semCon.list = semList;

        try {
            if (userAcc != null) {
                rolePrivList = dao.getPagesByRule(userAcc.getUserRole().getRoleId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void goToREg() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createReg() {
        try {
            if (ev.validate(student.getUserId().getEmail())) {
                if (!dao.checkMailExist(student.getUserId().getEmail())) {
                    if (pv.validate(student.getUserId().getPassword())) {
                        if (student.getUserId().getPassword().equals(confirmPass)) {
                            student.setQuestionaireFlag(false);
                            student.setCreatedAt(new Date());
                            student = dao.addStudent(student);
                            FacesContext facesContext2 = FacesContext.getCurrentInstance();
                            HttpSession session = (HttpSession) facesContext2.getExternalContext().getSession(true);
                            session.setAttribute("regStudent", student);
                            FacesContext.getCurrentInstance().getExternalContext().redirect("questionair.xhtml");
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registration went successfully"));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Passwords don't match"));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password should be 8 characters with special character"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email already exists"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Not a valid Email"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error will registring"));
            e.printStackTrace();
        }
    }

    public void signIn() {
        try {
            user = dao.getUser(emial, password.trim());
            if (user != null) {
                FacesContext facesContext1 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext1.getExternalContext().getSession(true);
                session.setAttribute("loggedUser", user);
                String url = user.getUserRole().getRoleId().getRolePriviledgeList().get(0).getPageId().getPageUrl()
                        + "?PId=" + user.getUserRole().getRoleId().getRolePriviledgeList().get(0).getPageId().getId() + "&faces-redirect=true;";
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            } else {
                errorFlag = true;
            }
        } catch (Exception e) {
            errorFlag = true;
            e.printStackTrace();
        }
    }

    public void signOut() {
        try {
            ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //setters and getters-----------------------
    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Semester> getSemList() {
        return semList;
    }

    public void setSemList(List<Semester> semList) {
        this.semList = semList;
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    public EmailValidator getEv() {
        return ev;
    }

    public void setEv(EmailValidator ev) {
        this.ev = ev;
    }

    public boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public SemConv getSemCon() {
        return semCon;
    }

    public void setSemCon(SemConv semCon) {
        this.semCon = semCon;
    }

    public PasswordValid getPv() {
        return pv;
    }

    public void setPv(PasswordValid pv) {
        this.pv = pv;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RolePriviledge> getRolePrivList() {
        return rolePrivList;
    }

    public void setRolePrivList(List<RolePriviledge> rolePrivList) {
        this.rolePrivList = rolePrivList;
    }

}
