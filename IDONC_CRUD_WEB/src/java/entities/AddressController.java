/*
 * AddressController.java
 *
 * Created on 21 styczeñ 2007, 21:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package entities;

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
public class AddressController {
    
    /** Creates a new instance of AddressController */
    public AddressController() {
    }

    private Address address;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DataModel getDetailAddresss() {
        return model;
    }

    public void setDetailAddresss(Collection<Address> m) {
        model = new ListDataModel(new ArrayList(m));
    }

    public String createSetup() {
        this.address = new Address();
        return "address_create";
    }

    public String create() {
        EntityManager em = getEntityManager();
        try {
            utx.begin();
            em.persist(address);
            utx.commit();
            addSuccessMessage("Address was successfully created.");
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
        return "address_list";
    }

    public String detailSetup() {
        setAddressFromRequestParam();
        return "address_detail";
    }

    public String editSetup() {
        setAddressFromRequestParam();
        return "address_edit";
    }

    public String edit() {
        EntityManager em = getEntityManager();
        try {
            utx.begin();
            address = em.merge(address);
            utx.commit();
            addSuccessMessage("Address was successfully updated.");
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
        return "address_list";
    }

    public String destroy() {
        EntityManager em = getEntityManager();
        try {
            utx.begin();
            Address address = getAddressFromRequestParam();
            address = em.merge(address);
            em.remove(address);
            utx.commit();
            addSuccessMessage("Address was successfully deleted.");
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
        return "address_list";
    }

    public Address getAddressFromRequestParam() {
        EntityManager em = getEntityManager();
        try{
            Address o = (Address) model.getRowData();
            o = em.merge(o);
            return o;
        } finally {
            em.close();
        }
    }

    public void setAddressFromRequestParam() {
        Address address = getAddressFromRequestParam();
        setAddress(address);
    }

    public DataModel getAddresss() {
        EntityManager em = getEntityManager();
        try{
            Query q = em.createQuery("select object(o) from Address as o");
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

    public Address findAddress(Integer id) {
        EntityManager em = getEntityManager();
        try{
            Address o = (Address) em.find(Address.class, id);
            return o;
        } finally {
            em.close();
        }
    }

    public javax.faces.model.SelectItem[] getCountryIds() {
        EntityManager em = getEntityManager();
        try{
            List <Country> l = (List <Country>) em.createQuery("select o from Country as o").getResultList();
            SelectItem select[] = new SelectItem[l.size()];
            int i = 0;
            for(Country x : l) {
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
            int count = ((Long) em.createQuery("select count(o) from Address as o").getSingleResult()).intValue();
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
        return "address_list";
    }

    public String prev() {
        firstItem -= batchSize;
        if (firstItem < 0) {
            firstItem = 0;
        }
        return "address_list";
    }
    
}
