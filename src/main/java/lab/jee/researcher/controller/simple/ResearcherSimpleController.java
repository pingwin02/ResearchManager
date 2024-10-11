package lab.jee.researcher.controller.simple;

import lab.jee.component.DtoFunctionFactory;
import lab.jee.controller.servlet.exception.BadRequestException;
import lab.jee.controller.servlet.exception.NotFoundException;
import lab.jee.researcher.controller.api.ResearcherController;
import lab.jee.researcher.dto.*;
import lab.jee.researcher.service.AvatarService;
import lab.jee.researcher.service.ResearcherService;

import java.io.InputStream;
import java.util.UUID;

public class ResearcherSimpleController implements ResearcherController {

    private final ResearcherService service;

    private final AvatarService avatarService;

    private final DtoFunctionFactory factory;

    public ResearcherSimpleController(ResearcherService service, AvatarService avatarService, DtoFunctionFactory factory) {
        this.avatarService = avatarService;
        this.service = service;
        this.factory = factory;
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
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void updateResearcherPassword(UUID id, PutPasswordRequest request) {
        service.find(id).ifPresentOrElse(researcher -> {
            service.update(factory.updateResearcherPasswordWithRequest().apply(researcher, request));
        }, () -> {
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
                avatarService.updateAvatar(id, avatar);
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
                avatarService.deleteAvatar(id);
            } catch (IllegalStateException ex) {
                throw new BadRequestException();
            }
        }, () -> {
            throw new NotFoundException();
        });
    }
}
