package lab.jee.researcher.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab.jee.researcher.dto.*;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface ResearcherController {

    @GET
    @Path("/researchers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetResearcherResponse getResearcher(@PathParam("id") UUID id);

    @GET
    @Path("/researchers")
    @Produces(MediaType.APPLICATION_JSON)
    GetResearchersResponse getResearchers();

    @PUT
    @Path("/researchers/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void createResearcher(@PathParam("id") UUID id, PutResearcherRequest request);

    @PATCH
    @Path("/researchers/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateResearcher(@PathParam("id") UUID id, PatchResearcherRequest request);

    void updateResearcherPassword(UUID id, PutPasswordRequest request);

    @DELETE
    @Path("/researchers/{id}")
    void deleteResearcher(@PathParam("id") UUID id);

    @GET
    @Path("/researchers/{id}/avatar")
    @Produces("image/png")
    byte[] getResearcherAvatar(@PathParam("id") UUID id);

    @PUT
    @Path("/researchers/{id}/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void createResearcherAvatar(
            @PathParam("id") UUID id,
            @SuppressWarnings("RestParamTypeInspection")
            @FormParam("avatar") InputStream inputStream);

    @PATCH
    @Path("/researchers/{id}/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void updateResearcherAvatar(
            @PathParam("id") UUID id,
            @SuppressWarnings("RestParamTypeInspection")
            @FormParam("avatar") InputStream inputStream);

    @DELETE
    @Path("/researchers/{id}/avatar")
    void deleteResearcherAvatar(@PathParam("id") UUID id);
}
