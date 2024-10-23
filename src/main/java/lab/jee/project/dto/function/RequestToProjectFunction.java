package lab.jee.project.dto.function;

import lab.jee.project.dto.PutProjectRequest;
import lab.jee.project.entity.Project;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToProjectFunction implements BiFunction<UUID, PutProjectRequest, Project> {

    @Override
    public Project apply(UUID id, PutProjectRequest putProjectRequest) {
        return Project.builder()
                .id(id)
                .title(putProjectRequest.getTitle())
                .budget(putProjectRequest.getBudget())
                .priority(putProjectRequest.getPriority())
                .build();
    }
}
