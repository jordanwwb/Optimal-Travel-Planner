/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Business;
import entity.Service;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author sucram
 */
@Named(value = "serviceManagementManagedBean")
@ViewScoped
public class serviceManagementManagedBean implements Serializable {

    @EJB(name = "ServiceSessionBeanLocal")
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    private List<Service> services;
    private List<Service> filteredServices;
    private Boolean filtered;

    /**
     *
     */
    public serviceManagementManagedBean() {
    }

    /**
     *
     */
    @PostConstruct
    public void post() {
        filteredServices = (List<Service>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("servicesToView");
        services = serviceSessionBeanLocal.retrieveAllActiveServices();
    }

    /**
     *
     * @param event
     */
    public void refreshServicesList(ActionEvent event) {

    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void viewServiceOwner(ActionEvent event) throws IOException {
        Business business = (Business) event.getComponent().getAttributes().remove("business");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("businessToView", business);
        FacesContext.getCurrentInstance().getExternalContext().redirect("BusinessManagement.xhtml");
    }
    
    /**
     *
     * @param event
     */
    public void toggleServiceActive(ActionEvent event){
        Service service = (Service) event.getComponent().getAttributes().remove("service");
        Boolean temp = service.getActive() ? false : true;
        service.setActive(temp);
    }

    /**
     *
     * @return
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     *
     * @param services
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     *
     * @return
     */
    public List<Service> getFilteredServices() {
        return filteredServices;
    }

    /**
     *
     * @param filteredServices
     */
    public void setFilteredServices(List<Service> filteredServices) {
        this.filteredServices = filteredServices;
    }

    /**
     *
     * @return
     */
    public Boolean getFiltered() {
        return filtered;
    }

    /**
     *
     * @param filtered
     */
    public void setFiltered(Boolean filtered) {
        this.filtered = filtered;
    }
}
