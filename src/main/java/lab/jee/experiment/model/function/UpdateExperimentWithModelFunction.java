package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentEditModel;
import lab.jee.project.entity.Project;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateExperimentWithModelFunction implements BiFunction<Experiment, ExperimentEditModel, Experiment>, Serializable {

    @Override
    public Experiment apply(Experiment experiment, ExperimentEditModel experimentEditModel) {
        return Experiment.builder()
                .id(experiment.getId())
                .description(experimentEditModel.getDescription())
                .success(experimentEditModel.isSuccess())
                .dateConducted(experimentEditModel.getDateConducted())
                .project(experimentEditModel.getProject() != null ? Project.builder().id(experimentEditModel.getProject().getId()).build() : null)
                .build();
    }
}
