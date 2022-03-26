/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AccountSessionBeanLocal;
import ejb.session.stateless.StaffSessionBeanLocal;
import entity.Staff;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.AccountNotFoundException;
import util.exception.DeleteStaffException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStaffException;
import util.exception.UsernameAlreadyExistException;

/**
 *
 * @author Anais
 */
@Named(value = "StaffManagementManagedBean")
@ViewScoped
public class adminManagementManagedBean implements Serializable {

    @EJB
    private StaffSessionBeanLocal StaffSessionBeanLocal;

    private List<Staff> Staffs;
    private List<Staff> filteredStaffs;
    
    private Staff newStaff;
    
    private Staff selectedStaffToUpdate;
    
    
    /**
     * Creates a new instance of StaffManagementManagedBean
     */
    public adminManagementManagedBean() {
        newStaff = new Staff();
    }
    
    /**
     *
     */
    @PostConstruct
    public void postConstruct() {
        setStaffs(StaffSessionBeanLocal.retrieveAllStaff());
    }
    
    /**
     *
     * @param event
     * @throws UsernameAlreadyExistException
     * @throws UnknownPersistenceException
     * @throws AccountNotFoundException
     */
    public void createNewStaff(ActionEvent event) throws UsernameAlreadyExistException, UnknownPersistenceException, AccountNotFoundException {
        Staff c = StaffSessionBeanLocal.retrieveStaffById(AccountSessionBeanLocal.createNewAccount(getNewStaff()));
        getStaffs().add(c);
        setNewStaff(new Staff());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Staff created successfully (Staff: " + c.getName() + ")", null));
    }
    
    /**
     *
     * @param event
     */
    public void doUpdateStaff(ActionEvent event) {
        setSelectedStaffToUpdate((Staff) event.getComponent().getAttributes().get("selectedStaffToUpdate"));
    }
    
    /**
     *
     * @param event
     * @throws AccountNotFoundException
     * @throws UpdateStaffException
     */
    public void updateStaff(ActionEvent event) throws AccountNotFoundException, UpdateStaffException {
        StaffSessionBeanLocal.updateStaff(getNewStaff());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff updated successfully", null));
    }
    
    /**
     *
     * @param event
     * @throws AccountNotFoundException
     */
    public void deleteStaff(ActionEvent event) throws AccountNotFoundException {
        try {
            Staff StaffToDelete = (Staff) event.getComponent().getAttributes().get("StaffToDelete");
            StaffSessionBeanLocal.deleteStaff(StaffToDelete.getStaffId());
            getStaffs().remove(StaffToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff deleted successfully", null));
        } catch (DeleteStaffException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to delete:" + ex.getMessage(), null));
        }
    }

    /**
     * @return the Staffs
     */
    public List<Staff> getStaffs() {
        return Staffs;
    }

    /**
     * @param Staffs the Staffs to set
     */
    public void setStaffs(List<Staff> Staffs) {
        this.Staffs = Staffs;
    }

    /**
     * @return the filteredStaffs
     */
    public List<Staff> getFilteredStaffs() {
        return filteredStaffs;
    }

    /**
     * @param filteredStaffs the filteredStaffs to set
     */
    public void setFilteredStaffs(List<Staff> filteredStaffs) {
        this.filteredStaffs = filteredStaffs;
    }

    /**
     * @return the newStaff
     */
    public Staff getNewStaff() {
        return newStaff;
    }

    /**
     * @param newStaff the newStaff to set
     */
    public void setNewStaff(Staff newStaff) {
        this.newStaff = newStaff;
    }

    /**
     * @return the selectedStaffToUpdate
     */
    public Staff getSelectedStaffToUpdate() {
        return selectedStaffToUpdate;
    }

    /**
     * @param selectedStaffToUpdate the selectedStaffToUpdate to set
     */
    public void setSelectedStaffToUpdate(Staff selectedStaffToUpdate) {
        this.selectedStaffToUpdate = selectedStaffToUpdate;
    }
    
}
