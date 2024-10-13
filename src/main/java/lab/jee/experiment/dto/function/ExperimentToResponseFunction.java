package lab.jee.experiment.dto.function;

import lab.jee.experiment.dto.GetExperimentResponse;
import lab.jee.experiment.entity.Experiment;

import java.util.function.Function;

public class ExperimentToResponseFunction implements Function<Experiment, GetExperimentResponse> {

    @Override
    public GetExperimentResponse apply(Experiment experiment) {
        return GetExperimentResponse.builder()
                .id(experiment.getId())
                .description(experiment.getDescription())
                .success(experiment.isSuccess())
                .dateConducted(experiment.getDateConducted())
                .project(experiment.getProject() != null ? GetExperimentResponse.Project.builder()
                        .id(experiment.getProject().getId())
                        .title(experiment.getProject().getTitle())
                        .build() : null)
                .researcher(experiment.getResearcher() != null ? GetExperimentResponse.Researcher.builder()
                        .id(experiment.getResearcher().getId())
                        .login(experiment.getResearcher().getLogin())
                        .build() : null)
                .build();
    }
}
