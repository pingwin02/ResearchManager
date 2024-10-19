package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentCreateModel;
import lab.jee.project.entity.Project;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToExperimentFunction implements Function<ExperimentCreateModel, Experiment>, Serializable {

    @Override
    public Experiment apply(ExperimentCreateModel experimentModel) {
        return Experiment.builder()
                .id(experimentModel.getId())
                .description(experimentModel.getDescription())
                .success(experimentModel.isSuccess())
                .dateConducted(experimentModel.getDateConducted())
                .project(experimentModel.getProject() != null ? Project.builder().id(experimentModel.getProject().getId()).build() : null)
                .build();
    }
}
