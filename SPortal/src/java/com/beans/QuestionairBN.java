/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.converter.QuestionnAnsConv;
import com.dlayer.Dao;
import com.dlayer.entities.Criteria;
import com.dlayer.entities.Dimention;
import com.dlayer.entities.Profile;
import com.dlayer.entities.ProfileDetails;
import com.dlayer.entities.QuestionairAnswer;
import com.dlayer.entities.Questionaire;
import com.dlayer.entities.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author yaraYaseen
 */
@ViewScoped
@ManagedBean
public class QuestionairBN {

    Dao dao = new Dao();
    List<Questionaire> questionnaireList;
    List<QuestionairAnswer> answersList;
    QuestionairAnswer selectedAnswer;
    private int totalRows;

    // Paging.
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;
    List<Criteria> cList;
    List<Dimention> dList;
    List<Criteria> answerL = new ArrayList<>();
    Set<Criteria> cL = new HashSet<>();
    List<String> fullAnswerL;
    Questionaire ques = new Questionaire();
    Set<String[]> dimSizList = new HashSet<>();
    Set<String> dimList = new HashSet<>();
    Profile profile = new Profile();
    QuestionnAnsConv quesConv = new QuestionnAnsConv();
    List<QuestionairAnswer> myAnswers = new ArrayList<>();
    int count = 0;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    Student student;
    Map<Criteria, Integer> qa;

    //Methods----------------------------------
    @PostConstruct
    public void prerpMethod() {

        rowsPerPage = 1; // Default rows per page (max amount of rows to be displayed at once).
        pageRange = 1; // Default page range (max amount of page links to be displayed at once).

        questionnaireList = dao.getAllQuestionnaireQues();

        Collections.shuffle(questionnaireList);

        ques = questionnaireList.get(0);

        student = (Student) session.getAttribute("regStudent");

//        for (QuestionairAnswer qa : ques.getQuestionairAnswerList()) {
//            cL.add(qa.getCriteriaID());
//        }

        quesConv.list = ques.getQuestionairAnswerList();

        for (Questionaire q : questionnaireList) {
            dimList.add(q.getDimentionId().getDimention());
        }

        for (String d : dimList) {
            String[] s = new String[2];
            s[0] = d;
            s[1] = "0";
            dimSizList.add(s);
        }

        for (Questionaire q : questionnaireList) {
            for (String[] mySet : dimSizList) {
                if (mySet[0].equals(q.getDimentionId().getDimention())) {
                    mySet[1] = String.valueOf(Integer.valueOf(mySet[1]) + 1);
                }
            }
        }
    }

    public void pageNext() {
        page(firstRow + rowsPerPage);
        getProfile();
    }

    public void pagePrevious() {
        page(firstRow - rowsPerPage);
    }

    private void page(int firstRow) {
        this.firstRow = firstRow;
    }

    public void getProfile() {

        // add new answer to answers list
        myAnswers.add(selectedAnswer); // 1

        int size = 0;
        boolean maxFlag = false;
        count = 0;

//        // count = number of answers per criteria
        for (QuestionairAnswer qa : myAnswers) { // 2n
            if (qa.getCriteriaID() == selectedAnswer.getCriteriaID()) { //1
                count++; //1
            }
        }

        //size of questions in this dimention
        size = selectedAnswer.getQuestionId().getDimentionId().getQuestionaireList().size(); //1

        try {
            if (firstRow == questionnaireList.size()) {//1
                student.setQuestionaireFlag(true);//1
                dao.updateStudent(student);//1
                maxFlag = true;//1
                FacesContext.getCurrentInstance().getExternalContext().redirect("studentHome.xhtml");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (firstRow == size) {//1
            //why we dont cut the list at 50 %???
            if (count == (size * 0.50)) {//1
                ProfileDetails pd = new ProfileDetails();//1
                pd.setPercentage(50);//1
                pd.setCriteria(selectedAnswer.getCriteriaID().getCriteria());//1
                List<ProfileDetails> pdL = profile.getProfileDetailsList();//1
                if (pdL == null) {//1
                    pdL = new ArrayList<>();//1
                }
                pdL.add(pd);//1
                profile.setProfileDetailsList(pdL);//1
                profile.setUserId(student.getUserId());//1
                profile.setCreatedAt(new Date());
                dao.addProfile(profile);//1
            } else {
                if (!maxFlag) {//1
                    ques = questionnaireList.get(firstRow);//1
                    quesConv.list = ques.getQuestionairAnswerList();//1
                }
            }
        }

        if (count >= (size
                * 0.60)) {//1
            if (!maxFlag) {//1
                questionnaireList = questionnaireList.subList(size, questionnaireList.size());//1
                ques = questionnaireList.get(0);//1
                quesConv.list = ques.getQuestionairAnswerList();//1
                myAnswers = new ArrayList<>();//1
                count = 0; //1
                firstRow = 0;//1
                ProfileDetails pd = new ProfileDetails();//1

                pd.setPercentage(100);//1

                pd.setCriteria(selectedAnswer.getCriteriaID().getCriteria());//1
                List<ProfileDetails> pdL = new ArrayList<>();//1
                pdL.add(pd);//1
                profile.setStyle(selectedAnswer.getQuestionId().getDimentionId().getDimention());//1
                profile.setProfileDetailsList(pdL);//1
                profile.setUserId(student.getUserId());//1
                dao.addProfile(profile); //2n+5
            }
        } else {
            if (!maxFlag) {//1
                ques = questionnaireList.get(firstRow);//1
                quesConv.list = ques.getQuestionairAnswerList();//1
            }
        }

    }

//setters and getters-----------------------
    public int getTotalRows() {
        return totalRows;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public Integer[] getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public List<Questionaire> getQuestionnaireList() {
        return questionnaireList;
    }

    public void setQuestionnaireList(List<Questionaire> questionnaireList) {
        this.questionnaireList = questionnaireList;
    }

    public QuestionairAnswer getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(QuestionairAnswer selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public Questionaire getQues() {
        return ques;
    }

    public void setQues(Questionaire ques) {
        this.ques = ques;
    }

    public QuestionnAnsConv getQuesConv() {
        return quesConv;
    }

    public void setQuesConv(QuestionnAnsConv quesConv) {
        this.quesConv = quesConv;
    }

}
