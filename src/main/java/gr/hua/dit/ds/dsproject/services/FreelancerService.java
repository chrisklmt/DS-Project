package gr.hua.dit.ds.dsproject.services;

import gr.hua.dit.ds.dsproject.entities.Assignment;
import gr.hua.dit.ds.dsproject.entities.Freelancer;
import gr.hua.dit.ds.dsproject.entities.User;
import gr.hua.dit.ds.dsproject.repositories.AssignmentRepository;
import gr.hua.dit.ds.dsproject.repositories.FreelancerRepository;
import gr.hua.dit.ds.dsproject.repositories.UserRepository;
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
    private final AssignmentService assignmentService;

    public FreelancerService(FreelancerRepository freelancerRepository, UserRepository userRepository, AssignmentRepository assignmentRepository, AssignmentService assignmentService) {
        this.freelancerRepository = freelancerRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
        this.assignmentService = assignmentService;
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
    public void verifyFreelancer(Integer freelancerId) {
        Freelancer freelancer = getFreelancer(freelancerId);
        freelancer.setVerified(true);
    }

    @Transactional
    public void deleteFreelancer(Integer freelancerId) {
        Freelancer freelancer = freelancerRepository.findById(freelancerId).orElse(null);
        if (freelancer != null) {
            List<Assignment> assignments = freelancer.getAssignments();

            if (!assignments.isEmpty()) {
                System.out.println("Freelancer has assignments: " + assignments.size());
                assignmentService.deleteAssignments(assignments);
                }
            freelancerRepository.delete(freelancer);
            System.out.println("Freelancer deleted successfully.");
        }
    }

}
