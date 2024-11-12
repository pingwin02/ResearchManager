package lab.jee.project.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lab.jee.component.DtoFunctionFactory;
import lab.jee.project.controller.api.ProjectController;
import lab.jee.project.dto.GetProjectResponse;
import lab.jee.project.dto.GetProjectsResponse;
import lab.jee.project.dto.PatchProjectRequest;
import lab.jee.project.dto.PutProjectRequest;
import lab.jee.project.service.ProjectService;
import lab.jee.researcher.entity.ResearcherRole;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(ResearcherRole.ASSISTANT)
public class ProjectRestController implements ProjectController {

    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private ProjectService service;
    private HttpServletResponse response;

    @Inject
    public ProjectRestController(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(ProjectService service) {
        this.service = service;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public GetProjectResponse getProject(UUID id) {
        return factory.projectToResponse().apply(service.find(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetProjectsResponse getProjects() {
        return factory.projectsToResponse().apply(service.findAll());
    }

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    @Override
    public void createProject(UUID id, PutProjectRequest request) {
        try {
            service.create(factory.requestToProject().apply(id, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ProjectController.class, "getProject")
                    .build(id)
                    .toString());

            throw new WebApplicationException(HttpServletResponse.SC_CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
        }
    }

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    @Override
    public void updateProject(UUID id, PatchProjectRequest request) {
        service.find(id).ifPresentOrElse(
                project -> service.update(factory.updateProjectWithRequest().apply(project, request)),
                () -> {
                    throw new NotFoundException();
                }
        );

    }

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    @Override
    public void deleteProject(UUID id) {
        service.find(id).ifPresentOrElse(
                project -> service.delete(project.getId()),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
