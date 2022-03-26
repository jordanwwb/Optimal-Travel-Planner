/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CountrySessionBeanLocal;
import entity.Country;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.DeleteCountryException;

/**
 *
 * @author Jorda
 */
@Named(value = "countryManagementManagedBean")
@ViewScoped
public class countryManagementManagedBean implements Serializable {

    @EJB
    private CountrySessionBeanLocal countrySessionBeanLocal;

    private List<Country> countries;
    private List<Country> filteredCountries;
    
    private Country newCountry;
    
    private Country selectedCountryToUpdate;
    
    
    /**
     * Creates a new instance of countryManagementManagedBean
     */
    public countryManagementManagedBean() {
        newCountry = new Country();
    }
    
    /**
     *
     */
    @PostConstruct
    public void postConstruct() {
        setCountries(countrySessionBeanLocal.retrieveAllCountries());
    }
    
    /**
     *
     * @param event
     */
    public void createNewCountry(ActionEvent event) {
        Country c = countrySessionBeanLocal.createNewCountry(getNewCountry());
        getCountries().add(c);
        setNewCountry(new Country());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Country created successfully (Country: " + c.getName() + ")", null));
    }
    
    /**
     *
     * @param event
     */
    public void doUpdateCountry(ActionEvent event) {
        setSelectedCountryToUpdate((Country) event.getComponent().getAttributes().get("selectedCountryToUpdate"));
    }
    
    /**
     *
     * @param event
     */
    public void updateCountry(ActionEvent event) {
        countrySessionBeanLocal.updateCountry(getNewCountry());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Country updated successfully", null));
    }
    
    /**
     *
     * @param event
     */
    public void deleteCountry(ActionEvent event) {
        try {
            Country countryToDelete = (Country) event.getComponent().getAttributes().get("countryToDelete");
            countrySessionBeanLocal.deleteCountry(countryToDelete.getCountryId());
            getCountries().remove(countryToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Country deleted successfully", null));
        } catch (DeleteCountryException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to delete:" + ex.getMessage(), null));
        }
    }

    /**
     * @return the countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * @return the filteredCountries
     */
    public List<Country> getFilteredCountries() {
        return filteredCountries;
    }

    /**
     * @param filteredCountries the filteredCountries to set
     */
    public void setFilteredCountries(List<Country> filteredCountries) {
        this.filteredCountries = filteredCountries;
    }

    /**
     * @return the newCountry
     */
    public Country getNewCountry() {
        return newCountry;
    }

    /**
     * @param newCountry the newCountry to set
     */
    public void setNewCountry(Country newCountry) {
        this.newCountry = newCountry;
    }

    /**
     * @return the selectedCountryToUpdate
     */
    public Country getSelectedCountryToUpdate() {
        return selectedCountryToUpdate;
    }

    /**
     * @param selectedCountryToUpdate the selectedCountryToUpdate to set
     */
    public void setSelectedCountryToUpdate(Country selectedCountryToUpdate) {
        this.selectedCountryToUpdate = selectedCountryToUpdate;
    }
    
}
