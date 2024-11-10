package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentEditModel;
import lab.jee.researcher.model.function.ResearcherToModelFunction;

import java.io.Serializable;
import java.util.function.Function;

public class ExperimentToEditModelFunction implements Function<Experiment, ExperimentEditModel>, Serializable {

    private final ResearcherToModelFunction function;

    public ExperimentToEditModelFunction(ResearcherToModelFunction function) {
        this.function = function;
    }

    @Override
    public ExperimentEditModel apply(Experiment e) {
        return ExperimentEditModel.builder()
                .description(e.getDescription())
                .success(e.isSuccess())
                .dateConducted(e.getDateConducted())
                .researcher(function.apply(e.getResearcher()))
                .build();
    }
}
