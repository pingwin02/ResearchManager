package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentModel;

import java.io.Serializable;
import java.util.function.Function;

public class ExperimentToModelFunction implements Function<Experiment, ExperimentModel>, Serializable {

    @Override
    public ExperimentModel apply(Experiment experimentModel) {
        return ExperimentModel.builder()
                .description(experimentModel.getDescription())
                .success(experimentModel.isSuccess())
                .dateConducted(experimentModel.getDateConducted())
                .projectTitle(experimentModel.getProject() != null ? experimentModel.getProject().getTitle() : null)
                .build();
    }
}
