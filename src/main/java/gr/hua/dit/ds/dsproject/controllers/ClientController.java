package gr.hua.dit.ds.dsproject.controllers;

import gr.hua.dit.ds.dsproject.entities.Project;
import gr.hua.dit.ds.dsproject.entities.Client;
import gr.hua.dit.ds.dsproject.entities.Request;
import gr.hua.dit.ds.dsproject.services.ClientService;
import gr.hua.dit.ds.dsproject.services.ProjectService;
import gr.hua.dit.ds.dsproject.services.RequestService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final ProjectService projectService;
    private final RequestService requestService;

    public ClientController(ClientService clientService, ProjectService projectService, RequestService requestService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.requestService = requestService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showClients(Model model){
        model.addAttribute("clients", clientService.getClients());
        return "client/clients";
    }

    @GetMapping("/my-projects")
    public String showProjects(Model model){
        Client client = clientService.getCurrentClient();
        model.addAttribute("projects", client.getProjects());
        return "project/myprojects";
    }

    @GetMapping("/new")
    public String addClient(Model model){
        Client client = new Client();
        model.addAttribute("client", client);
        return "client/client";
    }

    @PostMapping("/new")
    public String saveClient(@Valid @ModelAttribute("client") Client client,
                              BindingResult theBindingResult, Model model ){
        if (theBindingResult.hasErrors()) {
            System.out.println("error");
            return "client/client";
        } else {
            clientService.saveClient(client);
            model.addAttribute("clients", clientService.getClients());
            return "client/clients";
        }
    }
    @PostMapping("/projectRequests/{projectId}")
    public String showRequests(@PathVariable int projectId,Model model) {
        Project currentProject = projectService.getProject(projectId);
        List<Request> requestsForProject = requestService.getRequestsByProjectID(projectId);

        model.addAttribute("currentProject", currentProject);
        model.addAttribute("requestsForProject", requestsForProject);
        return "request/requestsForProject";
    }
    @Secured("ROLE_CLIENT")
    @GetMapping("/my-profile")
    public String showProfile(Model model) {
        Client client = clientService.getCurrentClient();
        model.addAttribute("client", client);
        return "client/my-profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model) {
        Client client = clientService.getCurrentClient();
        model.addAttribute("client", client);
        return "client/edit-profile";
    }
    @PostMapping("/edit-profile")
    public String updateProfile(@Valid @ModelAttribute("client") Client client,
                                BindingResult theBindingResult) {
        if (theBindingResult.hasErrors()) {
            System.out.println("error");
            return "client/edit-profile";
        }
        clientService.updateClient(client);
        return "client/my-profile";
    }
}
