/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Service;
import java.util.List;
import javax.ejb.Local;
import util.exception.AccountNotFoundException;
import util.exception.ConstraintViolationException;
import util.exception.CreateNewServiceException;
import util.exception.ServiceNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateServiceException;

/**
 *
 * @author sucram
 */
@Local
public interface ServiceSessionBeanLocal {

    /**
     *
     * @param newService
     * @param businessId
     * @param tagIds
     * @param countryId
     * @return
     * @throws UnknownPersistenceException
     * @throws ConstraintViolationException
     * @throws CreateNewServiceException
     */
    public Long createNewService(Service newService, Long businessId, List<Long> tagIds, Long countryId) throws UnknownPersistenceException, ConstraintViolationException, CreateNewServiceException;

    /**
     *
     * @param serviceId
     * @return
     * @throws ServiceNotFoundException
     */
    public Service retrieveServiceById(Long serviceId) throws ServiceNotFoundException;

    /**
     *
     * @return
     */
    public List<Service> retrieveAllServices();

    /**
     *
     * @return
     */
    public List<Service> retrieveAllActiveServices();

    /**
     *
     * @param newService
     * @throws ServiceNotFoundException
     * @throws UpdateServiceException
     * @throws AccountNotFoundException
     */
    public void updateService(Service newService) throws ServiceNotFoundException, UpdateServiceException, AccountNotFoundException;

    /**
     *
     * @param serviceId
     * @throws ServiceNotFoundException
     */
    public void toggleServiceActivation(Long serviceId) throws ServiceNotFoundException;

    /**
     *
     * @param countryId
     * @return
     */
    public List<Service> retrieveAllServiceByCountry(Long countryId);
    
}
