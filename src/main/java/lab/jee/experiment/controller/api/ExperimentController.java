package lab.jee.experiment.controller.api;

import lab.jee.experiment.dto.GetExperimentResponse;
import lab.jee.experiment.dto.GetExperimentsResponse;

import java.util.UUID;

public interface ExperimentController {

    GetExperimentResponse getExperiment(UUID id);

    GetExperimentsResponse getExperiments();

}
