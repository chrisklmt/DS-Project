package gr.hua.dit.ds.dsproject.controllers;


import gr.hua.dit.ds.dsproject.entities.Project;
import gr.hua.dit.ds.dsproject.entities.Request;
import gr.hua.dit.ds.dsproject.services.RequestService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("")
    public String showRequests(Model model){
        model.addAttribute("requests", requestService.getRequests());
        return "request/requests";
    }

    @GetMapping("/rejectedRequests")
    public String showRejectedRequest(Model model){
        model.addAttribute("rejectedRequests", requestService.getRejectedRequests());
        return "request/rejectedRequests";
    }

    @PostMapping("/rejectedRequests/{requestId}")
    public String deleteRequest(@PathVariable int requestId, Model model){
        Request request = requestService.getRequest(requestId);
        requestService.deleteRequest(requestId);
        model.addAttribute("rejectedRequests", requestService.getRejectedRequests());
        return "request/rejectedRequests";
    }




    @GetMapping("/new")
    public String addRequest(Model model){
        Request request = new Request();
        model.addAttribute("request", request);
        return "request/requests";/////////////////////s
    }

    @PostMapping("/new")
    public String saveRequest(@Valid @ModelAttribute("request") Request request,
                              BindingResult theBindingResult, Model model ){
        if (theBindingResult.hasErrors()) {
            System.out.println("error");
            return "request/requests";///////////////s
        } else {
            requestService.saveRequest(request);
            model.addAttribute("requests", requestService.getRequests());
            return "request/requests";
        }
    }

}
