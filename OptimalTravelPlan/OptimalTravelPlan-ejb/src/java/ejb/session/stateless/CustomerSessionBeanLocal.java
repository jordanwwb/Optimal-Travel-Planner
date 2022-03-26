/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Local;
import util.exception.AccountNotFoundException;
import util.exception.DeleteCustomerException;
import util.exception.InvalidLoginCredentialsException;
import util.exception.TagNotFoundException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author ryo20
 */
@Local
public interface CustomerSessionBeanLocal {

    /**
     *
     * @return
     */
    public List<Customer> retrieveAllCustomers();

    /**
     *
     * @param customerId
     * @return
     * @throws AccountNotFoundException
     */
    public Customer retrieveCustomerById(Long customerId) throws AccountNotFoundException;

    /**
     *
     * @param username
     * @return
     * @throws AccountNotFoundException
     */
    public Customer retrieveCustomerByUsername(String username) throws AccountNotFoundException;

    /**
     *
     * @param customer
     * @throws AccountNotFoundException
     * @throws UpdateCustomerException
     */
    public void updateCustomer(Customer customer) throws AccountNotFoundException, UpdateCustomerException;

    /**
     *
     * @param customerId
     * @param tagId
     * @throws AccountNotFoundException
     * @throws TagNotFoundException
     */
    public void associateTagToCustomer(Long customerId, Long tagId) throws AccountNotFoundException, TagNotFoundException;

    /**
     *
     * @param businessId
     * @throws AccountNotFoundException
     * @throws DeleteCustomerException
     */
    public void deleteCustomer(Long businessId) throws AccountNotFoundException, DeleteCustomerException;
    
}