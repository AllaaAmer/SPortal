/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "PAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Page.findAll", query = "SELECT p FROM Page p")
    , @NamedQuery(name = "Page.findById", query = "SELECT p FROM Page p WHERE p.id = :id")
    , @NamedQuery(name = "Page.findByPageUrl", query = "SELECT p FROM Page p WHERE p.pageUrl = :pageUrl")})
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PAGE_URL")
    private String pageUrl;
    @OneToMany(mappedBy = "pageId")
    private List<RolePriviledge> rolePriviledgeList;
    @Column(name = "PAGE_DESC")
    private String pageDesc;

    public Page() {
    }

    public Page(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageDesc() {
        return pageDesc;
    }

    public void setPageDesc(String pageDesc) {
        this.pageDesc = pageDesc;
    }

    @XmlTransient
    public List<RolePriviledge> getRolePriviledgeList() {
        return rolePriviledgeList;
    }

    public void setRolePriviledgeList(List<RolePriviledge> rolePriviledgeList) {
        this.rolePriviledgeList = rolePriviledgeList;
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
        if (!(object instanceof Page)) {
            return false;
        }
        Page other = (Page) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.Page[ id=" + id + " ]";
    }

}
