package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentEditModel;
import lab.jee.project.model.ProjectModel;

import java.io.Serializable;
import java.util.function.Function;

public class ExperimentToEditModelFunction implements Function<Experiment, ExperimentEditModel>, Serializable {

    @Override
    public ExperimentEditModel apply(Experiment experimentModel) {
        return ExperimentEditModel.builder()
                .description(experimentModel.getDescription())
                .success(experimentModel.isSuccess())
                .dateConducted(experimentModel.getDateConducted())
                .project(experimentModel.getProject() != null ? ProjectModel.builder().id(experimentModel.getProject().getId()).build() : null)
                .build();
    }
}
