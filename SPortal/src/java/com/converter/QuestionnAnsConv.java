/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter;

import com.dlayer.entities.QuestionairAnswer;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author yaraYaseen
 */
@FacesConverter(value = "QuestionnConv")
public class QuestionnAnsConv implements Converter {

    public List<QuestionairAnswer> list;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (QuestionairAnswer sem : list) {
            if (sem.getChoice().equals(value)) {
                return sem;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((QuestionairAnswer) value).getChoice();
    }

}
