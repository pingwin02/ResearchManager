package lab.jee.project.dto.function;

import lab.jee.project.dto.PatchProjectRequest;
import lab.jee.project.entity.Project;

import java.util.function.BiFunction;

public class UpdateProjectWithRequestFunction implements BiFunction<Project, PatchProjectRequest, Project> {

    @Override
    public Project apply(Project p, PatchProjectRequest r) {
        return Project.builder()
                .id(p.getId())
                .title(r.getTitle() != null ? r.getTitle() : p.getTitle())
                .budget(r.getBudget())
                .priority(r.getPriority() != null ? r.getPriority() : p.getPriority())
                .experiments(p.getExperiments())
                .build();
    }
}
