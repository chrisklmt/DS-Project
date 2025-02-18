package gr.hua.dit.ds.dsproject.controllers;

import gr.hua.dit.ds.dsproject.entities.Client;
import gr.hua.dit.ds.dsproject.entities.Freelancer;
import gr.hua.dit.ds.dsproject.entities.User;
import gr.hua.dit.ds.dsproject.services.ClientService;
import gr.hua.dit.ds.dsproject.services.FreelancerService;
import gr.hua.dit.ds.dsproject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class UserController {

    private final ClientService clientService;
    private final FreelancerService freelancerService;
    private UserService userService;

    public UserController(UserService userService, ClientService clientService, FreelancerService freelancerService) {
        this.userService = userService;
        this.clientService = clientService;
        this.freelancerService = freelancerService;
    }

    @GetMapping("/registerClient")
    public String registerClient(Model model) {
        User user = new User();
        Client client = new Client();
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        return "auth/register_client";
    }

    @PostMapping("/saveUserClient")
    public String saveUserClient(@Valid @ModelAttribute("user") User user, BindingResult userBindingResult,
                                 @Valid @ModelAttribute("client") Client client,
                                 BindingResult clientBindingResult, Model model){

        if (userBindingResult.hasErrors() || clientBindingResult.hasErrors()) {
            System.out.println("Error");
            return "auth/register_client";
        }

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            String message = "Username is already in use. Please choose another one.";
            model.addAttribute("msg", message);
            return "auth/register_client";
        }

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            userBindingResult.rejectValue("email", "error.user", "Email is already in use. Please use another one.");
            return "auth/register_client";
        }

        user.setClient(client);
        client.setUser(user);

        Integer id = userService.saveUser(user, "ROLE_CLIENT");
        clientService.saveClient(client);

        String message = "User '" + id + "' saved successfully!";
        model.addAttribute("msg", message);

        return "index";
    }

    @GetMapping("/registerFreelancer")
    public String registerFreelancer(Model model) {
        User user = new User();
        Freelancer freelancer = new Freelancer();
        model.addAttribute("user", user);
        model.addAttribute("freelancer", freelancer);
        return "auth/register_freelancer";
    }

    @PostMapping("/saveUserFreelancer")
    public String saveUserFreelancer(@Valid @ModelAttribute("user") User user, BindingResult userBindingResult,
                                     @Valid @ModelAttribute("freelancer") Freelancer freelancer,
                                     BindingResult freelancerBindingResult, Model model){

        if (userBindingResult.hasErrors() || freelancerBindingResult.hasErrors()) {
            System.out.println("Error");
            return "auth/register_freelancer";
        } if (userService.findByUsername(user.getUsername()).isPresent()) {
            userBindingResult.rejectValue("username", "error.user", "Username is already in use. Please choose another one.");
            return "auth/register_freelancer";
        }
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            userBindingResult.rejectValue("email", "error.user", "Email is already in use. Please use another one.");
            return "auth/register_freelancer";
        }
        user.setFreelancer(freelancer);
        freelancer.setUser(user);

        Integer id = userService.saveUser(user, "ROLE_FREELANCER");
        freelancerService.saveFreelancer(freelancer);

        String message = "User '" + id + "' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }
}