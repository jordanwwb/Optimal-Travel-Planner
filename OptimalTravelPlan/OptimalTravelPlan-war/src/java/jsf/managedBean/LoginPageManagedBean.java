/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AccountSessionBeanLocal;
import entity.Account;
import entity.Business;
import entity.Staff;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.AccountDisabledException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author ryo20
 */
@Named(value = "loginPageManagedBean")
@RequestScoped
public class LoginPageManagedBean {

    @EJB
    private AccountSessionBeanLocal accountSessionBeanLocal;

    private String username;
    private String password;

    private String username2;
    private String password2;

    private String recoveryEmail;
    private FacesContext fc;

    /**
     * Creates a new instance of LoginPageManagedBean
     */
    public LoginPageManagedBean() {
    }
    
    /**
     *
     */
    @PostConstruct
    public void post(){
        this.password = "";
        this.password2 = "";
        this.username = "";
        this.username2 = "";
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void login(ActionEvent event) throws IOException {
        try {
            fc = FacesContext.getCurrentInstance();
            
            Account loginAccount;
            
            System.out.println("User & pass = " + username.isEmpty() + " " + password.isEmpty());
            if (!username.isEmpty() && !password.isEmpty()) {
                System.out.println("User & pass = " + username + " " + password);
                loginAccount = accountSessionBeanLocal.login(username, password);
            } else {
                System.out.println("User & pass = " + username2 + " " + password2);
                loginAccount = accountSessionBeanLocal.login(username2, password2);
            }

            fc.getExternalContext().getSessionMap().put("isLogin", true);
            fc.getExternalContext().getSessionMap().put("loggedInAccount", loginAccount);

            // So the growl on the next page can access the message
            fc.getExternalContext().getFlash().setKeepMessages(true);

            if (loginAccount instanceof Staff) {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Successful!", null));
                fc.getExternalContext().redirect("./adminPageFolder/adminMain.xhtml");

            } else if (loginAccount instanceof Business) {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Successful!", null));
                fc.getExternalContext().redirect("./businessPageFolder/businessMain.xhtml");

            } else {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid AccessRight for this portal!", null));
                fc.getExternalContext().redirect("errorPage.xhtml");
            }

        } catch (InvalidLoginCredentialException ex) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid Credentials Provided!", null));
            System.out.println("Exception details : " + ex.getMessage());
        } catch (AccountDisabledException ex) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account has been disabled!", null));
        }
    }

    /**
     *
     * @throws IOException
     */
    public void logout() throws IOException {
        fc.getExternalContext().invalidateSession();
        fc.getExternalContext().redirect("index.xhtml");
    }

    /**
     *
     */
    public void sendRecoveryEmail() {
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Recovery Email Sent!", null));
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
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
    public String getRecoveryEmail() {
        return recoveryEmail;
    }

    /**
     *
     * @param recoveryEmail
     */
    public void setRecoveryEmail(String recoveryEmail) {
        this.recoveryEmail = recoveryEmail;
    }

    /**
     *
     * @return
     */
    public String getUsername2() {
        return username2;
    }

    /**
     *
     * @param username2
     */
    public void setUsername2(String username2) {
        this.username2 = username2;
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

}
