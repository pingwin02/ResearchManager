package lab.jee.experiment.model.function;

import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentsModel;

import java.util.List;
import java.util.function.Function;

public class ExperimentsToModelFunction implements Function<List<Experiment>, ExperimentsModel> {

    @Override
    public ExperimentsModel apply(List<Experiment> experiments) {
        return ExperimentsModel.builder()
                .experiments(experiments.stream()
                        .map(experiment -> ExperimentsModel.Experiment.builder()
                                .id(experiment.getId())
                                .description(experiment.getDescription())
                                .version(experiment.getVersion())
                                .creationDateTime(experiment.getCreationDateTime())
                                .modificationDateTime(experiment.getModificationDateTime())
                                .build())
                        .toList())
                .build();
    }
}
