/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author sucram
 */
@Named(value = "adminTemplateManagedBean")
@RequestScoped
public class adminTemplateManagedBean {

    /**
     *
     */
    public adminTemplateManagedBean() {
    }

    /**
     *
     * @throws IOException
     */
    public void redirectToCountryManagement() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("countryManagement.xhtml");
    }

    /**
     *
     * @throws IOException
     */
    public void redirectToAddCountryManagement() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("addNewCountry", true);
        redirectToCountryManagement();
    }

    /**
     *
     * @throws IOException
     */
    public void redirectToTagManagement() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("tagManagement.xhtml");
    }

    /**
     *
     * @throws IOException
     */
    public void redirectToAddTagManagement() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("addNewTag", true);
        redirectToTagManagement();
    }

    /**
     *
     * @throws IOException
     */
    public void redirectToAccountManagement() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("accountManagaement.xhtml");
    }

    /**
     *
     * @throws IOException
     */
    public void redirectToAddAccountManagement() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("addNewAccount", true);
        redirectToAccountManagement();
    }

    /**
     *
     */
    public void redirectToSupportManagement() {

    }

    /**
     *
     * @throws IOException
     */
    public void redirectToServiceManagement() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("serviceManagaement.xhtml");
    }

    /**
     *
     * @throws IOException
     */
    public void redirectToAddServiceManagement() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("addNewService", true);
        redirectToServiceManagement();
    }
}
