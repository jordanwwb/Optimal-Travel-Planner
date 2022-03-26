/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AccountSessionBeanLocal;
import ejb.session.stateless.BusinessSessionBeanLocal;
import ejb.session.stateless.StaffSessionBeanLocal;
import entity.Account;
import entity.Business;
import entity.Staff;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.AccountNotFoundException;
import util.exception.ChangePasswordException;
import util.exception.PasswordNotAcceptedException;
import util.exception.UpdateBusinessException;
import util.exception.UpdateStaffException;

/**
 *
 * @author Anais
 */
@Named(value = "loginManagedBean")
@ViewScoped
public class loginManagedBean implements Serializable {

    @EJB(name = "AccountSessionBeanLocal")
    private AccountSessionBeanLocal accountSessionBeanLocal;

    @EJB(name = "BusinessSessionBeanLocal")
    private BusinessSessionBeanLocal businessSessionBeanLocal;

    @EJB(name = "StaffSessionBeanLocal")
    private StaffSessionBeanLocal staffSessionBeanLocal;

    private Business business;
    private Staff admin;

    private String oldPassword;
    private String oldPassword2;
    
    private String password;
    private String password2;

    /**
     *
     */
    public loginManagedBean() {
    }

    /**
     *
     */
    @PostConstruct
    public void post() {
        Boolean loggedIn = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("isLogin");
        if(loggedIn){
            try {
                updateAccount(null);
            } catch (IOException ex) {
                Logger.getLogger(loginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @throws IOException
     */
    public void logout() throws IOException {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void updateAccount(ActionEvent event) throws IOException {
        Account user = (Account) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInAccount");
        password = "";
        if (user instanceof Business) {
            business = (Business) user;
        } else if (user instanceof Staff) {
            admin = (Staff) user;
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void doUpdateAction(ActionEvent event) throws IOException {
        try {
            if (business != null) {
                if (!password2.isEmpty()) {
                    accountSessionBeanLocal.changePassword(oldPassword2, password2, business.getAccountId());
                }
                businessSessionBeanLocal.updateBusiness(business);
            } else if (admin != null) {
                if (!password.isEmpty()) {
                    accountSessionBeanLocal.changePassword(oldPassword, password, admin.getAccountId());
                }
                staffSessionBeanLocal.updateStaff(admin);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account Updated!", null));
            password = "";
        } catch (AccountNotFoundException | ChangePasswordException | PasswordNotAcceptedException | UpdateBusinessException | UpdateStaffException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to update account : " + e.getMessage(), null));
        }
    }

    /**
     *
     * @return
     */
    public Business getBusiness() {
        return business;
    }

    /**
     *
     * @param business
     */
    public void setBusiness(Business business) {
        this.business = business;
    }

    /**
     *
     * @return
     */
    public Staff getAdmin() {
        return admin;
    }

    /**
     *
     * @param admin
     */
    public void setAdmin(Staff admin) {
        this.admin = admin;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getPassword2() {
        return password2;
    }

    /**
     *
     * @param password2
     */
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    /**
     *
     * @return
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     *
     * @param oldPassword
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     *
     * @return
     */
    public String getOldPassword2() {
        return oldPassword2;
    }

    /**
     *
     * @param oldPassword2
     */
    public void setOldPassword2(String oldPassword2) {
        this.oldPassword2 = oldPassword2;
    }

}
