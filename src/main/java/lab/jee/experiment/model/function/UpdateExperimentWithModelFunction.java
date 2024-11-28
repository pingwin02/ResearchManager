package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentEditModel;
import lab.jee.researcher.entity.Researcher;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateExperimentWithModelFunction implements BiFunction<Experiment,
        ExperimentEditModel, Experiment>, Serializable {

    @Override
    public Experiment apply(Experiment e, ExperimentEditModel m) {
        return Experiment.builder()
                .id(e.getId())
                .description(m.getDescription())
                .success(m.isSuccess())
                .dateConducted(m.getDateConducted())
                .project(e.getProject())
                .researcher(Researcher.builder()
                        .id(e.getResearcher()
                                .getId()).build())
                .version(m.getVersion())
                .creationDateTime(e.getCreationDateTime())
                .modificationDateTime(e.getModificationDateTime())
                .build();
    }
}
