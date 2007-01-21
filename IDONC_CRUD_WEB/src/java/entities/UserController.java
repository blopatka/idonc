/*
 * UserController.java
 *
 * Created on 21 styczeñ 2007, 21:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package entities;

import entities.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author Bartek
 */
public class UserController {
    
    /** Creates a new instance of UserController */
    public UserController() {
    }

    private User user;

    private DataModel model;

    @Resource
    private UserTransaction utx;

    @PersistenceUnit(unitName = "IDONC_CRUD_WEBPU")
    private EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private int batchSize = 20;

    private int firstItem = 0;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DataModel getDetailUsers() {
        return model;
    }

    public void setDetailUsers(Collection<User> m) {
        model = new ListDataModel(new ArrayList(m));
    }

    public String createSetup() {
        this.user = new User();
        return "user_create";
    }

    public String create() {
        EntityManager em = getEntityManager();
        try {
            utx.begin();
            em.persist(user);
            utx.commit();
            addSuccessMessage("User was successfully created.");
        } catch (Exception ex) {
            try {
                addErrorMessage(ex.getLocalizedMessage());
                utx.rollback();
            } catch (Exception e) {
                addErrorMessage(e.getLocalizedMessage());
            }
        } finally {
            em.close();
        }
        return "user_list";
    }

    public String detailSetup() {
        setUserFromRequestParam();
        return "user_detail";
    }

    public String editSetup() {
        setUserFromRequestParam();
        return "user_edit";
    }

    public String edit() {
        EntityManager em = getEntityManager();
        try {
            utx.begin();
            user = em.merge(user);
            utx.commit();
            addSuccessMessage("User was successfully updated.");
        } catch (Exception ex) {
            try {
                addErrorMessage(ex.getLocalizedMessage());
                utx.rollback();
            } catch (Exception e) {
                addErrorMessage(e.getLocalizedMessage());
            }
        } finally {
            em.close();
        }
        return "user_list";
    }

    public String destroy() {
        EntityManager em = getEntityManager();
        try {
            utx.begin();
            User user = getUserFromRequestParam();
            user = em.merge(user);
            em.remove(user);
            utx.commit();
            addSuccessMessage("User was successfully deleted.");
        } catch (Exception ex) {
            try {
                addErrorMessage(ex.getLocalizedMessage());
                utx.rollback();
            } catch (Exception e) {
                addErrorMessage(e.getLocalizedMessage());
            }
        } finally {
            em.close();
        }
        return "user_list";
    }

    public User getUserFromRequestParam() {
        EntityManager em = getEntityManager();
        try{
            User o = (User) model.getRowData();
            o = em.merge(o);
            return o;
        } finally {
            em.close();
        }
    }

    public void setUserFromRequestParam() {
        User user = getUserFromRequestParam();
        setUser(user);
    }

    public DataModel getUsers() {
        EntityManager em = getEntityManager();
        try{
            Query q = em.createQuery("select object(o) from User as o");
            q.setMaxResults(batchSize);
            q.setFirstResult(firstItem);
            model = new ListDataModel(q.getResultList());
            return model;
        } finally {
            em.close();
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("successInfo", facesMsg);
    }

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try{
            User o = (User) em.find(User.class, id);
            return o;
        } finally {
            em.close();
        }
    }

    public javax.faces.model.SelectItem[] getAddressIds() {
        EntityManager em = getEntityManager();
        try{
            List <Address> l = (List <Address>) em.createQuery("select o from Address as o").getResultList();
            SelectItem select[] = new SelectItem[l.size()];
            int i = 0;
            for(Address x : l) {
                    select[i++] = new SelectItem(x);
                }
                return select;
        } finally {
            em.close();
        }
    }

    public javax.faces.model.SelectItem[] getTeamIds() {
        EntityManager em = getEntityManager();
        try{
            List <Team> l = (List <Team>) em.createQuery("select o from Team as o").getResultList();
            SelectItem select[] = new SelectItem[l.size()];
            int i = 0;
            for(Team x : l) {
                    select[i++] = new SelectItem(x);
                }
                return select;
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try{
            int count = ((Long) em.createQuery("select count(o) from User as o").getSingleResult()).intValue();
            return count;
        } finally {
            em.close();
        }
    }

    public int getFirstItem() {
        return firstItem;
    }

    public int getLastItem() {
        int size = getItemCount();
        return firstItem + batchSize > size ? size : firstItem + batchSize;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public String next() {
        if (firstItem + batchSize < getItemCount()) {
            firstItem += batchSize;
        }
        return "user_list";
    }

    public String prev() {
        firstItem -= batchSize;
        if (firstItem < 0) {
            firstItem = 0;
        }
        return "user_list";
    }
    
}
