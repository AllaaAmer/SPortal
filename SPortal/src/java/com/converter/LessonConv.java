/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter;

import com.dlayer.entities.Lesson;
import com.dlayer.entities.Topic;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author yaraYaseen
 */
@FacesConverter(value = "LessonConv")
public class LessonConv implements Converter {

    public List<Lesson> list;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (Lesson obj : list) {
            if (obj.getLessonName().equals(value)) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((Lesson) value).getLessonName();
        } else {
            return "";
        }

    }
}
