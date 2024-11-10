package lab.jee.experiment.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lab.jee.component.DtoFunctionFactory;
import lab.jee.experiment.controller.api.ExperimentController;
import lab.jee.experiment.dto.GetExperimentResponse;
import lab.jee.experiment.dto.GetExperimentsResponse;
import lab.jee.experiment.dto.PatchExperimentRequest;
import lab.jee.experiment.dto.PutExperimentRequest;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.researcher.entity.ResearcherRole;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed({ResearcherRole.ASSISTANT, ResearcherRole.LEAD_RESEARCHER})
public class ExperimentRestController implements ExperimentController {

    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private ExperimentService service;
    private HttpServletResponse response;

    @Inject
    public ExperimentRestController(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(ExperimentService service) {
        this.service = service;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @RolesAllowed({ResearcherRole.ASSISTANT, ResearcherRole.LEAD_RESEARCHER})
    @Override
    public GetExperimentResponse getExperiment(UUID id) {
        return service.findForCallerPrincipal(id).map(factory.experimentToResponse()).orElseThrow(NotFoundException::new);
    }

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    @Override
    public GetExperimentsResponse getExperiments() {
        return factory.experimentsToResponse().apply(service.findAllForCallerPrincipal());
    }

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    @Override
    public GetExperimentsResponse getProjectExperiments(UUID projectId) {
        return service.findAllByProject(projectId)
                .map(factory.experimentsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    @Override
    public GetExperimentsResponse getResearcherExperiments(UUID researcherId) {
        return service.findAllByResearcher(researcherId)
                .map(factory.experimentsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @RolesAllowed({ResearcherRole.ASSISTANT, ResearcherRole.LEAD_RESEARCHER})
    @Override
    public void createExperiment(UUID projectId, UUID experimentId, PutExperimentRequest request) {
        try {
            service.createForCallerPrincipal(factory.requestToExperiment().apply(experimentId, projectId, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ExperimentController.class, "getExperiment")
                    .build(projectId, experimentId)
                    .toString());

            throw new WebApplicationException(HttpServletResponse.SC_CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
        }
    }

    @RolesAllowed({ResearcherRole.ASSISTANT, ResearcherRole.LEAD_RESEARCHER})
    @Override
    public void updateExperiment(UUID id, PatchExperimentRequest request) {
        service.findForCallerPrincipal(id).ifPresentOrElse(
                experiment -> {
                    try {
                        service.update(factory.updateExperimentWithRequest().apply(experiment, request));
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @RolesAllowed({ResearcherRole.ASSISTANT, ResearcherRole.LEAD_RESEARCHER})
    @Override
    public void deleteExperiment(UUID id) {
        service.findForCallerPrincipal(id).ifPresentOrElse(
                experiment -> {
                    try {
                        service.delete(experiment.getId());
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
