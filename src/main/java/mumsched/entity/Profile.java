package mumsched.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Id;

@MappedSuperclass
public class Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;

    protected String firstname;

    protected String lastname;

    protected String email;

    protected String description;

    @OneToOne(cascade=CascadeType.ALL)
    private User user;

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}