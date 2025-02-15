package gr.hua.dit.ds.dsproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotNull(message = "Date Submitted can't be null")
    @Temporal(TemporalType.DATE)
    private LocalDate dateSubmitted;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="freelancer_id")
    private Freelancer freelancer;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    public Assignment() {}

    public Assignment(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public LocalDate getDateSubmitted() {return dateSubmitted;}
    public void setDateSubmitted(LocalDate dateSubmitted) {this.dateSubmitted = dateSubmitted;}

    public Freelancer getFreelancer() {return freelancer;}
    public void setFreelancer(Freelancer freelancer) {this.freelancer = freelancer;}

    public Project getProject() {return project;}
    public void setProject(Project project) {this.project = project;}

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", dateSubmitted=" + dateSubmitted +
                '}';
    }
}
