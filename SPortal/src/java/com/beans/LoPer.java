/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.dlayer.entities.LearningObject;
import java.io.Serializable;

/**
 *
 * @author yaraYaseen
 */
public class LoPer implements Serializable{

    int lo; // id
    float percent; // 
    int learnObjType; 

    public int getLo() {
        return lo;
    }

    public void setLo(int lo) {
        this.lo = lo;
    }

    public int getLearnObjType() {
        return learnObjType;
    }

    public void setLearnObjType(int learnObjType) {
        this.learnObjType = learnObjType;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

}
