/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id")
    , @NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name")
    , @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE trim(u.email) = :email")
    , @NamedQuery(name = "Users.findByMPhone", query = "SELECT u FROM Users u WHERE u.mPhone = :mPhone")
    , @NamedQuery(name = "Users.findByCountry", query = "SELECT u FROM Users u WHERE u.country = :country")
    , @NamedQuery(name = "Users.findByCity", query = "SELECT u FROM Users u WHERE u.city = :city")
    , @NamedQuery(name = "Users.findByBirthday", query = "SELECT u FROM Users u WHERE u.birthday = :birthday")
    , @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "Users.findByMiddleName", query = "SELECT u FROM Users u WHERE u.middleName = :middleName")
    , @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "Users.findByEmailAndPass", query = "SELECT u FROM Users u WHERE trim(u.email) = :email and u.password = :password")})
public class Users implements Serializable {

    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(mappedBy = "userId")
    private List<LearnObjTrans> learnObjTransList;

    @OneToMany(mappedBy = "userId")
    private List<Student> studentList;

    @OneToMany(mappedBy = "userId")
    private List<TransactionData> transactionDataList;

    @OneToMany(mappedBy = "createdBy")
    private List<Questionaire> questionaireList;
    @OneToMany(mappedBy = "modifiedBy")
    private List<Questionaire> questionaireList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "M_PHONE")
    private String mPhone;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "CITY")
    private String city;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @OneToMany(mappedBy = "userId")
    private List<Comment> commentList;
    @OneToMany(mappedBy = "userId")
    private List<Instructor> instructorList;
    @OneToOne(mappedBy = "userId")
    private Student student;
    @OneToMany(mappedBy = "userId")
    private List<Profile> profileList;
    @OneToOne(mappedBy = "userId")
    private UserRole userRole;
    @Column(name = "PASSWORD")
    private String password;

    public Users() {
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMPhone() {
        return mPhone;
    }

    public void setMPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @XmlTransient
    public List<Instructor> getInstructorList() {
        return instructorList;
    }

    public void setInstructorList(List<Instructor> instructorList) {
        this.instructorList = instructorList;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @XmlTransient
    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.User[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Questionaire> getQuestionaireList() {
        return questionaireList;
    }

    public void setQuestionaireList(List<Questionaire> questionaireList) {
        this.questionaireList = questionaireList;
    }

    @XmlTransient
    public List<Questionaire> getQuestionaireList1() {
        return questionaireList1;
    }

    public void setQuestionaireList1(List<Questionaire> questionaireList1) {
        this.questionaireList1 = questionaireList1;
    }

    @XmlTransient
    public List<TransactionData> getTransactionDataList() {
        return transactionDataList;
    }

    public void setTransactionDataList(List<TransactionData> transactionDataList) {
        this.transactionDataList = transactionDataList;
    }

    @XmlTransient
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @XmlTransient
    public List<LearnObjTrans> getLearnObjTransList() {
        return learnObjTransList;
    }

    public void setLearnObjTransList(List<LearnObjTrans> learnObjTransList) {
        this.learnObjTransList = learnObjTransList;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
