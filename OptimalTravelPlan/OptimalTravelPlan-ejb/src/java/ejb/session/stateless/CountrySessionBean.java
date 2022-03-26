/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Country;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CountryNotFoundException;
import util.exception.DeleteCountryException;

/**
 *
 * @author Anais
 */
@Stateless
public class CountrySessionBean implements CountrySessionBeanLocal {

    @PersistenceContext(unitName = "OptimalTravelPlan-ejbPU")
    private EntityManager em;
    
    /**
     *
     * @param newCountry
     * @return
     */
    @Override
    public Country createNewCountry(Country newCountry) {
        em.persist(newCountry);
        em.flush();
        return newCountry;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<Country> retrieveAllCountries() {
        Query query = em.createQuery("SELECT c FROM Country c ORDER BY c.name ASC");
        List<Country> tagEntities = query.getResultList();
        
        for(Country tagEntity:tagEntities) {            
            tagEntity.getServices().size();
        }
        return tagEntities;
    } 
    
    /**
     *
     * @param countryId
     * @return
     * @throws CountryNotFoundException
     */
    @Override
    public Country retrieveCountryByCountryId(Long countryId) throws CountryNotFoundException {
        Country country = em.find(Country.class, countryId);
        if(country != null) {
            return country;
        } else {
            throw new CountryNotFoundException("Country ID " + countryId + " does not exist!");
        }
    }
    
    /**
     *
     * @param countryId
     * @throws DeleteCountryException
     */
    @Override
    public void deleteCountry(Long countryId) throws DeleteCountryException {
        Country countryEntityToRemove = em.find(Country.class, countryId);
        if(!countryEntityToRemove.getServices().isEmpty()) {
            throw new DeleteCountryException("Country ID " + countryId + " is associated with existing services and cannot be deleted!");
        }
        else {
            em.remove(countryEntityToRemove);
        }     
    }
    
    /**
     *
     * @param country
     * @return
     */
    @Override
    public Country updateCountry(Country country) {
        Country countryToUpdate = em.find(Country.class, country.getCountryId());
        countryToUpdate.setName(country.getName());
        if(countryToUpdate.getServices() != null) {
            countryToUpdate.setServices(country.getServices());
        }
        em.flush();
        return countryToUpdate;
    }

}
