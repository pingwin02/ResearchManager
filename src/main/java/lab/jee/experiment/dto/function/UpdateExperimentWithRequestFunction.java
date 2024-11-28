package lab.jee.experiment.dto.function;

import lab.jee.experiment.dto.PatchExperimentRequest;
import lab.jee.experiment.entity.Experiment;

import java.util.function.BiFunction;

public class UpdateExperimentWithRequestFunction implements BiFunction<Experiment, PatchExperimentRequest, Experiment> {

    @Override
    public Experiment apply(Experiment e, PatchExperimentRequest r) {
        return Experiment.builder()
                .id(e.getId())
                .description(r.getDescription() != null ? r.getDescription() : e.getDescription())
                .success(r.isSuccess())
                .dateConducted(r.getDateConducted() != null ? r.getDateConducted() : e.getDateConducted())
                .project(e.getProject())
                .researcher(e.getResearcher())
                .version(r.getVersion())
                .creationDateTime(e.getCreationDateTime())
                .modificationDateTime(e.getModificationDateTime())
                .build();
    }
}
