package lab.jee.researcher.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lab.jee.component.DtoFunctionFactory;
import lab.jee.researcher.controller.api.ResearcherController;
import lab.jee.researcher.dto.*;
import lab.jee.researcher.service.AvatarService;
import lab.jee.researcher.service.ResearcherService;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public class ResearcherRestController implements ResearcherController {

    private final ResearcherService service;

    private final AvatarService avatarService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Inject
    public ResearcherRestController(
            ResearcherService service,
            AvatarService avatarService,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.avatarService = avatarService;
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public GetResearcherResponse getResearcher(UUID id) {
        return service.find(id).map(factory.researcherToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    public GetResearchersResponse getResearchers() {
        return factory.researchersToResponse().apply(service.findAll());
    }

    @Override
    public void updateResearcher(UUID id, PatchResearcherRequest request) {
        service.find(id).ifPresentOrElse(researcher -> {
            service.update(factory.updateResearcherWithRequest().apply(researcher, request));
        }, () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public void createResearcher(UUID id, PutResearcherRequest request) {
        try {
            service.create(factory.requestToResearcher().apply(id, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ResearcherController.class, "getResearcher")
                    .build(id)
                    .toString());

            throw new WebApplicationException(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateResearcherPassword(UUID id, PutPasswordRequest request) {
        service.find(id).ifPresentOrElse(
                researcher -> service.update(factory.updateResearcherPasswordWithRequest().apply(researcher, request)), () -> {
                    throw new NotFoundException();
                });
    }

    @Override
    public void deleteResearcher(UUID id) {
        service.find(id).ifPresentOrElse(researcher -> {
            avatarService.deleteAvatar(id);
            service.delete(id);
        }, () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public byte[] getResearcherAvatar(UUID id) {
        return service.find(id).map(researcher ->
                avatarService.getAvatar(id).orElseThrow(NotFoundException::new)
        ).orElseThrow(NotFoundException::new);
    }

    @Override
    public void createResearcherAvatar(UUID id, InputStream avatar) {
        service.find(id).ifPresentOrElse(researcher -> {
            try {
                avatarService.createAvatar(id, avatar);

                response.setHeader("Location", uriInfo.getBaseUriBuilder()
                        .path(ResearcherController.class, "getResearcher")
                        .build(id)
                        .toString());

                throw new WebApplicationException(HttpServletResponse.SC_CREATED);
            } catch (IllegalStateException ex) {
                throw new BadRequestException();
            }
        }, () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public void updateResearcherAvatar(UUID id, InputStream avatar) {
        service.find(id).ifPresentOrElse(researcher -> {
            try {
                avatarService.updateAvatar(researcher.getId(), avatar);
            } catch (IllegalStateException ex) {
                throw new BadRequestException();
            }
        }, () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public void deleteResearcherAvatar(UUID id) {
        service.find(id).ifPresentOrElse(researcher -> {
            try {
                avatarService.deleteAvatar(researcher.getId());
            } catch (IllegalStateException ex) {
                throw new BadRequestException();
            }
        }, () -> {
            throw new NotFoundException();
        });
    }
}
