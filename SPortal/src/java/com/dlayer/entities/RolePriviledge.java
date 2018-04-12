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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaraYaseen
 */
@Entity
@Table(name = "ROLE_PRIVILEDGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolePriviledge.findAll", query = "SELECT r FROM RolePriviledge r")
    , @NamedQuery(name = "RolePriviledge.findById", query = "SELECT r FROM RolePriviledge r WHERE r.id = :id")
    , @NamedQuery(name = "RolePriviledge.findByCanRead", query = "SELECT r FROM RolePriviledge r WHERE r.canRead = :canRead")
    , @NamedQuery(name = "RolePriviledge.findByCanUpdate", query = "SELECT r FROM RolePriviledge r WHERE r.canUpdate = :canUpdate")
    , @NamedQuery(name = "RolePriviledge.findByCanDelete", query = "SELECT r FROM RolePriviledge r WHERE r.canDelete = :canDelete")
    , @NamedQuery(name = "RolePriviledge.findByCanCreate", query = "SELECT r FROM RolePriviledge r WHERE r.canCreate = :canCreate")
    , @NamedQuery(name = "RolePriviledge.findByUserIdEntityId", query = "SELECT r FROM RolePriviledge r WHERE r.roleId.id = :roleId and r.pageId.id = :pageId")
    , @NamedQuery(name = "RolePriviledge.findByRole", query = "SELECT r FROM RolePriviledge r WHERE r.roleId.id = :roleId")})
public class RolePriviledge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 1)
    @Column(name = "CAN_READ")
    private String canRead;
    @Size(max = 1)
    @Column(name = "CAN_UPDATE")
    private String canUpdate;
    @Size(max = 1)
    @Column(name = "CAN_DELETE")
    private String canDelete;
    @Size(max = 1)
    @Column(name = "CAN_CREATE")
    private String canCreate;
    @JoinColumn(name = "PAGE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Page pageId;
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Role roleId;

    public RolePriviledge() {
    }

    public RolePriviledge(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCanRead() {
        return canRead;
    }

    public void setCanRead(String canRead) {
        this.canRead = canRead;
    }

    public String getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(String canUpdate) {
        this.canUpdate = canUpdate;
    }

    public String getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(String canDelete) {
        this.canDelete = canDelete;
    }

    public String getCanCreate() {
        return canCreate;
    }

    public void setCanCreate(String canCreate) {
        this.canCreate = canCreate;
    }

    public Page getPageId() {
        return pageId;
    }

    public void setPageId(Page pageId) {
        this.pageId = pageId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
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
        if (!(object instanceof RolePriviledge)) {
            return false;
        }
        RolePriviledge other = (RolePriviledge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dlayer.entities.RolePriviledge[ id=" + id + " ]";
    }

}
