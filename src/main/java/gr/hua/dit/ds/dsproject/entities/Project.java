package gr.hua.dit.ds.dsproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotEmpty(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters" )
    private String title;

    @Column
    @NotEmpty(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;

    @Column(name = "payment_amount")
    @Min(value = 50, message = "Payment amount must be at least 50.0 $")
    @Max(value = 10000, message = "Payment amount must be less than 10,000.0 $")
    @NotNull(message = "Payment amount can't be null")
    private Float paymentAmount;

    @NotNull(message = "Project status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private Status projectStatus;

    @Column
    @NotNull(message = "Deadline can't be null")
    @Future(message = "Deadline must be a future date")
    @Temporal(TemporalType.DATE)
    private LocalDate deadline;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="client_id")
    private Client client;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Assignment assignment;

    @OneToMany(mappedBy = "project", cascade= CascadeType.ALL)
    private List<Request> requests;

    public Project(){this.projectStatus = Status.Pending;}

    public Project(String title, String description, Float paymentAmount, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.paymentAmount = paymentAmount;
        this.projectStatus = Status.Pending;
        this.deadline = deadline;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Float getPaymentAmount() {return paymentAmount;}
    public void setPaymentAmount(Float paymentAmount) {this.paymentAmount = paymentAmount;}

    public Status getProjectStatus() {return projectStatus;}
    public void setProjectStatus(Status projectStatus) {this.projectStatus = projectStatus;}

    public LocalDate getDeadline() {return deadline;}
    public void setDeadline(LocalDate deadline) {this.deadline = deadline;}

    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}

    public Assignment getAssignment() {return assignment;}
    public void setAssignment(Assignment assignment) {this.assignment = assignment;}

    public List<Request> getRequests() {return requests;}
    public void setRequests(List<Request> requests) {this.requests = requests;}

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", projectStatus=" + projectStatus +
                ", deadline=" + deadline +
                '}';
    }
}
