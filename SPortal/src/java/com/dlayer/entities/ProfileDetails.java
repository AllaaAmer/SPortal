/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "PROFILE_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProfileDetails.findAll", query = "SELECT p FROM ProfileDetails p")
    , @NamedQuery(name = "ProfileDetails.findById", query = "SELECT p FROM ProfileDetails p WHERE p.id = :id")
    , @NamedQuery(name = "ProfileDetails.findByStyle", query = "SELECT p FROM ProfileDetails p WHERE p.criteria = :criteria")
    , @NamedQuery(name = "ProfileDetails.findByPercentage", query = "SELECT p FROM ProfileDetails p WHERE p.percentage = :percentage")})
public class ProfileDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CRITERIA")
    private String criteria;
     //new
    @Column(name = "PERCENTAGE")
    private float percentage;
    @JoinColumn(name = "PROFILE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Profile profileId;

    public ProfileDetails() {
    }

    public ProfileDetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public Profile getProfileId() {
        return profileId;
    }

    public void setProfileId(Profile profileId) {
        this.profileId = profileId;
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
        if (!(object instanceof ProfileDetails)) {
            return false;
        }
        ProfileDetails other = (ProfileDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProfileDetails{" + "id=" + id + ", criteria=" + criteria + ", percentage=" + percentage + ", profileId=" + profileId + '}';
    }

}
