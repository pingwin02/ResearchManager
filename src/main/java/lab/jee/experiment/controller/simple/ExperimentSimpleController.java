package lab.jee.experiment.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lab.jee.component.DtoFunctionFactory;
import lab.jee.controller.servlet.exception.NotFoundException;
import lab.jee.experiment.controller.api.ExperimentController;
import lab.jee.experiment.dto.GetExperimentResponse;
import lab.jee.experiment.dto.GetExperimentsResponse;
import lab.jee.experiment.service.ExperimentService;

import java.util.UUID;

@RequestScoped
public class ExperimentSimpleController implements ExperimentController {

    private final ExperimentService service;

    private final DtoFunctionFactory factory;

    @Inject
    public ExperimentSimpleController(ExperimentService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }


    @Override
    public GetExperimentResponse getExperiment(UUID id) {
        return service.find(id).map(factory.experimentToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    public GetExperimentsResponse getExperiments() {
        return factory.experimentsToResponse().apply(service.findAll());
    }
}
