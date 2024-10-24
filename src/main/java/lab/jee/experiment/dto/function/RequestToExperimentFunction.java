package lab.jee.experiment.dto.function;

import lab.jee.experiment.dto.PutExperimentRequest;
import lab.jee.experiment.entity.Experiment;
import lab.jee.function.api.TriFunction;
import lab.jee.project.entity.Project;

import java.util.UUID;

public class RequestToExperimentFunction implements TriFunction<UUID, UUID, PutExperimentRequest, Experiment> {

    @Override
    public Experiment apply(UUID id, UUID projectId, PutExperimentRequest r) {
        return Experiment.builder()
                .id(id)
                .description(r.getDescription())
                .success(r.isSuccess())
                .dateConducted(r.getDateConducted())
                .project(Project.builder().id(projectId).build())
                .build();
    }
}
