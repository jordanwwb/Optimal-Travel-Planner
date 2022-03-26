/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PaymentAccount;
import javax.ejb.Local;
import util.exception.DeletePaymentAccountException;
import util.exception.PaymentAccountNotFoundException;

/**
 *
 * @author Jorda
 */
@Local
public interface PaymentAccountSessionBeanLocal {

    /**
     *
     * @param newPaymentAccount
     * @return
     */
    public PaymentAccount createNewPaymentAccount(PaymentAccount newPaymentAccount);

    /**
     *
     * @param paymentAccountId
     * @return
     * @throws PaymentAccountNotFoundException
     */
    public PaymentAccount retrievePaymentAccountByPaymentAccountId(Long paymentAccountId) throws PaymentAccountNotFoundException;

    /**
     *
     * @param paymentAccountId
     * @throws DeletePaymentAccountException
     */
    public void deletePaymentAccount(Long paymentAccountId) throws DeletePaymentAccountException;

    /**
     *
     * @param paymentAccount
     * @throws PaymentAccountNotFoundException
     */
    public void updatePaymentAccount(PaymentAccount paymentAccount) throws PaymentAccountNotFoundException;
    
}
