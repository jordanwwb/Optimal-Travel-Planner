package jsf.managedBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ejb.session.stateless.AccountSessionBeanLocal;
import entity.Business;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.PasswordNotAcceptedException;
import util.exception.UnknownPersistenceException;
import util.exception.UsernameAlreadyExistException;

/**
 *
 * @author ryo20
 */
@Named(value = "registerPageManagedBean")
@RequestScoped
public class RegisterPageManagedBean {

    @EJB
    private AccountSessionBeanLocal accountSessionBeanLocal;

    private String username;
    private String password;
    private String companyName;
    private String companyWebsite;
    private String companyNumber;
    private String companyAddress;

    /**
     *
     */
    public RegisterPageManagedBean() {
    }

    /**
     *
     * @return
     */
    public String getCompanyNumber() {
        return companyNumber;
    }

    /**
     *
     * @param companyNumber
     */
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    /**
     *
     * @return
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     *
     * @param companyAddress
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    /**
     *
     * @throws IOException
     */
    public void register() throws IOException {
        try {
            Business newBusiness = new Business(companyName, companyWebsite, companyNumber, companyAddress, username, password);
            accountSessionBeanLocal.createNewAccount(newBusiness);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInAccount", newBusiness);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration Successful!", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("./businessPageFolder/businessMain.xhtml");

        } catch (PasswordNotAcceptedException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid Password!", null));
        } catch (UsernameAlreadyExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Username already exists!", null));
        } catch (UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration Failed", null));
        }
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
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return
     */
    public String getCompanyWebsite() {
        return companyWebsite;
    }

    /**
     *
     * @param companyWebsite
     */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

}
