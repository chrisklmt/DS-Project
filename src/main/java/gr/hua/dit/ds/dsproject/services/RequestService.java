package gr.hua.dit.ds.dsproject.services;


import gr.hua.dit.ds.dsproject.entities.Project;
import gr.hua.dit.ds.dsproject.entities.Request;
import gr.hua.dit.ds.dsproject.entities.Status;
import gr.hua.dit.ds.dsproject.repositories.RequestRepository;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.atn.LexerIndexedCustomAction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final ProjectService projectService;

    public RequestService(RequestRepository requestRepository, ProjectService projectService) {
        this.requestRepository = requestRepository;
        this.projectService = projectService;
    }

    @Transactional
    public List<Request> getRequests(){return requestRepository.findAll();}

    @Transactional
    public void saveRequest(Request request) {
        requestRepository.save(request);
    }

    @Transactional
    public Request getRequest(Integer requestId) {
        return requestRepository.findById(requestId).get();
    }

    @Transactional
    public List<Request> getRejectedRequests() {
        List<Request> requests = requestRepository.findAll();
        List<Request> rejectedRequests = new ArrayList<>();
        for(Request r : requests) {
            if(r.getRequestStatus().equals(Status.Rejected)){
                rejectedRequests.add(r);
            }
        }
        return rejectedRequests;
    }
    @Transactional
    public void deleteRequest(Integer requestId) {
        requestRepository.deleteById(requestId);
    }
    @Transactional
    public List<Request> getRequestsByProjectID(Integer projectId) {
        Project project = projectService.getProject(projectId);
        List<Request> requestsForProject = new ArrayList<>();

        for(Request r : requestRepository.findAll()) {
            if(r.getProject().getId().equals(project.getId())) {
                requestsForProject.add(r);
            }
        }
        return requestsForProject;
    }

}
