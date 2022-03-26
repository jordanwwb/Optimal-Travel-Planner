/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.TravelItinerary;
import java.math.BigDecimal;
import javax.ejb.Local;
import util.exception.AccountNotFoundException;
import util.exception.BookingAlreadyConfirmedException;
import util.exception.BookingNotFoundException;
import util.exception.ConstraintViolationException;
import util.exception.CreateNewBookingException;
import util.exception.TravelItineraryNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author sucram
 */
@Local
public interface TravelItinerarySessionBeanLocal {

    /**
     *
     * @param travelItinerary
     * @param customerId
     * @param countryId
     * @return
     * @throws UnknownPersistenceException
     * @throws ConstraintViolationException
     * @throws AccountNotFoundException
     */
    public Long createNewTravelItinerary(TravelItinerary travelItinerary, Long customerId, Long countryId) throws UnknownPersistenceException, ConstraintViolationException, AccountNotFoundException;
    
    /**
     *
     * @param travelItineraryId
     * @return
     * @throws TravelItineraryNotFoundException
     */
    public TravelItinerary retrieveTravelItineraryById(Long travelItineraryId) throws TravelItineraryNotFoundException;

    /**
     *
     * @param travelItineraryId
     * @throws TravelItineraryNotFoundException
     * @throws BookingNotFoundException
     * @throws BookingAlreadyConfirmedException
     */
    public void deleteTravelItinerary(Long travelItineraryId) throws TravelItineraryNotFoundException, BookingNotFoundException, BookingAlreadyConfirmedException;

    /**
     *
     * @param travelItinerary
     * @return
     * @throws ConstraintViolationException
     * @throws UnknownPersistenceException
     * @throws CreateNewBookingException
     */
    public TravelItinerary recommendTravelItinerary(TravelItinerary travelItinerary) throws ConstraintViolationException, UnknownPersistenceException, CreateNewBookingException ;

    /**
     *
     * @param travelItinerary
     * @return
     */
    public BigDecimal calculateTotalItineraryPrice(TravelItinerary travelItinerary);


}
