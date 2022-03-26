/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PaymentTransaction;
import java.util.List;
import javax.ejb.Local;
import util.exception.ConstraintViolationException;
import util.exception.PaymentTransactionNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author sucram
 */
@Local
public interface TransactionSessionBeanLocal {

    /**
     *
     * @return
     */
    public List<PaymentTransaction> retrieveAllPaymentTransaction();

    /**
     *
     * @param paymentTransaction
     * @return
     * @throws ConstraintViolationException
     * @throws UnknownPersistenceException
     */
    public PaymentTransaction createNewPaymentTransaction(PaymentTransaction paymentTransaction) throws ConstraintViolationException, UnknownPersistenceException;

    /**
     *
     * @param transactionId
     * @return
     * @throws PaymentTransactionNotFoundException
     */
    public PaymentTransaction retrievePaymentTransactionByTransactionId(Long transactionId) throws PaymentTransactionNotFoundException;
    
}
