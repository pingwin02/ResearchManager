package lab.jee.project.controller.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lab.jee.component.DtoFunctionFactory;
import lab.jee.controller.servlet.exception.NotFoundException;
import lab.jee.project.controller.api.ProjectController;
import lab.jee.project.dto.GetProjectResponse;
import lab.jee.project.dto.GetProjectsResponse;
import lab.jee.project.dto.PatchProjectRequest;
import lab.jee.project.dto.PutProjectRequest;
import lab.jee.project.service.ProjectService;

import java.util.UUID;

@Path("")
public class ProjectRestController implements ProjectController {

    private final ProjectService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ProjectRestController(
            ProjectService projectService,
            DtoFunctionFactory dtoFunctionFactory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = projectService;
        this.factory = dtoFunctionFactory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetProjectResponse getProject(UUID id) {
        return factory.projectToResponse().apply(service.find(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetProjectsResponse getProjects() {
        return factory.projectsToResponse().apply(service.findAll());
    }

    @Override
    public void createProject(UUID id, PutProjectRequest request) {
        try {
            service.create(factory.requestToProject().apply(id, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ProjectController.class, "getProject")
                    .build(id)
                    .toString());

            throw new WebApplicationException(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateProject(UUID id, PatchProjectRequest request) {
        service.find(id).ifPresentOrElse(
                project -> service.update(factory.updateProjectWithRequest().apply(id, request)),
                () -> {
                    throw new NotFoundException();
                }
        );

    }

    @Override
    public void deleteProject(UUID id) {
        service.find(id).ifPresentOrElse(
                project -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
