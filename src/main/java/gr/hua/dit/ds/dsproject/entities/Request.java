package gr.hua.dit.ds.dsproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Request status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private Status requestStatus;

    @Column
    @NotNull(message = "Date Submitted can't be null")
    @Temporal(TemporalType.DATE)
    private LocalDate dateSubmitted;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="freelancer_id")
    private Freelancer freelancer;

    public Request(){
        this.requestStatus = Status.Under_Consideration;
        this.dateSubmitted = LocalDate.now();
    }

    public Request(LocalDate dateSubmitted) {
        this.requestStatus = Status.Under_Consideration;
        this.dateSubmitted = dateSubmitted;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public Status getRequestStatus() {return requestStatus;}
    public void setRequestStatus(Status requestStatus) {this.requestStatus = requestStatus;}

    public LocalDate getDateSubmitted() {return dateSubmitted;}
    public void setDateSubmitted(LocalDate dateSubmitted) {this.dateSubmitted = dateSubmitted;}

    public Project getProject() {return project;}
    public void setProject(Project project) {this.project = project;}

    public Freelancer getFreelancer() {return freelancer;}
    public void setFreelancer(Freelancer freelancer) {this.freelancer = freelancer;}

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", requestStatus=" + requestStatus +
                ", dateSubmitted=" + dateSubmitted +
                '}';
    }
}
