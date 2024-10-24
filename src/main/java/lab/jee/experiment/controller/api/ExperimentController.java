package lab.jee.experiment.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab.jee.experiment.dto.GetExperimentResponse;
import lab.jee.experiment.dto.GetExperimentsResponse;
import lab.jee.experiment.dto.PatchExperimentRequest;
import lab.jee.experiment.dto.PutExperimentRequest;

import java.util.UUID;

@Path("")
public interface ExperimentController {

    @GET
    @Path("/experiments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetExperimentResponse getExperiment(@PathParam("id") UUID id);

    @GET
    @Path("/experiments")
    @Produces(MediaType.APPLICATION_JSON)
    GetExperimentsResponse getExperiments();

    @GET
    @Path("/projects/{projectId}/experiments")
    @Produces(MediaType.APPLICATION_JSON)
    GetExperimentsResponse getProjectExperiments(@PathParam("projectId") UUID projectId);

    @GET
    @Path("/researchers/{researcherId}/experiments")
    @Produces(MediaType.APPLICATION_JSON)
    GetExperimentsResponse getResearcherExperiments(@PathParam("researcherId") UUID projectId);

    @PUT
    @Path("/projects/{projectId}/experiments/{experimentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    void createExperiment(
            @PathParam("projectId") UUID projectId,
            @PathParam("experimentId") UUID experimentId,
            PutExperimentRequest request
    );

    @PATCH
    @Path("/projects/{projectId}/experiments/{experimentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateExperiment(
            @PathParam("projectId") UUID projectId,
            @PathParam("experimentId") UUID experimentId,
            PatchExperimentRequest request
    );

    @DELETE
    @Path("/experiments/{id}")
    void deleteExperiment(@PathParam("id") UUID id);
}
