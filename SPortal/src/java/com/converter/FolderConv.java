/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter;

import com.dlayer.entities.Course;
import com.dlayer.entities.LessonFolders;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author yaraYaseen
 */
@FacesConverter(value = "FolderConv")
public class FolderConv implements Converter {

    public List<LessonFolders> list;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (LessonFolders obj : list) {
            if (obj.getFolderName().equals(value)) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((LessonFolders) value).getFolderName();
        } else {
            return "";
        }

    }
}
