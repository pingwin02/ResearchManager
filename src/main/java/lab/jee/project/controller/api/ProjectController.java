package lab.jee.project.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab.jee.project.dto.GetProjectResponse;
import lab.jee.project.dto.GetProjectsResponse;
import lab.jee.project.dto.PatchProjectRequest;
import lab.jee.project.dto.PutProjectRequest;

import java.util.UUID;

@Path("")
public interface ProjectController {

    @GET
    @Path("/projects/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetProjectResponse getProject(@PathParam("id") UUID id);

    @GET
    @Path("/projects")
    @Produces(MediaType.APPLICATION_JSON)
    GetProjectsResponse getProjects();

    @PUT
    @Path("/projects/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void createProject(@PathParam("id") UUID id, PutProjectRequest request);

    @PATCH
    @Path("/projects/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateProject(@PathParam("id") UUID id, PatchProjectRequest request);

    @DELETE
    @Path("/projects/{id}")
    void deleteProject(@PathParam("id") UUID id);
}
