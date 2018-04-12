/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter;

import com.dlayer.entities.Course;
import com.dlayer.entities.LearningObjectType;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author yaraYaseen
 */
@FacesConverter(value = "LearnObjTypeCon")
public class LearnObjTypeCon implements Converter {

    public List<LearningObjectType> list;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (LearningObjectType obj : list) {
            if (obj.getLearningObjectType().equals(value)) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((LearningObjectType) value).getLearningObjectType();
        } else {
            return "";
        }

    }
}
