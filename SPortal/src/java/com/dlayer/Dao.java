/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer;

import com.dlayer.entities.Course;
import com.dlayer.entities.Criteria;
import com.dlayer.entities.Instructor;
import com.dlayer.entities.LearnObjCritPres;
import com.dlayer.entities.LearnObjTrans;
import com.dlayer.entities.LearningObject;
import com.dlayer.entities.LearningObjectType;
import com.dlayer.entities.LearningStratigy;
import com.dlayer.entities.LearningStratigyObject;
import com.dlayer.entities.LearningStratigyStyle;
import com.dlayer.entities.Lesson;
import com.dlayer.entities.LessonDir;
import com.dlayer.entities.LessonFolders;
import com.dlayer.entities.OwlRules;
import com.dlayer.entities.Profile;
import com.dlayer.entities.ProfileDetails;
import com.dlayer.entities.Questionaire;
import com.dlayer.entities.Role;
import com.dlayer.entities.RolePriviledge;
import com.dlayer.entities.Semester;
import com.dlayer.entities.Settings;
import com.dlayer.entities.Student;
import com.dlayer.entities.Topic;
import com.dlayer.entities.TransactionData;
import com.dlayer.entities.UserRole;
import com.dlayer.entities.Users;
import java.awt.Font;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author yaraYaseen
 */
public class Dao {
    //JPA implemnation

    FactoryManagerHandler emfHandller = new FactoryManagerHandler();

    public Users addUser(Users user, String role) {

        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        user.setPassword(hashWithMD5(user.getPassword()));

        user = em.merge(user);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);

        addUserRole(user, role);

        return user;

    }

    public void addUserRole(Users user, String role) {

        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        UserRole userRole = new UserRole();

        userRole.setUserId(user);
        userRole.setRoleId(getRuleByValue(role));

        em.merge(userRole);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);

    }

    public Role getRuleByValue(String role) {
        EntityManager em = emfHandller.getEntityManager();

        try {
            Role list = em.createNamedQuery("Role.findByRoleName", Role.class).setParameter("roleName", role).getSingleResult();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public void deleteUser(Users ins) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from Users a where a.id= " + ins.getId();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public Student addStudent(Student student) {

        student.setUserId(addUser(student.getUserId(), "STUDENT"));

        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        student = em.merge(student);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);

        return student;

    }

    public List<Semester> getAllSemesters() {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Semester> sems = em.createNamedQuery("Semester.findAll", Semester.class).getResultList();

            emfHandller.closeEntityManager(em);

            return sems;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public String hashWithMD5(String word) {
        String returnResult = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(word.getBytes());

            byte byteData[] = md.digest();

            // convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }

            returnResult = sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            returnResult = null;
        }
        return returnResult;
    }

    public Users getUser(String email, String passw) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            Users users = em.createNamedQuery("Users.findByEmailAndPass", Users.class).setParameter("email", email.trim())
                    .setParameter("password", hashWithMD5(passw)).getSingleResult();

            emfHandller.closeEntityManager(em);

            return users;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;
        }
    }

    public Student getStudentByUserId(int id) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            Student list = em.createNamedQuery("Student.findByUserID", Student.class).setParameter("userid", id).getSingleResult();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public boolean checkMailExist(String email) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            Users users = em.createNamedQuery("Users.findByEmail", Users.class).setParameter("email", email.trim()).getSingleResult();

            emfHandller.closeEntityManager(em);

            if (users == null) {
                return false;
            }

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return false;

        }
    }

    public List<Questionaire> getAllQuestionnaireQues() {
        EntityManager em = emfHandller.getEntityManager();
        try {
            List<Questionaire> list = em.createNamedQuery("Questionaire.findAll", Questionaire.class).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public Profile addProfile(Profile profile) {
        try {

            EntityManager em = emfHandller.getEntityManager(); //1

            em.getTransaction().begin(); //1

            profile = em.merge(profile);// 1

            for (ProfileDetails pd : profile.getProfileDetailsList()) { // 2n
                pd.setProfileId(profile);
                em.merge(pd);
            }

            em.getTransaction().commit(); //1

            emfHandller.closeEntityManager(em);//1

            return profile;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void updateStudent(Student student) {
        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        em.merge(student);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);

    }

    public List<RolePriviledge> getPagesByRule(Role role) {
        EntityManager em = emfHandller.getEntityManager();

        try {
            List<RolePriviledge> list = em.createNamedQuery("RolePriviledge.findByRole", RolePriviledge.class).setParameter("roleId", role.getId()).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public RolePriviledge getRolePrivlageByUserIdEntityId(Integer roleId, String entityId) {

        EntityManager em = emfHandller.getEntityManager();

        RolePriviledge securityRolePrivilageArray = (RolePriviledge) em.createNamedQuery("RolePriviledge.findByUserIdEntityId").setParameter("roleId", roleId).setParameter("pageId", Integer.parseInt(entityId)).getSingleResult();

        emfHandller.closeEntityManager(em);

        return securityRolePrivilageArray;

    }

    public List<Instructor> getAllInstructor() {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Instructor> list = em.createNamedQuery("Instructor.findAll", Instructor.class).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public void deleteInst(Instructor ins) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from Instructor a where a.id= " + ins.getId();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public List<Course> getAllCourses() {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Course> list = em.createNamedQuery("Course.findAll", Course.class).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<Instructor> searchInstructor(String instName,
            Course selectedCourse) {

        EntityManager em = emfHandller.getEntityManager();

        List<Instructor> result;

        Query q = null;

        String query = "select a from Instructor a where 1=1";

//        //search by couses
//        if (selectedCourse != null) {
//
//            query += " and c.id = " + selectedCourse.getId();
//
//        }
        //search by instructor name
        if (instName != null && instName.trim().length() > 0) {

            query += " and a.userId.name LIKE '%" + instName + "%'";

        }

        q = em.createQuery(query);

        result = q.getResultList();

        emfHandller.closeEntityManager(em);

        return result;

    }

    public void saveInstructor(Instructor inst) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            if (inst.getId() != null) {
                deleteInst(inst);
                deleteUser(inst.getUserId());
            }
            em.getTransaction().begin();

            inst.setUserId(addUser(inst.getUserId(), "INSTRUCTOR"));

            em.persist(inst);

            List<Instructor> ins = new ArrayList<>();
            ins.add(inst);
            for (Course c : inst.getCourseList()) {
                c.setInstructorList(ins);
                em.merge(c);
            }

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

        }

    }

    public List<Instructor> getInstructorById(Instructor inst) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Instructor> list = em.createNamedQuery("Instructor.findById", Instructor.class).setParameter("id", inst.getId()).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<Course> searchCourse(Course course) {

        EntityManager em = emfHandller.getEntityManager();

        List<Course> result;

        Query q = null;

        String query = "select a from Course a where 1=1";

        //search by course code
        if (course.getCourseCode() != null && course.getCourseCode().trim().length() > 0) {

            query += " and a.courseCode LIKE '%" + course.getCourseCode() + "%'";

        }

        //search by course full name
        if (course.getFullName() != null && course.getFullName().trim().length() > 0) {

            query += " and a.fullName LIKE '%" + course.getFullName() + "%'";

        }

        //search by short name
        if (course.getShortName() != null && course.getShortName().trim().length() > 0) {

            query += " and a.shortName LIKE '%" + course.getShortName() + "%'";

        }

        if (course.getSemesterId() != null) {

            query += " and a.semesterId.id = " + course.getSemesterId().getId() + "";
        }

        q = em.createQuery(query);

        result = q.getResultList();

        emfHandller.closeEntityManager(em);

        return result;

    }

    public void deleteCourse(Course course) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from Course a where a.id= " + course.getId();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public Course getCourseById(Course course) {
        EntityManager em = emfHandller.getEntityManager();

        try {
            System.out.println("course: " + course);
            Course list = (Course) em.createNamedQuery("Course.findById", Course.class).setParameter("id", course.getId()).getSingleResult();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public void saveCourse(Course course) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            course = em.merge(course);

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

        }

    }

    public Settings getSettingByDesc(String desc) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            Settings result = em.createNamedQuery("Settings.findByDes", Settings.class).setParameter("des", desc).getSingleResult();

            emfHandller.closeEntityManager(em);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public Criteria saveCriteria(Criteria criteria) {
        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        criteria = em.merge(criteria);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);

        return criteria;
    }

    public LearningObjectType saveLearningObjType(LearningObjectType learnObjType) {
        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        learnObjType = em.merge(learnObjType);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);

        return learnObjType;
    }

    public Criteria getCriteriaByValue(String criteria) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            Criteria result = em.createNamedQuery("Criteria.findByCriteria", Criteria.class).setParameter("criteria", "%" + criteria + "%").getSingleResult();

            emfHandller.closeEntityManager(em);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public LearningObjectType getLearnObjTypeByValue(String learnObjType) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            LearningObjectType result = em.createNamedQuery("LearningObjectType.findByLearningObjectType", LearningObjectType.class).setParameter("learningObjectType", "%" + learnObjType + "%").getSingleResult();

            emfHandller.closeEntityManager(em);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public void saveOwlRules(List<OwlRules> owlRuleList) {
        EntityManager em = emfHandller.getEntityManager();
        try {
            em.getTransaction().begin();

            for (OwlRules or : owlRuleList) {
                if (or.getCriteriaId().getId() == null) {
                    Criteria c = getCriteriaByValue(or.getCriteriaId().getCriteria());
                    if (c != null) {
                        or.setCriteriaId(c);
                    } else {
                        or.setCriteriaId(saveCriteria(or.getCriteriaId()));
                    }
                }
                if (or.getLearningObjectTypeId().getId() == null) {
                    LearningObjectType lo = getLearnObjTypeByValue(or.getLearningObjectTypeId().getLearningObjectType());
                    if (lo != null) {
                        or.setLearningObjectTypeId(lo);
                    } else {
                        or.setLearningObjectTypeId(saveLearningObjType(or.getLearningObjectTypeId()));
                    }
                }

                if (!checkIfRuleExist(or)) {
                    em.merge(or);
                }
            }

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);
        } catch (Exception ex) {
            emfHandller.closeEntityManager(em);
            ex.printStackTrace();
        }

    }

    public boolean checkIfRuleExist(OwlRules owlRule) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            OwlRules result = em.createNamedQuery("OwlRules.findByDesCrLo", OwlRules.class).setParameter("description", owlRule.getDescription())
                    .setParameter("criteria", owlRule.getCriteriaId()).setParameter("lo", owlRule.getLearningObjectTypeId()).getSingleResult();

            emfHandller.closeEntityManager(em);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return false;

        }
    }

    public OwlRules saveRuleDB(OwlRules owlRule) {
        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        owlRule = em.merge(owlRule);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);

        return owlRule;
    }

    public List<OwlRules> searchRulesFromDB() {

        EntityManager em = emfHandller.getEntityManager();

        List<OwlRules> result;

        Query q = null;

        String query = "select a from OwlRules a where 1=1";

//        //search by couses
//        if (selectedCourse != null) {
//
//            query += " and c.id = " + selectedCourse.getId();
//
//        }
        q = em.createQuery(query);

        result = q.getResultList();

        emfHandller.closeEntityManager(em);

        return result;

    }

    public void saveTopic(Topic topic) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            topic = em.merge(topic);

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

        }

    }

    public List<Topic> searchTopic(Topic topic) {

        EntityManager em = emfHandller.getEntityManager();

        List<Topic> result;

        Query q = null;

        String query = "select a from Topic a where 1=1";

        //search by course code
        if (topic.getTopicName() != null && topic.getTopicName().trim().length() > 0) {

            query += " and a.topicName LIKE '%" + topic.getTopicName() + "%'";

        }

        if (topic.getCourseId() != null) {

            query += " and a.courseId.id = " + topic.getCourseId().getId() + "";
        }

        q = em.createQuery(query);

        result = q.getResultList();

        emfHandller.closeEntityManager(em);

        return result;

    }

    public List<Topic> getTopicById(Topic inst) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Topic> list = em.createNamedQuery("Topic.findById", Topic.class).setParameter("id", inst.getId()).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public void deleteTopic(Topic topic) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from Topic a where a.id= " + topic.getId();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public List<Lesson> getLessonById(Lesson inst) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Lesson> list = em.createNamedQuery("Lesson.findById", Lesson.class).setParameter("id", inst.getId()).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public Lesson saveLesson(Lesson lesson, boolean editFlag) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            List<LessonFolders> listy = lesson.getLessonFoldersList();

            lesson = em.merge(lesson);

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

            List<LessonFolders> list = new ArrayList<>();
            for (LessonFolders lf : listy) {
                lf.setLessonId(lesson);
                list.add(lf);
            }
            saveFolder(list, editFlag);

            return lesson;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);
            return null;
        }

    }

    public void saveFolder(List<LessonFolders> list, boolean editFlag) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            if (editFlag) {
                deleteFolder(list.get(0).getLessonId());
            }

            for (LessonFolders lf : list) {
                lf = em.merge(lf);
            }

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);
        }

    }

    public void deleteFolder(Lesson obj) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from LESSON_FOLDERS  where LESSON_ID= " + obj.getId();
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public List<LessonFolders> getFoldersByLessonId(Lesson inst) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<LessonFolders> list = em.createNamedQuery("LessonFolders.findByLessonId", LessonFolders.class).setParameter("lesson", inst.getId()).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public void deleteLesson(Lesson lesson) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from Lesson a where a.id= " + lesson.getId();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public List<Lesson> searchLesson(Lesson lesson) {

        EntityManager em = emfHandller.getEntityManager();

        List<Lesson> result;

        Query q = null;

        String query = "select a from Lesson a where 1=1";

        //search by lesson name
        if (lesson.getLessonName() != null && lesson.getLessonName().trim().length() > 0) {

            query += " and a.lessonName LIKE '%" + lesson.getLessonName() + "%'";

        }

        if (lesson.getTopicId() != null) {

            query += " and a.topicId.id = " + lesson.getTopicId().getId() + "";
        }

        q = em.createQuery(query);

        result = q.getResultList();

        emfHandller.closeEntityManager(em);

        return result;

    }

    public List<LearningObjectType> getAllLearnObjectType() {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<LearningObjectType> list = em.createNamedQuery("LearningObjectType.findAll", LearningObjectType.class).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public LearningObject saveLearnOBj(LearningObject learnObj) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            learnObj = em.merge(learnObj);

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

            return learnObj;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);
            return null;

        }

    }

    public void deleteLearnObj(LearningObject learnObj) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from LearningObject a where a.id= " + learnObj.getId();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public List<LearningObject> searchLearnObj(LearningObject learnObj, Lesson lesson, Topic topic, Course course) {

        EntityManager em = emfHandller.getEntityManager();

        List<LearningObject> result;

        Query q = null;

        String query = "select a from LearningObject a where 1=1";

        //search by lesson name
        if (lesson.getId() != null) {

            query += " and a.lessonLearnObjList IN (" + lesson.getId() + ")";

        }

//        if (topic != null) {
//
//            query += " and a.topicId.id = " + lesson.getTopicId().getId() + "";
//        }
//
//        //search by lesson name
//        if (course != null) {
//
//            query += " and a.lessonName LIKE '%" + lesson.getLessonName() + "%'";
//
//        }
        if (learnObj.getFileDesc() != null && learnObj.getFileDesc().trim().length() > 0) {

            query += " and a.fileDesc LIKE '%" + learnObj.getFileDesc() + "%'";

        }

        q = em.createQuery(query);

        result = q.getResultList();

        emfHandller.closeEntityManager(em);

        return result;

    }

    public void saveLessonDir(LessonDir lessonDir) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            lessonDir = em.merge(lessonDir);

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

        }

    }

    public List<Lesson> getLessonByTopicId(Topic id) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Lesson> list = em.createNamedQuery("Lesson.findByTopicId", Lesson.class).setParameter("topicId", id).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<LearningStratigy> searchLearnObj(LearningStratigy learnStObj, Course course) {

        EntityManager em = emfHandller.getEntityManager();

        List<LearningStratigy> result;

        Query q = null;

        String query = "select a from LearningStratigy a where 1=1";

        //search by id
        if (learnStObj != null) {
            if (learnStObj.getId() != null) {

                query += " and a.id in = " + learnStObj.getId();

            }
        }

        //search by course name
        if (course != null) {

            query += " and a.courseId.courseCode = " + course.getCourseCode();

        }

        //search by name
        if (learnStObj != null) {
            if (learnStObj.getStratigicName() != null && learnStObj.getStratigicName().trim().length() > 0) {

                query += " and upper(a.stratigicName) LIKE '%" + learnStObj.getStratigicName().toUpperCase() + "%'";

            }
        }

        q = em.createQuery(query);

        result = q.getResultList();

        emfHandller.closeEntityManager(em);

        return result;

    }

    public void saveStratigy(LearningStratigy obj, boolean editFlag) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            obj = em.merge(obj);

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

            List<LearningStratigyStyle> list = new ArrayList<>();
            for (LearningStratigyStyle ls : obj.getLearningStratigyStyleList()) {
                ls.setLearningStratigyId(obj);
                list.add(ls);
            }
            saveStratigyStyle(list, editFlag);

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

        }

    }

    public void saveStratigyStyle(List<LearningStratigyStyle> list, boolean editFlag) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            if (editFlag) {
                deleteStratigyStyle(list.get(0).getLearningStratigyId());
            }
            for (LearningStratigyStyle obj : list) {
                obj = em.merge(obj);
                List<LearningStratigyObject> objList = new ArrayList<>();
                for (LearningStratigyObject so : obj.getLearningStratigyObjectList()) {
                    so.setStratigicStyleId(obj);
                    objList.add(so);
                }
                saveStratigyStyleObj(objList);
            }

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

        }
    }

    public void saveStratigyStyleObj(List<LearningStratigyObject> list) {

        EntityManager em = emfHandller.getEntityManager();

        try {

            em.getTransaction().begin();

            for (LearningStratigyObject obj : list) {
                obj = em.merge(obj);
            }

            em.getTransaction().commit();

            emfHandller.closeEntityManager(em);

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

        }

    }

    public void deleteLearnStObj(LearningStratigy startObj) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from LearningStratigy a where a.id= " + startObj.getId();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public void deleteStratigyStyle(LearningStratigy startObj) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "delete from LearningStratigyStyle a where a.learningStratigyId.id= " + startObj.getId();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);
    }

    public LearningStratigy getLearnStById(LearningStratigy startObj) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            LearningStratigy list = (LearningStratigy) em.createNamedQuery("LearningStratigy.findById", LearningStratigy.class).setParameter("id", startObj.getId()).getSingleResult();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<LessonFolders> getFoldersByLessonIdFol(LearningObject inst, Lesson lesson) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<LessonFolders> list = em.createNamedQuery("LessonFolders.findByLessonIFol", LessonFolders.class).setParameter("lesson", lesson.getId())
                    .setParameter("folderName", inst.getFolderId().getFolderName()).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public LearningObject getLearningObjByID(String id) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            LearningObject list = em.createNamedQuery("LearningObject.findById", LearningObject.class).setParameter("id", Integer.valueOf(id)).getSingleResult();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<LearningObject> getLearningObjs() {
        EntityManager em = emfHandller.getEntityManager();
        try {
            List<LearningObject> list = em.createNamedQuery("LearningObject.findAll", LearningObject.class).getResultList();
            emfHandller.closeEntityManager(em);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            emfHandller.closeEntityManager(em);
            return null;
        }
    }

    public void saveTrans(TransactionData transObj) {
        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        em.merge(transObj);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);
    }

    public List<TransactionData> getTransByUserID(int id) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<TransactionData> list = em.createNamedQuery("TransactionData.findByUserID", TransactionData.class).setParameter("userID", id).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<OwlRules> getLearnObjTypeByCri(List<String> criteriaList) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<OwlRules> list = em.createNamedQuery("OwlRules.findByCriteria", OwlRules.class).setParameter("criList", criteriaList).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<Integer> getLearnObjTypeByCri(String criteria, Lesson lesson) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "select lo.id from owl_rules o, lesson l, learning_object lo,lesson_folders lf,criteria c "
                + "where lo.folder_id = lf.id and lf.lesson_id = l.id "
                + "and o.LEARNING_OBJECT_TYPE_ID = lo.LEARNING_OBJ_TY_ID "
                + "and o.criteria_id = c.id "
                + "and c.CRITERIA = '" + criteria + "' "
                + "and l.id=" + lesson.getId();
        List<Integer> list = em.createNativeQuery(query).getResultList();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);

        return list;
    }

    public int countLesson(Topic topic) {
        EntityManager em = emfHandller.getEntityManager();
        em.getTransaction().begin();
        String query = "select count(*) from lesson where topic_id = " + topic.getId();
        int list = (Integer) em.createNativeQuery(query).getSingleResult();
        em.getTransaction().commit();
        emfHandller.closeEntityManager(em);

        return list;
    }

    public LearningObject getLearnObjById(int id) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            LearningObject result = em.createNamedQuery("LearningObject.findById", LearningObject.class).setParameter("id", id).getSingleResult();

            emfHandller.closeEntityManager(em);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public void saveLearnObjTrans(LearnObjTrans transObj) {
        EntityManager em = emfHandller.getEntityManager();

        em.getTransaction().begin();

        em.merge(transObj);

        em.getTransaction().commit();

        emfHandller.closeEntityManager(em);
    }

    public List<LearnObjTrans> getLearnObjTranByLesson(Lesson lesson, Users user) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<LearnObjTrans> result = em.createNamedQuery("LearnObjTrans.findByLesson", LearnObjTrans.class)
                    .setParameter("topicId", lesson.getTopicId().getId())
                    .setParameter("userId", user.getId())
                    .setParameter("lessonId", lesson.getId()).getResultList();

            emfHandller.closeEntityManager(em);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<LearnObjTrans> getLearnObjTranByUser(Lesson lesson, Users user) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<LearnObjTrans> result = em.createNamedQuery("LearnObjTrans.findByUser", LearnObjTrans.class)
                    .setParameter("topicId", lesson.getTopicId().getId())
                    .setParameter("userId", user.getId())
                    .setParameter("ltopicId", lesson.getTopicId().getId())
                    .setParameter("luserId", user.getId()).getResultList();

            emfHandller.closeEntityManager(em);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    //new
    public List<Object[]> getTimeSpent(Topic topic, Users user) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Object[]> result = em.createNativeQuery("select USER_id,LEARNING_OBJECT_TYPE_ID,sum(d.time_spent) time "
                    + "from TRANSACTION_DATA d,LESSON l,USERS u "
                    + "where d.LESSON_ID = l.ID "
                    + "and u.ID = d.USER_ID "
                    + " and d.USER_ID = ? "
                    + " and l.TOPIC_ID = ? "
                    + " group by d.LEARNING_OBJECT_TYPE_ID,user_id")
                    .setParameter(1, user.getId()).setParameter(2, topic.getId()).getResultList();

            emfHandller.closeEntityManager(em);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<OwlRules> getCriByLearnObjType(Integer lot) {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<OwlRules> list = em.createQuery("select o from OwlRules o where o.learningObjectTypeId.id = :lot", OwlRules.class).setParameter("lot", lot).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<Criteria> getAllCriteria() {
        EntityManager em = emfHandller.getEntityManager();

        try {

            List<Criteria> list = em.createNamedQuery("Criteria.findAll", Criteria.class).getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    public List<Profile> getLastProfileByUser(Users user) {
        EntityManager em = emfHandller.getEntityManager();

        try {
//EFinal.dbo.
            List<Profile> list = em.createQuery("select p from Profile p where p.userId.id = :userId and "
                    + " p.createdAt = (select max(lp.createdAt) from Profile lp where lp.userId.id = :luserId)", Profile.class)
                    .setParameter("userId", user.getId())
                    .setParameter("luserId", user.getId())
                    .getResultList();

            emfHandller.closeEntityManager(em);

            return list;

        } catch (Exception e) {

            e.printStackTrace();

            emfHandller.closeEntityManager(em);

            return null;

        }
    }

    // adding percentage
    public LearnObjCritPres addLearningObjectPersentage(LearnObjCritPres loCriPer) {
        EntityManager em = emfHandller.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(loCriPer);
            em.getTransaction().commit();
            emfHandller.closeEntityManager(em);
            return loCriPer;
        } catch (Exception e) {
            e.printStackTrace();
            emfHandller.closeEntityManager(em);
            return null;
        }
    }

    public List<LearnObjCritPres> getLOCriPerByLO(int id) {
        EntityManager em = emfHandller.getEntityManager();
        try {
            List<LearnObjCritPres> list = em.createNamedQuery("LearnObjCritPres.findByLearningObj", LearnObjCritPres.class).setParameter("id", id).getResultList();
            emfHandller.closeEntityManager(em);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            emfHandller.closeEntityManager(em);
            return null;
        }
    }
}
