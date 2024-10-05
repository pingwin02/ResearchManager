package lab.jee.experiment.dto.function;

import lab.jee.experiment.dto.GetExperimentsResponse;
import lab.jee.experiment.entity.Experiment;

import java.util.List;
import java.util.function.Function;

public class ExperimentsToResponseFunction implements Function<List<Experiment>, GetExperimentsResponse> {

    @Override
    public GetExperimentsResponse apply(List<Experiment> experiments) {
        return GetExperimentsResponse.builder()
                .experiments(experiments.stream().map(experiment -> GetExperimentsResponse.Experiment.builder()
                                .id(experiment.getId())
                                .description(experiment.getDescription())
                                .build())
                        .toList())
                .build();
    }
}
