
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import util.enumeration.EmployeeRole;
import util.exception.PasswordNotAcceptedException;

/**
 *
 * @author Anais
 */
@Entity
public class Staff extends Account implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private EmployeeRole employeeRole;

    /**
     *
     */
    public Staff() {
    }

    /**
     *
     * @param username
     * @param password
     * @param name
     * @param employeeRole
     * @throws PasswordNotAcceptedException
     */
    public Staff(String username, String password, String name, EmployeeRole employeeRole) throws PasswordNotAcceptedException {
        super(username, password);
        this.name = name;
        this.employeeRole = employeeRole;
    }

    /**
     *
     * @return
     */
    public Long getStaffId() {
        return getAccountId();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getStaffId() != null ? getStaffId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the staffId fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.getStaffId() == null && other.getStaffId() != null) || (getStaffId() != null && !getStaffId().equals(other.getStaffId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Staff[ id=" + getStaffId() + " ]";
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    /**
     *
     * @param employeeRole
     */
    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }
    
}
