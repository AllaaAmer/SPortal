/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.dlayer.Dao;
import com.dlayer.entities.Criteria;
import com.dlayer.entities.LearningObjectType;
import com.dlayer.entities.OwlRules;
import com.dlayer.entities.RolePriviledge;
import com.dlayer.entities.Users;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

/**
 *
 * @author yaraYaseen
 */
@ViewScoped
@ManagedBean
public class RuleBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    RolePriviledge rulePriv;
    String entityId;
    Users user = new Users();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
    List<OwlRules> rulesList = new ArrayList<>();

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

            rulesList = dao.searchRulesFromDB();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            dao.saveRuleDB((OwlRules) event.getObject());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rules processed succefully ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while processing rules", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    public void onRowCancel(RowEditEvent event) {

    }

    //setters and getters--------------------------------------------------------------------------
    public List<OwlRules> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<OwlRules> rulesList) {
        this.rulesList = rulesList;
    }

}
