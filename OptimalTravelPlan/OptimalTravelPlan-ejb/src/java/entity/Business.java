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
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import util.exception.PasswordNotAcceptedException;

/**
 *
 * @author Anais
 */
@Entity
public class Business extends Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY)
    private List<Service> services;

    private String companyName;

    private String companyWebsite;

    private String companyNumber;

    private String headquarterAddress;

    /**
     *
     */
    public Business() {
    }

    /**
     *
     * @param companyName
     * @param companyWebsite
     * @param companyNumber
     * @param headquarterAddress
     * @param username
     * @param password
     * @throws PasswordNotAcceptedException
     */
    public Business(String companyName, String companyWebsite, String companyNumber, String headquarterAddress, String username, String password) throws PasswordNotAcceptedException {
        super(username, password);
        this.companyName = companyName;
        this.companyWebsite = companyWebsite;
        this.companyNumber = companyNumber;
        this.headquarterAddress = headquarterAddress;
        this.services = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public Long getBusinessId() {
        return getAccountId();
    }

    /**
     *
     * @param businessId
     */
    public void setBusinessId(Long businessId) {
        setAccountId(businessId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getAccountId() != null ? getAccountId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the getAccountId() fields are not set
        if (!(object instanceof Business)) {
            return false;
        }
        Business other = (Business) object;
        if ((this.getAccountId() == null && other.getAccountId() != null) || (this.getAccountId() != null && !this.getAccountId().equals(other.getAccountId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Business[ id=" + getAccountId() + " ]";
    }

    /**
     *
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return
     */
    public String getCompanyWebsite() {
        return companyWebsite;
    }

    /**
     *
     * @param companyWebsite
     */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    /**
     *
     * @return
     */
    public String getCompanyNumber() {
        return companyNumber;
    }

    /**
     *
     * @param companyNumber
     */
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    /**
     *
     * @return
     */
    public String getHeadquarterAddress() {
        return headquarterAddress;
    }

    /**
     *
     * @param headquarterAddress
     */
    public void setHeadquarterAddress(String headquarterAddress) {
        this.headquarterAddress = headquarterAddress;
    }

    /**
     *
     * @return
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     *
     * @param services
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     *
     * @param service
     */
    public void addService(Service service) {
        services.add(service);
    }

    /**
     *
     * @param service
     */
    public void removeService(Service service) {
        services.remove(service);
    }
}
