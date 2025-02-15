package gr.hua.dit.ds.dsproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Freelancer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    @NotEmpty(message = "First Name is required")
    @Size(min = 3, max = 30, message = "First Name must be between 3 and 30 characters" )
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last Name is required")
    @Size(min = 3, max = 50, message = "Last Name must be between 3 and 50 characters" )
    private String lastName;

    @Column
    @Pattern(regexp = "^\\+\\d{1,4}\\s?\\d{6,15}$",
            message = "Please enter a valid phone number\nFormat: +(1,4)digits [space] (6,15)digits")
    private String phone;

    @Column
    @NotEmpty(message = "Skills are required")
    @Size(min = 5, message = "Skills must be at least 5 characters")
    private String skills;

    @NotNull(message = "Freelancer's verification can't be null")
    @Column
    private Boolean verified;

    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL)
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL)
    private List<Request> requests;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Freelancer() {this.verified = false;}

    public Freelancer(String firstName, String lastName, String phone,String skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.skills = skills;
        this.verified = false;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Boolean getVerified() {
        return verified;
    }
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public List<Assignment> getAssignments() {return assignments;}
    public void setAssignments(List<Assignment> assignments) {this.assignments = assignments;}

    public List<Request> getRequests() {return requests;}
    public void setRequests(List<Request> requests) {this.requests = requests;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    @Override
    public String toString() {
        return "Freelancer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", skills='" + skills + '\'' +
                ", verified=" + verified +
                '}';
    }
}
