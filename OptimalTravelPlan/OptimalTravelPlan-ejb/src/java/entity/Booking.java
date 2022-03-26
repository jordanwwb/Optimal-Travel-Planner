/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Anais
 */
@Entity
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    private TravelItinerary travelItinerary;

    @OneToOne
    private PaymentTransaction paymentTransaction;

    @OneToOne(cascade = CascadeType.REMOVE)
    private SupportRequest supportRequest;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Review review;

    @ManyToOne
    private Service service;

    /**
     *
     */
    public Booking() {
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param travelItinerary
     * @param service
     */
    public Booking(Date startDate, Date endDate, TravelItinerary travelItinerary, Service service) {
        this();
        this.startDate = startDate;
        this.endDate = endDate;
        this.travelItinerary = travelItinerary;
        this.service = service;
    }

    /**
     *
     * @return
     */
    public Long getBookingId() {
        return bookingId;
    }

    /**
     *
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     */
    public TravelItinerary getTravelItinerary() {
        return travelItinerary;
    }

    /**
     *
     * @param travelItinerary
     */
    public void setTravelItinerary(TravelItinerary travelItinerary) {
        this.travelItinerary = travelItinerary;
    }

    /**
     *
     * @return
     */
    public PaymentTransaction getPaymentTransaction() {
        return paymentTransaction;
    }

    /**
     *
     * @param paymentTransaction
     */
    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    /**
     *
     * @return
     */
    public SupportRequest getSupportRequest() {
        return supportRequest;
    }

    /**
     *
     * @param supportRequest
     */
    public void setSupportRequest(SupportRequest supportRequest) {
        this.supportRequest = supportRequest;
    }

    /**
     *
     * @return
     */
    public Review getReview() {
        return review;
    }

    /**
     *
     * @param review
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     *
     * @return
     */
    public Service getService() {
        return service;
    }

    /**
     *
     * @param service
     */
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the bookingId fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Booking[ id=" + bookingId + " ]";
    }

}
