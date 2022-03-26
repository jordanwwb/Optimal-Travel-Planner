/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Anais
 */
@Entity
public class TravelItinerary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelItineraryId;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "travelItinerary", fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @ManyToOne
    private Country country;

    /**
     *
     */
    public TravelItinerary() {
    }

    /**
     *
     * @param customer
     * @param startDate
     * @param endDate
     * @param country
     */
    public TravelItinerary(Customer customer, Date startDate, Date endDate, Country country) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.country = country;
    }

    /**
     *
     * @return
     */
    public Long getTravelItineraryId() {
        return travelItineraryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (travelItineraryId != null ? travelItineraryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the travelItineraryId fields are not set
        if (!(object instanceof TravelItinerary)) {
            return false;
        }
        TravelItinerary other = (TravelItinerary) object;
        if ((this.travelItineraryId == null && other.travelItineraryId != null) || (this.travelItineraryId != null && !this.travelItineraryId.equals(other.travelItineraryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TravelItinerary[ id=" + travelItineraryId + " ]";
    }

    /**
     *
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     *
     * @return
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     *
     * @param bookings
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     *
     * @param booking
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     *
     * @param booking
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
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
    public Country getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

}
