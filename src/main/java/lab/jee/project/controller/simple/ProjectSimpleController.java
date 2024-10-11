package lab.jee.project.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lab.jee.component.DtoFunctionFactory;
import lab.jee.controller.servlet.exception.NotFoundException;
import lab.jee.project.controller.api.ProjectController;
import lab.jee.project.dto.GetProjectResponse;
import lab.jee.project.dto.GetProjectsResponse;
import lab.jee.project.service.ProjectService;

import java.util.UUID;

@RequestScoped
public class ProjectSimpleController implements ProjectController {

    private final ProjectService service;

    private final DtoFunctionFactory factory;

    @Inject
    public ProjectSimpleController(ProjectService projectService, DtoFunctionFactory dtoFunctionFactory) {
        this.service = projectService;
        this.factory = dtoFunctionFactory;
    }

    @Override
    public GetProjectResponse getProject(UUID id) {
        return factory.projectToResponse().apply(service.find(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetProjectsResponse getProjects() {
        return factory.projectsToResponse().apply(service.findAll());
    }
}
