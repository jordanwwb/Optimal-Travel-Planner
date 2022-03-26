/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Account;
import javax.ejb.Local;
import util.exception.AccountDisabledException;
import util.exception.AccountNotFoundException;
import util.exception.ChangePasswordException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PasswordNotAcceptedException;
import util.exception.UnknownPersistenceException;
import util.exception.UsernameAlreadyExistException;

/**
 *
 * @author sucram
 */
@Local
public interface AccountSessionBeanLocal {

    /**
     *
     * @param newAccount
     * @return
     * @throws UsernameAlreadyExistException
     * @throws UnknownPersistenceException
     */
    public Long createNewAccount(Account newAccount) throws UsernameAlreadyExistException, UnknownPersistenceException;

    /**
     *
     * @param accountId
     * @throws AccountNotFoundException
     */
    public void toggleAccountStatus(Long accountId) throws AccountNotFoundException;

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws InvalidLoginCredentialException
     * @throws AccountDisabledException
     */
    public Account login(String username, String password) throws InvalidLoginCredentialException, AccountDisabledException;

    /**
     *
     * @param oldPassword
     * @param newPassword
     * @param accountId
     * @throws AccountNotFoundException
     * @throws ChangePasswordException
     * @throws PasswordNotAcceptedException
     */
    public void changePassword(String oldPassword, String newPassword, Long accountId) throws AccountNotFoundException, ChangePasswordException, PasswordNotAcceptedException;
    
}
