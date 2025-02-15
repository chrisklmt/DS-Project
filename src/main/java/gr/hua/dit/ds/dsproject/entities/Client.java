package gr.hua.dit.ds.dsproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {

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
            message = "Please enter a valid phone number\nMust start with '+' for international")
    private String phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Client() {this.projects = new ArrayList<>();}

    public Client(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.projects = new ArrayList<>();
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public List<Project> getProjects() {return projects;}
    public void setProjects(List<Project> projects) {this.projects = projects;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
