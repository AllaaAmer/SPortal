/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.converter.SemConv;
import com.dlayer.Dao;
import com.dlayer.entities.Course;
import com.dlayer.entities.Criteria;
import com.dlayer.entities.Instructor;
import com.dlayer.entities.LearningObjectType;
import com.dlayer.entities.OwlRules;
import com.dlayer.entities.RolePriviledge;
import com.dlayer.entities.Semester;
import com.dlayer.entities.Users;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
public class OwlRuleBN {

    Dao dao = new Dao();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Users userAcc = (Users) request.getSession().getAttribute("loggedUser");
    List<RolePriviledge> rolePrivList = new ArrayList<>();
    RolePriviledge rulePriv;
    String entityId;
    Users user = new Users();
    String reqId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
    boolean editFlag = false;
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

            readFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void readFile() {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            File file = new File(dao.getSettingByDesc("OWL_FILE_PATH").getValue());
            OWLOntology myontology = manager.loadOntologyFromOntologyDocument(file);
            Set<OWLClass> myclasses = myontology.getClassesInSignature();

            ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
            OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
            OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
            OWLReasoner reasoner = reasonerFactory.createReasoner(myontology, config);
            OWLDataFactory factory = manager.getOWLDataFactory();

            IRI iri = null;
            for (OWLClass c : myclasses) {
                if (c.getIRI().getFragment().equals("criteria")) {
                    iri = c.getIRI();
                }
            }

            OWLClass criteria = factory.getOWLClass(iri);
            NodeSet<OWLClass> subClses = reasoner.getSubClasses(criteria, true);
            for (OWLClass sc : subClses.getFlattened()) {
                Set<OWLClassAxiom> tempAx = myontology.getAxioms(sc);
                for (OWLClassAxiom ax : tempAx) {
                    for (OWLClassExpression nce : ax.getNestedClassExpressions()) {
                        if (nce.getClassExpressionType() != ClassExpressionType.OWL_CLASS) {

                            OwlRules ruleObj = new OwlRules();

                            LearningObjectType lObj = dao.getLearnObjTypeByValue(ax.toString().split("#")[3].split(">")[0]);
                            if (lObj != null) {
                                ruleObj.setLearningObjectTypeId(lObj);
                            } else {
                                LearningObjectType learnObj = new LearningObjectType();
                                learnObj.setLearningObjectType(ax.toString().split("#")[3].split(">")[0]);
                                ruleObj.setLearningObjectTypeId(learnObj);
                            }

                            Criteria criteriaDB = dao.getCriteriaByValue(ax.toString().split("#")[1].split(">")[0]);
                            if (criteriaDB != null) {
                                ruleObj.setCriteriaId(criteriaDB);
                            } else {
                                Criteria cc = new Criteria();
                                cc.setCriteria(ax.toString().split("#")[1].split(">")[0]);
                                ruleObj.setCriteriaId(cc);
                            }

                            ruleObj.setDescription(ax.toString().split("#")[2].split(">")[0]);

                            rulesList.add(ruleObj);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveRules() {
        try {
            dao.saveOwlRules(rulesList);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rules processed succefully ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while processing rules", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
        }
    }

    //setters and getters--------------------------------------------------------------------------
    public List<OwlRules> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<OwlRules> rulesList) {
        this.rulesList = rulesList;
    }

}
