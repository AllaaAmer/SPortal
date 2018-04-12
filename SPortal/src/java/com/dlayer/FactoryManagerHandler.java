/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author yaraYaseen
 */
public class FactoryManagerHandler {

    public EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SPortalPU");
        return emf.createEntityManager();
    }

    public void closeEntityManager(EntityManager em) {
        EntityManagerFactory emf = em.getEntityManagerFactory();
        em.close();
        emf.close();
    }
}
