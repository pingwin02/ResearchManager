package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentModel;

import java.io.Serializable;
import java.util.function.Function;

public class ExperimentToModelFunction implements Function<Experiment, ExperimentModel>, Serializable {

    @Override
    public ExperimentModel apply(Experiment e) {
        return ExperimentModel.builder()
                .description(e.getDescription())
                .success(e.isSuccess())
                .dateConducted(e.getDateConducted())
                .projectTitle(e.getProject().getTitle())
                .researcherLogin(e.getResearcher().getLogin())
                .build();
    }
}
