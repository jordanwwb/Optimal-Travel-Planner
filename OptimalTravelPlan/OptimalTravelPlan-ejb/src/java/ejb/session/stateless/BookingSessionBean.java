/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Booking;
import entity.Country;
import entity.Service;
import entity.Tag;
import entity.TravelItinerary;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.BookingAlreadyConfirmedException;
import util.exception.BookingNotFoundException;
import util.exception.ConstraintViolationException;
import util.exception.CountryNotFoundException;
import util.exception.CreateNewBookingException;
import util.exception.CreateNewServiceException;
import util.exception.ServiceNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.TravelItineraryNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Anais
 */
@Stateless
public class BookingSessionBean implements BookingSessionBeanLocal {

    @EJB
    private TravelItinerarySessionBeanLocal travelItinerarySessionBeanLocal;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @PersistenceContext(unitName = "OptimalTravelPlan-ejbPU")
    private EntityManager em;

    /**
     *
     * @param newBooking
     * @param serviceId
     * @param travelItineraryId
     * @return
     * @throws ConstraintViolationException
     * @throws UnknownPersistenceException
     * @throws CreateNewBookingException
     */
    @Override
    public Long createBooking(Booking newBooking, Long serviceId, Long travelItineraryId) throws ConstraintViolationException, UnknownPersistenceException, CreateNewBookingException {
        try {
            Service service = serviceSessionBeanLocal.retrieveServiceById(serviceId);
            TravelItinerary travelItinerary = travelItinerarySessionBeanLocal.retrieveTravelItineraryById(travelItineraryId);
            newBooking.setService(service);
            newBooking.setTravelItinerary(travelItinerary);
            em.persist(newBooking);
            travelItinerary.getBookings().add(newBooking);
            service.getBookings().add(newBooking);
            em.flush();

            return newBooking.getBookingId();

        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new ConstraintViolationException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } catch (ServiceNotFoundException | TravelItineraryNotFoundException ex) {
            throw new CreateNewBookingException("Issue with provided serviceId or travelItinaryId!");
        }
    }

    /**
     *
     * @param bookingId
     * @return
     * @throws BookingNotFoundException
     */
    @Override
    public Booking retrieveBookingById(Long bookingId) throws BookingNotFoundException {
        Booking booking = em.find(Booking.class, bookingId);
        if (booking != null) {
            //remove loading since by default (one to one / many to one) are EARGER fetch
            return booking;
        } else {
            throw new BookingNotFoundException();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<Booking> retrieveAllBookings() {
        Query query = em.createQuery("SELECT b FROM Booking b");
        return query.getResultList();
        //remove loading since by default (one to one / many to one) are EARGER fetch
    }

    /**
     *
     * @param serviceId
     * @return
     */
    @Override
    public List<Booking> retrieveBookingsByServiceId(Long serviceId) {
        Query query = em.createQuery("SELECT b FROM Service s JOIN s.bookings b WHERE s.serviceId = :serviceId");
        query.setParameter("serviceId", serviceId);
        return query.getResultList();
    }

    /**
     *
     * @param bookingId
     * @throws BookingNotFoundException
     * @throws BookingAlreadyConfirmedException
     */
    @Override
    public void deleteBookingById(Long bookingId) throws BookingNotFoundException, BookingAlreadyConfirmedException {
        Booking booking = retrieveBookingById(bookingId);
        if (booking.getPaymentTransaction() != null) {
            throw new BookingAlreadyConfirmedException("Booking has been paid and confirmed!");
        }
        booking.getService().removeBooking(booking);
        booking.getTravelItinerary().removeBooking(booking);
        //cascade type for review and support = Cascade.remove
        em.remove(booking);
        em.flush();
    }
}
