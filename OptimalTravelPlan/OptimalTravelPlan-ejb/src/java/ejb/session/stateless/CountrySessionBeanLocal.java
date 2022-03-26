/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Country;
import java.util.List;
import javax.ejb.Local;
import util.exception.CountryNotFoundException;
import util.exception.DeleteCountryException;

/**
 *
 * @author sucram
 */
@Local
public interface CountrySessionBeanLocal {

    /**
     *
     * @return
     */
    public List<Country> retrieveAllCountries();

    /**
     *
     * @param countryId
     * @return
     * @throws CountryNotFoundException
     */
    public Country retrieveCountryByCountryId(Long countryId) throws CountryNotFoundException;

    /**
     *
     * @param countryId
     * @throws DeleteCountryException
     */
    public void deleteCountry(Long countryId) throws DeleteCountryException;

    /**
     *
     * @param newCountry
     * @return
     */
    public Country createNewCountry(Country newCountry);

    /**
     *
     * @param country
     * @return
     */
    public Country updateCountry(Country country);
    
}
