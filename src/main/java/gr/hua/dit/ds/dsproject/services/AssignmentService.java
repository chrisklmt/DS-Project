package gr.hua.dit.ds.dsproject.services;

import gr.hua.dit.ds.dsproject.entities.Assignment;
import gr.hua.dit.ds.dsproject.repositories.AssignmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
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
}
