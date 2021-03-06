package avra.hrsystem.employeemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable{
    private Integer employeeId;

    @NotNull
    @Length(max=30)
    private String firstName;

    @NotNull
    @Length(max=50)
    private String lastName;

    @NotNull
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateOfEmployment;

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private Employee leader;

    private List<Employee> subordinate=new ArrayList<>();

    public Employee(Integer employeeId, String firstName, String lastName, Date dateOfEmployment, Employee leader, List<Employee> subordinate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfEmployment = dateOfEmployment;
        this.leader = leader;
        this.subordinate = subordinate;
    }

    public Employee() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EMPLOYEE_ID")
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name="DATE_OF_EMPLOYMENT")
    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LEADER_ID", referencedColumnName = "EMPLOYEE_ID",foreignKey = @ForeignKey(name = "FK_LEADER_SUBORDINATE"))

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY)
    public List<Employee> getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(List<Employee> subordinate) {
        this.subordinate = subordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
        return dateOfEmployment != null ? dateOfEmployment.equals(employee.dateOfEmployment) : employee.dateOfEmployment == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dateOfEmployment != null ? dateOfEmployment.hashCode() : 0);
        return result;
    }
}