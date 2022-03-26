/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PaymentTransaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.ConstraintViolationException;
import util.exception.PaymentTransactionNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Anais
 */
@Stateless
public class TransactionSessionBean implements TransactionSessionBeanLocal {

    @PersistenceContext(unitName = "OptimalTravelPlan-ejbPU")
    private EntityManager em;
    
    /**
     *
     * @param paymentTransaction
     * @return
     * @throws ConstraintViolationException
     * @throws UnknownPersistenceException
     */
    @Override
    public PaymentTransaction createNewPaymentTransaction(PaymentTransaction paymentTransaction) throws ConstraintViolationException, UnknownPersistenceException{
        try {
            em.persist(paymentTransaction);
            em.flush();
            return paymentTransaction;
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
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<PaymentTransaction> retrieveAllPaymentTransaction(){
        Query query = em.createQuery("SELECT p FROM PaymentTransaction p");
        List<PaymentTransaction> paymentTransactions = query.getResultList();
        for(PaymentTransaction p : paymentTransactions){
            p.getPaymentAccount();
        }
        return paymentTransactions;
    }

    /**
     *
     * @param transactionId
     * @return
     * @throws PaymentTransactionNotFoundException
     */
    @Override
    public PaymentTransaction retrievePaymentTransactionByTransactionId(Long transactionId) throws PaymentTransactionNotFoundException {
        PaymentTransaction paymentTransaction = em.find(PaymentTransaction.class, transactionId);
        if(paymentTransaction != null) {
            return paymentTransaction;
        } else {
            throw new PaymentTransactionNotFoundException();
        }
    }
    
}
