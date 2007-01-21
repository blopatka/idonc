/*
 * TeamConverter.java
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
public class TeamConverter implements Converter {
    
    /** Creates a new instance of TeamConverter */
    public TeamConverter() {
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) {
        if (string == null) {
            return null;
        }
        Integer id = new Integer(string);
        TeamController controller = (TeamController) facesContext.getApplication().getELResolver().getValue(
            facesContext.getELContext(), null, "team");
        
        return controller.findTeam(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null) {
            return null;
        }
        if(object instanceof Team) {
            Team o = (Team) object;
            return "" + o.getId();
        } else {
            throw new IllegalArgumentException("object:" + object + " of type:" + object.getClass().getName() + "; expected type: entites.team.Team");
        }
    }
    
}
