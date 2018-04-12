/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.dlayer.Dao;
import com.dlayer.entities.Course;
import com.dlayer.entities.Criteria;
import com.dlayer.entities.LearnObjCritPres;
import com.dlayer.entities.LearnObjTrans;
import com.dlayer.entities.LearningObject;
import com.dlayer.entities.Lesson;
import com.dlayer.entities.LessonFolders;
import com.dlayer.entities.OwlRules;
import com.dlayer.entities.Profile;
import com.dlayer.entities.ProfileDetails;
import com.dlayer.entities.Topic;
import com.dlayer.entities.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
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
public class StudBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    List<Course> coursesList = new ArrayList<>();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("EId");
    List<Lesson> lessonList = new ArrayList<>();
    List<Topic> topicList = new ArrayList<>();
    Course selectedCourse = new Course();
    List<LessonFolders> lessonFoldersList = new ArrayList<>();
    boolean folderFlag = false;
    //new
    Lesson selectedLesson = new Lesson();

    List<LearnObjCritPres> loCPercentages = new ArrayList<>();
    List<List<LearnObjCritPres>> loCPercentagesList = new ArrayList<>();
    List<LearningObject> allLearningObjects = new ArrayList<>();

    //methods------------------------------------------------------
    @PostConstruct
    public void prepeareMethod() {
        try {
            if (!dao.getStudentByUserId(userAcc.getId()).isQuestionaireFlag()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
            }
            Course course = new Course();
            course.setSemesterId(userAcc.getStudent().getSemester());
            coursesList = dao.searchCourse(course);

            if (reqId != null) {
                selectedCourse.setId(Integer.valueOf(reqId));
                selectedCourse = dao.getCourseById(selectedCourse);
            }

            allLearningObjects = dao.getLearningObjs();
            for (LearningObject lo : allLearningObjects) {
                loCPercentages = dao.getLOCriPerByLO(lo.getId());
                loCPercentagesList.add(loCPercentages);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getLearningOBj(Lesson obj) {
        try {
            //new
            lessonFoldersList = new ArrayList<>();
            selectedLesson = obj;
            List<LessonFolders> lessonFolList = dao.getFoldersByLessonId(obj);
            List<LearnObjTrans> history = dao.getLearnObjTranByLesson(obj, userAcc);

            System.out.println("history in ths same lesson ive been there before : " + history);

            if (history != null && history.size() > 0) { //same lesson same course
                for (LessonFolders lessF : lessonFolList) {
                    for (LearnObjTrans his : history) {
                        if (lessF.getLearningObjectList().getId() == his.getLearningObjId().getId()) {
                            lessonFoldersList.add(lessF);
                        }
                    }
                }
                folderFlag = true;
            } else {
                System.out.println("this is a new lesson has no transc before!!");
                folderFlag = true;

                history = dao.getLearnObjTranByUser(obj, userAcc);

                System.out.println("history trans per User: " + history);

                if (history != null && history.size() > 0) { // same course , not lesson 1
                    List<Profile> proList = userAcc.getProfileList();
                    List<ProfileDetails> proDetList = new ArrayList<>();
                    for (Profile p : proList) {
                        for (ProfileDetails pd : p.getProfileDetailsList()) {
                            proDetList.add(pd);
                        }
                    }

                    // get the total precentage for the algorithm
                    float totalPercentage = 0;
                    for (ProfileDetails profileDetails : proDetList) {
                        totalPercentage += profileDetails.getPercentage();
                    }
                    System.out.println("total presentage: " + totalPercentage);

                    List<LoPer> learnObjtPerc = new ArrayList<>();
                    for (ProfileDetails pdl : proDetList) {
                        List<Integer> list = dao.getLearnObjTypeByCri(pdl.getCriteria(), obj);
                        for (Integer learnObj : list) {
                            LoPer lp = new LoPer();
                            lp.setLo(learnObj);
                            float per = pdl.getPercentage() / list.size();
                            lp.setPercent(per);
                            lp.setLearnObjType(dao.getLearnObjById(learnObj).getLearningObjTyId().getId());
                            learnObjtPerc.add(lp);
                        }
                    }

                    for (LoPer lop : learnObjtPerc) {
                        for (LearnObjTrans lot : history) {
                            if (lop.getLearnObjType() == lot.getLearningObjTypeId().getId()) {
                                lop.setPercent(lot.getPercentage());
                            }
                        }
                    }

                    int lessonCount = dao.countLesson(obj.getTopicId());
                    float displayedPercentage = totalPercentage / lessonCount;

                    float maxPercent = learnObjtPerc.get(0).getPercent();
                    for (LoPer learnObcPer : learnObjtPerc) {
                        if (learnObcPer.getPercent() > maxPercent) {
                            maxPercent = learnObcPer.getPercent();
                        }
                    }

                    for (LoPer learnObcPer : learnObjtPerc) {
                        if (learnObcPer.getPercent() == maxPercent) {
                            if (Math.signum(maxPercent - displayedPercentage) < -1) {
                                learnObcPer.setPercent(0);
                            } else {
                                learnObcPer.setPercent(maxPercent - displayedPercentage);
                            }
                            break;
                        }
                    }

                    for (LoPer learnObcPer : learnObjtPerc) {
                        LearnObjTrans learnObjTrans = new LearnObjTrans();
                        learnObjTrans.setCreatedAt(new Date());
                        learnObjTrans.setLearningObjId(dao.getLearnObjById(learnObcPer.getLo()));
                        learnObjTrans.setLessonId(obj);
                        learnObjTrans.setPercentage(learnObcPer.getPercent());
                        learnObjTrans.setUserId(userAcc);
                        learnObjTrans.setLearningObjTypeId(dao.getLearnObjById(learnObcPer.getLo()).getLearningObjTyId());
                        learnObjTrans.setTopicId(obj.getTopicId());
                        learnObjTrans.setLessonSequence(obj.getSequence());
                        dao.saveLearnObjTrans(learnObjTrans);
                    }

                    for (LoPer learnObcPer : learnObjtPerc) {
                        for (LessonFolders lf : lessonFolList) {
                            if (learnObcPer.getPercent() != 0) {
                                if (lf.getLearningObjectList() != null) {
                                    if (learnObcPer.getLo() == lf.getLearningObjectList().getId()) {
                                        lessonFoldersList.add(lf);
                                    }
                                }
                            }
                        }
                    }

                } else {
                    //new student or new topic
                    System.out.println("this a new student or a new topic!!");

                    //get max profile
                    List<Profile> proList = dao.getLastProfileByUser(userAcc);
                    List<ProfileDetails> proDetList = new ArrayList<>();
                    for (Profile p : proList) {
                        for (ProfileDetails pd : p.getProfileDetailsList()) {
                            proDetList.add(pd);
                        }
                    }
                    // get the similar LearningObjects
                    getLearningObjectsSimilarToProfile(loCPercentagesList, proDetList);

                    // get the total precentage to use for the algorithm
                    float totalPercentage = 0;
                    for (ProfileDetails profileDetails : proDetList) {
                        totalPercentage += profileDetails.getPercentage();
                    }
                    System.out.println("total presentage: " + totalPercentage);

                    List<LoPer> learnObjtPerc = new ArrayList<>();
                    for (ProfileDetails pdl : proDetList) {
                        List<Integer> list = dao.getLearnObjTypeByCri(pdl.getCriteria(), obj);
                        for (Integer learnObj : list) {
                            LoPer lp = new LoPer();
                            lp.setLo(learnObj);
                            float per = pdl.getPercentage() / list.size();
                            lp.setPercent(per);
                            lp.setLearnObjType(dao.getLearnObjById(learnObj).getLearningObjTyId().getId());
                            learnObjtPerc.add(lp);
                        }
                    }

                    //count criteria percentage from profile details
                    int lessonCount = dao.countLesson(obj.getTopicId());
                    float displayedPercentage = totalPercentage / lessonCount; // 

                    float maxPercent = learnObjtPerc.get(0).getPercent();
                    for (LoPer learnObcPer : learnObjtPerc) {
                        if (learnObcPer.getPercent() > maxPercent) {
                            maxPercent = learnObcPer.getPercent();
                        }
                    }

                    for (LoPer learnObcPer : learnObjtPerc) {
                        if (learnObcPer.getPercent() == maxPercent) {
                            if (Math.signum(maxPercent - displayedPercentage) < -1) {
                                learnObcPer.setPercent(0);
                            } else {
                                learnObcPer.setPercent(maxPercent - displayedPercentage);
                            }
                            break;
                        }
                    }

                    for (LoPer learnObcPer : learnObjtPerc) {
                        LearnObjTrans learnObjTrans = new LearnObjTrans();
                        learnObjTrans.setCreatedAt(new Date());
                        learnObjTrans.setLearningObjId(dao.getLearnObjById(learnObcPer.getLo()));
                        learnObjTrans.setLessonId(obj);
                        learnObjTrans.setPercentage(learnObcPer.getPercent());
                        learnObjTrans.setUserId(userAcc);
                        learnObjTrans.setLearningObjTypeId(dao.getLearnObjById(learnObcPer.getLo()).getLearningObjTyId());
                        learnObjTrans.setTopicId(obj.getTopicId());
                        learnObjTrans.setLessonSequence(obj.getSequence());
                        dao.saveLearnObjTrans(learnObjTrans);
                    }

                    // hena hn3mel l similraty 
                    // hangeb l cratria bta3et l lea
                    for (LoPer learnObcPer : learnObjtPerc) {
                        for (LessonFolders lf : lessonFolList) {
                            if (learnObcPer.getPercent() != 0) {
                                if (learnObcPer.getLo() == lf.getLearningObjectList().getId()) {
                                    lessonFoldersList.add(lf);
                                }
                            }
                        }
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getLearningObjectsSimilarToProfile(List<List<LearnObjCritPres>> loPercentagesList, List<ProfileDetails> profileCriList) {
        for (List<LearnObjCritPres> loListPreList : loPercentagesList) {
            if (!loListPreList.isEmpty()) {
                System.err.println("loListPreList: " + loListPreList);
                System.err.println("profile: " + profileCriList);

                // should be a while here and reapte
                // get max critira percentage
                float maxPercent = profileCriList.get(0).getPercentage();
                String maxCritira = profileCriList.get(0).getCriteria();
                for (ProfileDetails profileDetails : profileCriList) {
                    if (profileDetails.getPercentage() > maxPercent) {
                        maxPercent = profileDetails.getPercentage();
                        maxCritira = profileDetails.getCriteria();
                    }
                }
                List<LearningObject> similarLO = new ArrayList<>();
                // get the critria of the max precentage
                for (LearnObjCritPres loCPer : loListPreList) {
                    if (maxCritira.equals(loCPer.getCriteriaId().getCriteria())) {
                        if (maxPercent < (loCPer.getPrecentage() + 10) && maxPercent > (loCPer.getPrecentage() - 10)) {
                            similarLO.add(loCPer.getLearningObgId());
                        }
                    }
                }

            }
        }
    }

//new
    public void updateProfile() {
        List<TransactionClass> transList = new ArrayList<>();
        List<Object[]> list = dao.getTimeSpent(selectedLesson.getTopicId(), userAcc);
        for (Object[] s : list) {
            List<OwlRules> owlRulesList = dao.getCriByLearnObjType(Integer.valueOf((s[1]).toString()));
            for (OwlRules o : owlRulesList) {
                TransactionClass trans = new TransactionClass();
                trans.setCriteria(o.getCriteriaId());
                trans.setValue((long) o.getValue());
                trans.setTimeSpent(Long.valueOf((s[2]).toString()) / owlRulesList.size()); // >>>>>>>>???????
                transList.add(trans);
            }
        }
        List<TransactionClass> finalList = new ArrayList<>();
        List<Criteria> criteriaList = dao.getAllCriteria();
        for (Criteria c : criteriaList) {
            TransactionClass trans = new TransactionClass();
            for (TransactionClass tc : transList) {
                if (c.getId() == tc.getCriteria().getId()) {
                    trans.setCriteria(c);
                    trans.setValue(tc.getValue());
                    trans.setTimeSpent(TimeUnit.MILLISECONDS.toMinutes(trans.getTimeSpent() + tc.getTimeSpent())); /// trans.getTimeSpent() is empty
                }
            }
            finalList.add(trans);
        }

        //update profile
        List<ProfileDetails> profilDetailList = new ArrayList<>();
        for (TransactionClass tc : finalList) {
            ProfileDetails profilDetail = new ProfileDetails();
            profilDetail.setCriteria(tc.getCriteria().getCriteria());
            profilDetail.setPercentage((((float) tc.getTimeSpent() / tc.getValue()) * 100));
            profilDetailList.add(profilDetail);
        }

        Profile newProfile = new Profile();
        newProfile.setProfileDetailsList(profilDetailList);
        newProfile.setCreatedAt(new Date());
        newProfile.setUserId(userAcc);
        newProfile.setProfileDetailsList(profilDetailList);
        dao.addProfile(newProfile);

    }

//setters and getters---------------------------
    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
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

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public List<LessonFolders> getLessonFoldersList() {
        return lessonFoldersList;
    }

    public void setLessonFoldersList(List<LessonFolders> lessonFoldersList) {
        this.lessonFoldersList = lessonFoldersList;
    }

    public boolean isFolderFlag() {
        return folderFlag;
    }

    public void setFolderFlag(boolean folderFlag) {
        this.folderFlag = folderFlag;
    }

}
