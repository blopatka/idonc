/*
 * UserConverter.java
 *
 * Created on 21 styczeñ 2007, 21:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package entities;

import entities.User;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Bartek
 */
public class UserConverter implements Converter {
    
    /** Creates a new instance of UserConverter */
    public UserConverter() {
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) {
        if (string == null) {
            return null;
        }
        Integer id = new Integer(string);
        UserController controller = (UserController) facesContext.getApplication().getELResolver().getValue(
            facesContext.getELContext(), null, "user");
        
        return controller.findUser(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null) {
            return null;
        }
        if(object instanceof User) {
            User o = (User) object;
            return "" + o.getId();
        } else {
            throw new IllegalArgumentException("object:" + object + " of type:" + object.getClass().getName() + "; expected type: entities.user.User");
        }
    }
    
}
