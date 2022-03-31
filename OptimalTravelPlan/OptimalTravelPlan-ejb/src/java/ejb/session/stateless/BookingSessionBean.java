/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Booking;
import entity.Service;
import entity.SupportRequest;
import entity.TravelItinerary;
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
import util.exception.CreateNewBookingException;
import util.exception.ServiceNotFoundException;
import util.exception.SupportRequestNotFoundException;
import util.exception.TravelItineraryNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateBookingException;

@Stateless
public class BookingSessionBean implements BookingSessionBeanLocal {

    @EJB
    private SupportRequestSessionBeanLocal supportRequestSessionBeanLocal;

    @EJB
    private TravelItinerarySessionBeanLocal travelItinerarySessionBeanLocal;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @PersistenceContext(unitName = "OptimalTravelPlan-ejbPU")
    private EntityManager em;

    @Override
    public Long createBooking(Booking newBooking, Long serviceId, Long travelItineraryId) throws ConstraintViolationException, UnknownPersistenceException, CreateNewBookingException {
        System.out.println("ejb.session.stateless.BookingSessionBean.createBooking()");
        try {
            Service service = serviceSessionBeanLocal.retrieveServiceById(serviceId);
            TravelItinerary travelItinerary = travelItinerarySessionBeanLocal.retrieveTravelItineraryById(travelItineraryId);
            newBooking.setService(service);
            newBooking.setTravelItinerary(travelItinerary);
            em.persist(newBooking);
            travelItinerary.getBookings().add(newBooking);
            service.getBookings().add(newBooking);
            em.flush();
            System.out.println("ejb.session.stateless.BookingSessionBean.createBooking() successful!! booking id = " + newBooking.getBookingId());
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

    @Override
    public Booking retrieveBookingBySupportRequest(Long supportRequestId) throws SupportRequestNotFoundException {
        SupportRequest sq = supportRequestSessionBeanLocal.retrieveSupportRequestById(supportRequestId);
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b.supportRequest = :supportReq");
        query.setParameter("supportReq", sq);
        return (Booking) query.getSingleResult();
    }

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

    @Override
    public List<Booking> retrieveAllBookings() {
        Query query = em.createQuery("SELECT b FROM Booking b");
        return query.getResultList();
        //remove loading since by default (one to one / many to one) are EARGER fetch
    }

    @Override
    public List<Booking> retrieveBookingsByServiceId(Long serviceId) {
        Query query = em.createQuery("SELECT b FROM Service s JOIN s.bookings b WHERE s.serviceId = :serviceId");
        query.setParameter("serviceId", serviceId);
        return query.getResultList();
    }

    @Override
    public List<Booking> retrieveBookingsByBusinessId(Long businessId) {
        Query query = em.createQuery("SELECT b FROM Service s JOIN s.bookings b where s.business.accountId = :businessId");
        query.setParameter("businessId", businessId);
        return query.getResultList();
    }

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

    @Override
    public void updateBooking(Booking booking) throws BookingNotFoundException, UpdateBookingException {
        Booking bookingToUpdate = retrieveBookingById(booking.getBookingId());

        if (booking.getStartDate().after(booking.getEndDate())) {
            throw new UpdateBookingException();
        }

        bookingToUpdate.setStartDate(booking.getStartDate());
        bookingToUpdate.setEndDate(booking.getEndDate());
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
