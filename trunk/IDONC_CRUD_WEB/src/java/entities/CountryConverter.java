/*
 * CountryConverter.java
 *
 * Created on 21 styczeñ 2007, 21:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package entities;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Bartek
 */
public class CountryConverter implements Converter {
    
    /** Creates a new instance of CountryConverter */
    public CountryConverter() {
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) {
        if (string == null) {
            return null;
        }
        Integer id = new Integer(string);
        CountryController controller = (CountryController) facesContext.getApplication().getELResolver().getValue(
            facesContext.getELContext(), null, "country");
        
        return controller.findCountry(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null) {
            return null;
        }
        if(object instanceof Country) {
            Country o = (Country) object;
            return "" + o.getId();
        } else {
            throw new IllegalArgumentException("object:" + object + " of type:" + object.getClass().getName() + "; expected type: entities.country.Country");
        }
    }
    
}
