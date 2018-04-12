/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter;

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
@FacesConverter(value = "TopicConv")
public class TopicConv implements Converter {

    public List<Topic> list;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (Topic obj : list) {
            if (obj.getTopicName().equals(value)) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((Topic) value).getTopicName();
        } else {
            return "";
        }

    }
}
