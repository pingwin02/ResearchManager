package lab.jee.project.dto.function;

import lab.jee.project.dto.PatchProjectRequest;
import lab.jee.project.entity.Project;

import java.util.UUID;
import java.util.function.BiFunction;

public class UpdateProjectWithRequestFunction implements BiFunction<UUID, PatchProjectRequest, Project> {

    @Override
    public Project apply(UUID id, PatchProjectRequest r) {
        return Project.builder()
                .id(id)
                .title(r.getTitle())
                .budget(r.getBudget())
                .priority(r.getPriority())
                .build();
    }
}
