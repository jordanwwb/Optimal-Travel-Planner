/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.ServiceType;

/**
 *
 * @author Anais
 */
@Entity
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @ManyToOne
    private Business business;

    @OneToMany
    private List<ServiceRate> rates;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "service")
    private List<Booking> bookings;
    
    @ManyToMany(mappedBy = "services")
    private List<Tag> tags;
    
    private String serviceName;

    private ServiceType serviceType;

    private Boolean requireVaccination;

    private String address;
    
    private Boolean active;
    
    private Integer rating;
    
    private Integer totalNumOfRatings;

    /**
     *
     */
    public Service() {
        this.rates = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.active = true;
        this.rating = 0;
        this.totalNumOfRatings = 0;
    }

    /**
     *
     * @param business
     * @param country
     * @param serviceType
     * @param requireVaccination
     * @param address
     * @param serviceName
     */
    public Service(Business business, Country country, ServiceType serviceType, Boolean requireVaccination, String address, String serviceName) {
        this();
        this.business = business;
        this.country = country;
        this.serviceType = serviceType;
        this.requireVaccination = requireVaccination;
        this.address = address;
        this.serviceName = serviceName;
    }

    /**
     *
     * @return
     */
    public Integer getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    /**
     *
     * @return
     */
    public Integer getTotalNumOfRatings() {
        return totalNumOfRatings;
    }

    /**
     *
     * @param totalNumOfRatings
     */
    public void setTotalNumOfRatings(Integer totalNumOfRatings) {
        this.totalNumOfRatings = totalNumOfRatings;
    }

    /**
     *
     * @return
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     *
     * @return
     */
    public Long getServiceId() {
        return serviceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceId != null ? serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the serviceId fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.serviceId == null && other.serviceId != null) || (this.serviceId != null && !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public Business getBusiness() {
        return business;
    }

    /**
     *
     * @param business
     */
    public void setBusiness(Business business) {
        this.business = business;
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
        this.bookings.add(booking);
    }

    /**
     *
     * @param booking
     */
    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    /**
     *
     * @return
     */
    public List<ServiceRate> getRates() {
        return rates;
    }

    /**
     *
     * @param rates
     */
    public void setRates(List<ServiceRate> rates) {
        this.rates = rates;
    }

    /**
     *
     * @param rate
     */
    public void addRate(ServiceRate rate) {
        this.rates.add(rate);
    }

    /**
     *
     * @param rate
     */
    public void removeRate(ServiceRate rate) {
        this.rates.remove(rate);
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

    /**
     *
     * @return
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     *
     * @param serviceType
     */
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    /**
     *
     * @return
     */
    public Boolean getRequireVaccination() {
        return requireVaccination;
    }

    /**
     *
     * @param requireVaccination
     */
    public void setRequireVaccination(Boolean requireVaccination) {
        this.requireVaccination = requireVaccination;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "entity.Service[ id=" + serviceId + " ]";
    }

    /**
     *
     * @return
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     *
     * @param serviceName
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
