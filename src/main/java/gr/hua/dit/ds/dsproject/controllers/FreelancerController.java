package gr.hua.dit.ds.dsproject.controllers;

import gr.hua.dit.ds.dsproject.entities.Freelancer;
import gr.hua.dit.ds.dsproject.entities.Project;
import gr.hua.dit.ds.dsproject.services.FreelancerService;
import gr.hua.dit.ds.dsproject.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/freelancer")
public class FreelancerController {
    private final FreelancerService freelancerService;
    private final ProjectService projectService;

    public FreelancerController(FreelancerService freelancerService, ProjectService projectService) {
        this.freelancerService = freelancerService;
        this.projectService = projectService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showFreelancers(Model model){
        model.addAttribute("freelancers", freelancerService.getFreelancers());
        return "freelancer/freelancers";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/not-verified")
    public String showNotVerifiedFreelancers(Model model){
        model.addAttribute("freelancers", freelancerService.getNotVerifiedFreelancer());
        return "freelancer/notVerifiedFreelancers";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/verify/{freelancer_id}")
    public String changeVerifiedStatus(@PathVariable int freelancer_id,Model model){
        Freelancer freelancer = freelancerService.getFreelancer(freelancer_id);
        freelancer.setVerified(true);
        freelancerService.saveFreelancer(freelancer);
        model.addAttribute("freelancers", freelancerService.getNotVerifiedFreelancer());
        return "freelancer/notVerifiedFreelancers";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/delete/{freelancer_id}")
    public String deleteFreelancer(@PathVariable int freelancer_id,Model model){

        freelancerService.deleteFreelancer(freelancer_id);
        model.addAttribute("freelancers", freelancerService.getFreelancers());
        return "freelancer/freelancers";
    }

    @GetMapping("/new")
    public String addFreelancer(Model model){
        Freelancer freelancer = new Freelancer();
        model.addAttribute("freelancer", freelancer);
        return "freelancer/freelancer";
    }

    @PostMapping("/new")
    public String saveFreelancer(@Valid @ModelAttribute("freelancer") Freelancer freelancer,
                              BindingResult theBindingResult, Model model ){
        if (theBindingResult.hasErrors()) {
            System.out.println("error");
            return "freelancer/freelancer";
        } else {
            freelancerService.saveFreelancer(freelancer);
            model.addAttribute("freelancers", freelancerService.getFreelancers());
            return "freelancer/freelancers";
        }
    }

    @Secured("ROLE_FREELANCER")
    @GetMapping("/projects")
    public String showProjects(Model model) {
        Freelancer freelancer = freelancerService.getCurrentFreelancer();
        List<Project> acceptedProjects = projectService.getAcceptedProjects();
        List<Project> requestedProjects = projectService.getRequestedProjects(acceptedProjects,freelancer.getRequests());
        List<Project> notRequestedNotAssignedProjects = projectService.getNotRequestedAndUnassignedProjects(acceptedProjects, requestedProjects);
        List<Project> notOutdated = projectService.getProjectNotOutDated(notRequestedNotAssignedProjects);

        model.addAttribute("notRequestedProjects", notOutdated);
        model.addAttribute("freelancerVerified", freelancer.getVerified());
        return "project/projectsForFreelancer";
    }

    @Secured("ROLE_FREELANCER")
    @GetMapping("/requests")
    public String showRequests(Model model) {
        Freelancer freelancer = freelancerService.getCurrentFreelancer();

        model.addAttribute("freelancerRequests", freelancer.getRequests());
        return "request/myrequests";
    }

    @Secured("ROLE_FREELANCER")
    @GetMapping("/my-assignments")
    public String showMyAssignments(Model model) {
        Freelancer freelancer = freelancerService.getCurrentFreelancer();
        model.addAttribute("assignments", freelancer.getAssignments());
        return "assignment/myassignments";
    }

    @Secured("ROLE_FREELANCER")
    @GetMapping("/my-profile")
    public String showProfile(Model model) {
        Freelancer freelancer = freelancerService.getFreelancer(freelancerService.getCurrentFreelancer().getId());
        model.addAttribute("freelancer", freelancer);
        return "freelancer/my-profile";
    }

    @Secured("ROLE_FREELANCER")
    @GetMapping("/edit-profile")
    public String editProfile(Model model) {
        Freelancer freelancer = freelancerService.getFreelancer(freelancerService.getCurrentFreelancer().getId());
        model.addAttribute("freelancer", freelancer);
        return "freelancer/edit-profile";
    }

    @Secured("ROLE_FREELANCER")
    @PostMapping("/edit-profile")
    public String updateProfile(@Valid @ModelAttribute("freelancer") Freelancer freelancer,
                              BindingResult theBindingResult) {
        if (theBindingResult.hasErrors()) {
            System.out.println("error");
            return "freelancer/edit-profile";
        }
        freelancerService.updateFreelancer(freelancer);
        return "freelancer/my-profile";
    }
}