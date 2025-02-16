package gr.hua.dit.ds.dsproject.services;

import gr.hua.dit.ds.dsproject.entities.Assignment;
import gr.hua.dit.ds.dsproject.entities.Freelancer;
import gr.hua.dit.ds.dsproject.repositories.AssignmentRepository;
import gr.hua.dit.ds.dsproject.repositories.FreelancerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final FreelancerRepository freelancerRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, FreelancerRepository freelancerRepository) {
        this.assignmentRepository = assignmentRepository;
        this.freelancerRepository = freelancerRepository;
    }

    @Transactional
    public List<Assignment> getAssignments(){
        return assignmentRepository.findAll();
    }

    @Transactional
    public void saveAssignment(Assignment assignment) {
        // Check if the project already has an assignment
        if (assignment.getProject() != null && assignment.getProject().getAssignment() != null) {
            throw new IllegalStateException("This project is already assigned to another freelancer.");
        }
        assignmentRepository.save(assignment);
    }

    @Transactional
    public Assignment getAssignment(Integer assignmentId) {
        return assignmentRepository.findById(assignmentId).get();
    }

    @Transactional
    public List<Assignment> getAssignmentsByProjectId(Integer projectId) {
        return assignmentRepository.findByProjectId(projectId);
    }

    @Transactional
    public void deleteAssignments(List<Assignment> assignments) {
        assignmentRepository.deleteAll(assignments);
        }
}
