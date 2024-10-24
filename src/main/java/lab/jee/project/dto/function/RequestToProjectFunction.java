package lab.jee.project.dto.function;

import lab.jee.project.dto.PutProjectRequest;
import lab.jee.project.entity.Project;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToProjectFunction implements BiFunction<UUID, PutProjectRequest, Project> {

    @Override
    public Project apply(UUID id, PutProjectRequest r) {
        return Project.builder()
                .id(id)
                .title(r.getTitle())
                .budget(r.getBudget())
                .priority(r.getPriority())
                .build();
    }
}
