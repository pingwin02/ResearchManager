package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class ExperimentToEditModelFunction implements Function<Experiment, ExperimentEditModel>, Serializable {

    @Override
    public ExperimentEditModel apply(Experiment m) {
        return ExperimentEditModel.builder()
                .description(m.getDescription())
                .success(m.isSuccess())
                .dateConducted(m.getDateConducted())
                .build();
    }
}
