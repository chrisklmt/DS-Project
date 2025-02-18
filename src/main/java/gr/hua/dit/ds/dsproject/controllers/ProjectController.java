package gr.hua.dit.ds.dsproject.controllers;

import gr.hua.dit.ds.dsproject.entities.Client;
import gr.hua.dit.ds.dsproject.entities.Freelancer;
import gr.hua.dit.ds.dsproject.services.ClientService;
import gr.hua.dit.ds.dsproject.services.FreelancerService;
import gr.hua.dit.ds.dsproject.services.ProjectService;
import gr.hua.dit.ds.dsproject.entities.Project;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static gr.hua.dit.ds.dsproject.entities.Status.Accepted;
import static gr.hua.dit.ds.dsproject.entities.Status.Rejected;


@Controller
@RequestMapping("/project")
public class  ProjectController {
    private final ProjectService projectService;
    private final ClientService clientService;
    private final FreelancerService freelancerService;

    public ProjectController(ProjectService projectService, ClientService clientService, FreelancerService freelancerService) {
        this.projectService = projectService;
        this.clientService = clientService;
        this.freelancerService = freelancerService;
    }
    
    @Secured("ROLE_CLIENT")
    @GetMapping("/new")
    public String addProject(Model model){
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("msg","");
        return "project/project";
    }

    @Secured("ROLE_CLIENT")
    @PostMapping("/new")
    public String saveProject(@Valid @ModelAttribute("project") Project project, BindingResult theBindingResult, Model model ){
        if (theBindingResult.hasErrors()) {
            System.out.println("error");
            return "project/project";
        }else {
            Client currentClient = clientService.getCurrentClient();
            project.setClient(currentClient);

            System.out.printf(project.getProjectStatus().toString());
            projectService.saveProject(project);
            model.addAttribute("projects", projectService.getProjects());
            return "redirect:/client/my-projects";
            /*
            Εδώ κάνουμε redirect έτσι ώστε σε περίπτωση που ο client
            κάνει reload page, να μην ξανά στείλουμε post request και
            να καταγράψουμε το ίδιο project πολλές φορές.
            */
        }
    }

    @PostMapping("/assignRequest/{projectId}")
    public String assignRequestToProject(@PathVariable int projectId, Model model) {

        Freelancer freelancer = freelancerService.getCurrentFreelancer();
        projectService.assignRequestToProject(projectId, freelancer);

        model.addAttribute("freelancerRequests", freelancer.getRequests());
        return "request/myrequests";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/projectsPending")
    public String showPendingProjects(Model model) {
        model.addAttribute("projects", projectService.getProjectsPending());
        return "project/projectsPending";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("accept-project/{projectId}")
    public String acceptProject(@PathVariable int projectId,Model model) {
        Project project = projectService.getProject(projectId);
        project.setProjectStatus(Accepted);
        projectService.saveProject(project);
        model.addAttribute("projects", projectService.getProjectsPending());
        return "project/projectsPending";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("reject-project/{projectId}")
    public String rejectProject(@PathVariable int projectId,Model model) {
        Project project = projectService.getProject(projectId);
        project.setProjectStatus(Rejected);
        projectService.saveProject(project);
        model.addAttribute("projects", projectService.getProjectsPending());
        return "project/projectsPending";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/projectsRejected")
    public String showRejectedProjects(Model model) {
        model.addAttribute("projects", projectService.getRejectedProjects());
        return "project/projectsRejected";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("deleteProject/{projectId}")
    public String deleteProjectRejected(@PathVariable int projectId, Model model) {
        projectService.deleteProject(projectId);
        model.addAttribute("projects", projectService.getRejectedProjects());
        return "project/projectsRejected";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("deleteProjectOutdated/{projectId}")
    public String deleteProjectOutdated(@PathVariable int projectId, Model model) {
        projectService.deleteProject(projectId);
        Client currentClient = clientService.getCurrentClient();
        model.addAttribute("projectsUnassignedAndOutdated", projectService.getUnassignedAndOutdatedProjects(currentClient));
        return "project/unassignedANDoutdated";
    }

    @Secured("ROLE_CLIENT")
    @GetMapping("/projectsUnassigned")
    public String showUnassignedProjects(Model model) {
        Client currentClient = clientService.getCurrentClient();
        model.addAttribute("projectsUnassigned", projectService.getUnassignedProjects(currentClient));
        return "project/projectsUnassigned";
    }

    @Secured("ROLE_CLIENT")
    @GetMapping("/projectsAssigned")
    public String showAssignedProjects(Model model) {
        Client currentClient = clientService.getCurrentClient();
        model.addAttribute("assignedProjects", projectService.getAssignedProjects(currentClient));
        return "project/projectsAssigned";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showAcceptedProjects(Model model) {
        model.addAttribute("acceptedProjects", projectService.getAcceptedProjects());
        return "project/projects";
    }

    @Secured("ROLE_CLIENT")
    @GetMapping("/unassignedANDoutdated")
    public String showUnassignedAndOutdatedProjects(Model model) {
        Client currentClient = clientService.getCurrentClient();
        model.addAttribute("projectsUnassignedAndOutdated", projectService.getUnassignedAndOutdatedProjects(currentClient));
        return "project/unassignedANDoutdated";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/projectOutdated")
    public String showProjectsOutdated(Model model) {
        model.addAttribute("projects", projectService.getAllOutdatedProjects());
        return "project/projectOutdated";
    }

    @Secured("ROLE_CLIENT")
    @GetMapping("/completedProjects")
    public String showCompletedProjects(Model model) {
        Client currentClient = clientService.getCurrentClient();
        model.addAttribute("completedProjects", projectService.getCompletedProjects(currentClient));
        return "project/completedProjects";
    }
}

