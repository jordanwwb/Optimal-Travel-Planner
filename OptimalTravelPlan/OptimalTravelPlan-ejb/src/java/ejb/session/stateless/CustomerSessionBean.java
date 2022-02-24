/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AccountNotFoundException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author ryo20
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "EasyInstruments-ejbPU")
    private EntityManager em;

    @Override
    public List<Customer> retrieveAllCustomers() {
        Query query = em.createQuery("SELECT c FROM Customer c");

        List<Customer> customers = query.getResultList();
        
        //changed to eager loading
        
        return customers;
    }

    @Override
    public Customer retrieveCustomerById(Long customerId) throws AccountNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer != null) {
            //changed to eager loading
            return customer;
        } else {
            throw new AccountNotFoundException();
        }
    }

    @Override
    public Customer retrieveCustomerByUsername(String username) throws AccountNotFoundException {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            Customer customer = (Customer) query.getSingleResult();
            //changed to eager loading
            return customer;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AccountNotFoundException("Username has no match!");
        }
    }

    //Deleted customer login

    @Override
    public void updateCustomer(Customer customer) throws AccountNotFoundException, UpdateCustomerException {
        if (customer != null && customer.getCustomerId()!= null) {
            Customer customerToUpdate = this.retrieveCustomerById(customer.getCustomerId());

            if (customerToUpdate.getUsername().equals(customer.getUsername())) {
                customerToUpdate.setName(customer.getName());
                customerToUpdate.setMobile(customer.getMobile());
                customerToUpdate.setEmail(customer.getEmail());
            } else {
                throw new UpdateCustomerException("Username of customer record to be updated does not match the existing record");
            }
        } else {
            throw new AccountNotFoundException("Customer ID not provided for customer to be updated");
        }
    }
}
