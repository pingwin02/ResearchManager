package lab.jee.experiment.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lab.jee.component.DtoFunctionFactory;
import lab.jee.experiment.controller.api.ExperimentController;
import lab.jee.experiment.dto.GetExperimentResponse;
import lab.jee.experiment.dto.GetExperimentsResponse;
import lab.jee.experiment.dto.PatchExperimentRequest;
import lab.jee.experiment.dto.PutExperimentRequest;
import lab.jee.experiment.service.ExperimentService;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class ExperimentRestController implements ExperimentController {

    private final ExperimentService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Inject
    public ExperimentRestController(
            ExperimentService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public GetExperimentResponse getExperiment(UUID id) {
        return service.find(id).map(factory.experimentToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    public GetExperimentsResponse getExperiments() {
        return factory.experimentsToResponse().apply(service.findAll());
    }

    @Override
    public GetExperimentsResponse getProjectExperiments(UUID projectId) {
        return service.findAllByProject(projectId)
                .map(factory.experimentsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetExperimentsResponse getResearcherExperiments(UUID researcherId) {
        return service.findAllByResearcher(researcherId)
                .map(factory.experimentsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void createExperiment(UUID projectId, UUID experimentId, PutExperimentRequest request) {
        try {
            service.create(factory.requestToExperiment().apply(experimentId, projectId, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ExperimentController.class, "getExperiment")
                    .build(projectId, experimentId)
                    .toString());

            throw new WebApplicationException(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateExperiment(UUID id, PatchExperimentRequest request) {
        service.find(id).ifPresentOrElse(
                experiment -> service.update(factory.updateExperimentWithRequest().apply(experiment, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteExperiment(UUID id) {
        service.find(id).ifPresentOrElse(
                experiment -> service.delete(experiment.getId()),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
