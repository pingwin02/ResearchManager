package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentCreateModel;
import lab.jee.project.entity.Project;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToExperimentFunction implements Function<ExperimentCreateModel, Experiment>, Serializable {

    @Override
    public Experiment apply(ExperimentCreateModel m) {
        return Experiment.builder()
                .id(m.getId())
                .description(m.getDescription())
                .success(m.isSuccess())
                .dateConducted(m.getDateConducted())
                .project(Project.builder().id(m.getProject().getId()).build())
                .build();
    }
}
