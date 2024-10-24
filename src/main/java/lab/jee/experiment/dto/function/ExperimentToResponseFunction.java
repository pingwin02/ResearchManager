package lab.jee.experiment.dto.function;

import lab.jee.experiment.dto.GetExperimentResponse;
import lab.jee.experiment.entity.Experiment;

import java.util.function.Function;

public class ExperimentToResponseFunction implements Function<Experiment, GetExperimentResponse> {

    @Override
    public GetExperimentResponse apply(Experiment e) {
        return GetExperimentResponse.builder()
                .id(e.getId())
                .description(e.getDescription())
                .success(e.isSuccess())
                .dateConducted(e.getDateConducted())
                .project(e.getProject() != null ? GetExperimentResponse.Project.builder()
                        .id(e.getProject().getId())
                        .title(e.getProject().getTitle())
                        .build() : null)
                .researcher(e.getResearcher() != null ? GetExperimentResponse.Researcher.builder()
                        .id(e.getResearcher().getId())
                        .login(e.getResearcher().getLogin())
                        .build() : null)
                .build();
    }
}
