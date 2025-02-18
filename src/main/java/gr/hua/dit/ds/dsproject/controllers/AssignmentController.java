package gr.hua.dit.ds.dsproject.controllers;

import gr.hua.dit.ds.dsproject.entities.*;
import gr.hua.dit.ds.dsproject.services.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final FreelancerService freelancerService;
    private final ProjectService projectService;
    private final RequestService requestService;

    public AssignmentController(AssignmentService assignmentService, FreelancerService freelancerService,
                                ProjectService projectService, RequestService requestService) {
        this.assignmentService = assignmentService;
        this.freelancerService = freelancerService;
        this.projectService = projectService;
        this.requestService = requestService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showAssignments(Model model){
        model.addAttribute("assignments", assignmentService.getAssignments());
        return "assignment/assignments";
    }

    @PostMapping("/assignFreelancer/{requestId}")
    public String assignFreelancerToProject(@PathVariable int requestId) {
        Request request = requestService.getRequest(requestId);
        Freelancer freelancer = freelancerService.getFreelancer(request.getFreelancer().getId());
        Project project = projectService.getProject(request.getProject().getId());

        Assignment assignment = new Assignment();

        List<Assignment> freelancer_assignments = freelancer.getAssignments();
        freelancer_assignments.add(assignment);
        freelancer.setAssignments(freelancer_assignments);
        assignment.setFreelancer(freelancer);

        project.setAssignment(assignment);
        assignment.setProject(project);

        List<Request> allRequestsForProject = project.getRequests();
        List<Request> requestsToUpdate = new ArrayList<>(allRequestsForProject);

        for (Request req : requestsToUpdate) {
            if (req.getId().equals(requestId)) {
                req.setRequestStatus(Status.Accepted);
            } else {
                req.setRequestStatus(Status.Rejected);
            }
            requestService.saveRequest(req);
        }
        return "redirect:/project/projectsAssigned";
    }
}
