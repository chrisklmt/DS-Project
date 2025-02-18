package gr.hua.dit.ds.dsproject.services;

import gr.hua.dit.ds.dsproject.entities.*;
import gr.hua.dit.ds.dsproject.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FreelancerService {

    private final FreelancerRepository freelancerRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final RequestRepository requestRepository;
    private final ProjectRepository projectRepository;

    public FreelancerService(FreelancerRepository freelancerRepository, UserRepository userRepository,
                             AssignmentRepository assignmentRepository, RequestRepository requestRepository,
                             ProjectRepository projectRepository) {
        this.freelancerRepository = freelancerRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
        this.requestRepository = requestRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public List<Freelancer> getFreelancers(){
        return freelancerRepository.findAll();
    }

    @Transactional
    public List<Freelancer> getNotVerifiedFreelancer() {
        List<Freelancer> freelancers = freelancerRepository.findAll();
        List<Freelancer> notVerifiedFreelancers = new ArrayList<>();
        for (Freelancer f : freelancers) {
            if(!f.getVerified()) {
                notVerifiedFreelancers.add(f);
            }
        }
        return notVerifiedFreelancers;
    }

    @Transactional
    public void saveFreelancer(Freelancer freelancer) {freelancerRepository.save(freelancer);}

    @Transactional
    public void updateFreelancer(Freelancer freelancer) {
        Freelancer existingFreelancer = freelancerRepository.findById(freelancer.getId()).get();

        existingFreelancer.setId(existingFreelancer.getId());
        existingFreelancer.setFirstName(freelancer.getFirstName());
        existingFreelancer.setLastName(freelancer.getLastName());
        existingFreelancer.setPhone(freelancer.getPhone());
        existingFreelancer.setSkills(freelancer.getSkills());
    }

    @Transactional
    public Freelancer getFreelancer(Integer freelancerId) {
        return freelancerRepository.findById(freelancerId).get();
    }

    @Transactional
    public Freelancer getCurrentFreelancer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(email);

        Freelancer freelancer = user.getFreelancer();
        if (freelancer == null) {
            throw new RuntimeException("Freelancer not found for email: " + email);
        }
        return freelancer;
    }

    @Transactional
    public void deleteFreelancer(Integer freelancerId) {
        Freelancer freelancer = freelancerRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer not found"));

        List<Assignment> assignments = freelancer.getAssignments();
        List<Request> requests = freelancer.getRequests();

        // Αποσύνδεση από τα requests
        if (requests != null && !requests.isEmpty()) {
            for (Request request : requests) {
                request.setFreelancer(null);  // Αποσύνδεση του freelancer από το request
                requestRepository.save(request); // Αποθήκευση για την ανανέωση της κατάστασης
            }
            // Διαγραφή των requests
            requestRepository.deleteAll(requests);
        }

        // Αποσύνδεση από τα assignments
        if (assignments != null && !assignments.isEmpty()) {
            for (Assignment assignment : assignments) {
                assignment.setFreelancer(null);  // Αποσύνδεση του freelancer από το assignment
                Project project = assignment.getProject();
                project.setAssignment(null);
                assignment.setProject(null);
                projectRepository.save(project);
                assignmentRepository.save(assignment); // Αποθήκευση για την ανανέωση της κατάστασης
            }
            // Διαγραφή των assignments
            assignmentRepository.deleteAll(assignments);
        }
        // Διαγραφή του freelancer
        freelancerRepository.delete(freelancer);

        List<Assignment> allAssignments = assignmentRepository.findAll();
        for (Assignment assignment : allAssignments) {
            if (assignment.getFreelancer() == null) {
                assignmentRepository.delete(assignment);
            }
        }
    }
}
