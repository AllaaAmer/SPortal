/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.dlayer.Dao;
import com.dlayer.entities.Criteria;
import com.dlayer.entities.LearningObject;
import com.dlayer.entities.OwlRules;
import com.dlayer.entities.Profile;
import com.dlayer.entities.ProfileDetails;
import com.dlayer.entities.TransactionData;
import com.dlayer.entities.Users;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.StreamedContent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PreDestroy;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author yaraYaseen
 */
@ViewScoped
@ManagedBean
public class LessonLearnBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    StreamedContent streamedContent;
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("LId");
    boolean pdfFlag = false;
    boolean videoFlag = false;
    File initialFile;
    boolean imageFlag = false;
    boolean audioFlag = false;
    boolean textFlag = false;
    String time;
    LearningObject lo;
    Date instant;
    String url;

    //methods------------------------------------------------------
    @PostConstruct
    public void prepeareMethod() {
        //check the type of learning object
        try {
            instant = new Date();

            lo = dao.getLearningObjByID(reqId);

            if (lo.getText() != null && lo.getText().trim().length() > 0) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

                session.setAttribute("loBytesArray", lo.getText());

                textFlag = true;
            } else {
                String learnObjType = lo.getLearningObjTyId().getLearningObjectType();
                initialFile = new File(lo.getDirPath() + "\\" + lo.getFileName());
                InputStream targetStream = new FileInputStream(initialFile);

                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

                session.setAttribute("loBytesArray", IOUtils.toByteArray(targetStream));

                session.setAttribute("extention", lo.getFileName().split("\\.")[1].trim().toLowerCase());

                textFlag = false;
                if (lo.getFileName().split("\\.")[1].trim().toLowerCase().equals("pdf")) {
                    pdfFlag = true;
                } else {
                    String mimetype = Files.probeContentType(initialFile.toPath());
                    if (mimetype.split("/")[0].equals("image")) {
                        imageFlag = true;
                    } else if (mimetype.split("/")[0].equals("video")) {
                        videoFlag = true;
                    } else if (mimetype.split("/")[0].equals("audio")) {
                        audioFlag = true;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String generateRandomIdForNotCaching() {
        return java.util.UUID.randomUUID().toString();
    }

    public void addTrans() {
        try {
            TransactionData transObj = new TransactionData();
            transObj.setCreatedAt(new Date());
            transObj.setLearningObjectTypeId(lo.getLearningObjTyId());
            transObj.setLessonId(lo.getFolderId().getLessonId());
            transObj.setUserId(userAcc);
            transObj.setTimeSpent((new Date()).getTime() - instant.getTime());
            dao.saveTrans(transObj);
            FacesContext.getCurrentInstance().getExternalContext().redirect("courseTopics.xhtml?EId=" + transObj.getLessonId().getTopicId().getCourseId().getId() + "&faces-redirect=true");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //setters and getters---------------------------
    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public boolean isPdfFlag() {
        return pdfFlag;
    }

    public void setPdfFlag(boolean pdfFlag) {
        this.pdfFlag = pdfFlag;
    }

    public boolean isVideoFlag() {
        return videoFlag;
    }

    public void setVideoFlag(boolean videoFlag) {
        this.videoFlag = videoFlag;
    }

    public File getInitialFile() {
        return initialFile;
    }

    public void setInitialFile(File initialFile) {
        this.initialFile = initialFile;
    }

    public boolean isImageFlag() {
        return imageFlag;
    }

    public void setImageFlag(boolean imageFlag) {
        this.imageFlag = imageFlag;
    }

    public boolean isAudioFlag() {
        return audioFlag;
    }

    public void setAudioFlag(boolean audioFlag) {
        this.audioFlag = audioFlag;
    }

    public boolean isTextFlag() {
        return textFlag;
    }

    public void setTextFlag(boolean textFlag) {
        this.textFlag = textFlag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
