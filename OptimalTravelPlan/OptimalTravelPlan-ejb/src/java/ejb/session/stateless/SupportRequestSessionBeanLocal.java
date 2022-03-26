/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SupportRequest;
import java.util.List;
import javax.ejb.Local;
import util.exception.ConstraintViolationException;
import util.exception.CreateSupportRequestException;
import util.exception.ResolveSupportRequestException;
import util.exception.SupportRequestNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author sucram
 */
@Local
public interface SupportRequestSessionBeanLocal {

    /**
     *
     * @param supportRequestId
     * @return
     * @throws SupportRequestNotFoundException
     */
    public SupportRequest retrieveSupportRequestById(Long supportRequestId) throws SupportRequestNotFoundException;

    /**
     *
     * @param newSupportRequest
     * @param bookingId
     * @return
     * @throws UnknownPersistenceException
     * @throws ConstraintViolationException
     * @throws CreateSupportRequestException
     */
    public Long createNewSupportRequest(SupportRequest newSupportRequest, Long bookingId) throws UnknownPersistenceException, ConstraintViolationException, CreateSupportRequestException;

    /**
     *
     * @return
     */
    public List<SupportRequest> retrieveAllSupportRequests();

    /**
     *
     * @return
     */
    public List<SupportRequest> retrieveAllUnresolvedSupportRequests();

    /**
     *
     * @param supportRequestId
     * @throws SupportRequestNotFoundException
     * @throws ResolveSupportRequestException
     */
    public void resolveSupportRequest(Long supportRequestId) throws SupportRequestNotFoundException, ResolveSupportRequestException;
    
}
