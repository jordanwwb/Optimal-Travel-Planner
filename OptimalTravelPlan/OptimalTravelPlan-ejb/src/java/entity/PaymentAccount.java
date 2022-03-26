/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.PaymentType;

/**
 *
 * @author Anais
 */
@Entity
public class PaymentAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymenetAccountId;

    private String accountNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date cardExpirationDate;

    private String ccv;

    private PaymentType paymentType;
    
    private Boolean enabled;

    /**
     *
     */
    public PaymentAccount() {
    }

    /**
     *
     * @param accountNumber
     * @param cardExpirationDate
     * @param ccv
     * @param paymentType
     * @param enabled
     */
    public PaymentAccount(String accountNumber, Date cardExpirationDate, String ccv, PaymentType paymentType, Boolean enabled) {
        this.accountNumber = accountNumber;
        this.cardExpirationDate = cardExpirationDate;
        this.ccv = ccv;
        this.paymentType = paymentType;
        this.enabled = enabled;
    }

    /**
     *
     * @return
     */
    public Long getPaymenetAccountId() {
        return paymenetAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymenetAccountId != null ? paymenetAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the paymenetAccountId fields are not set
        if (!(object instanceof PaymentAccount)) {
            return false;
        }
        PaymentAccount other = (PaymentAccount) object;
        if ((this.paymenetAccountId == null && other.paymenetAccountId != null) || (this.paymenetAccountId != null && !this.paymenetAccountId.equals(other.paymenetAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PaymentAccount[ id=" + paymenetAccountId + " ]";
    }

    /**
     *
     * @return
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     *
     * @return
     */
    public Date getCardExpirationDate() {
        return cardExpirationDate;
    }

    /**
     *
     * @param cardExpirationDate
     */
    public void setCardExpirationDate(Date cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    /**
     *
     * @return
     */
    public String getCcv() {
        return ccv;
    }

    /**
     *
     * @param ccv
     */
    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    /**
     *
     * @return
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     *
     * @param paymentType
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     *
     * @return
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
