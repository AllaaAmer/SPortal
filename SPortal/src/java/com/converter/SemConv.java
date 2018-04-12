/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter;

import com.dlayer.entities.Semester;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author yaraYaseen
 */
@FacesConverter(value = "SemConv")
public class SemConv implements Converter {

    public List<Semester> list;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (Semester sem : list) {
            if (sem.getSemesterValue().equals(value)) {
                return sem;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((Semester) value).getSemesterValue();
        } else {
            return null;
        }
    }

}
