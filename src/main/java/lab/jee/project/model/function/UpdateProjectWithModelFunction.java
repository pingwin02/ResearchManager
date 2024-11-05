package lab.jee.project.model.function;

import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateProjectWithModelFunction implements BiFunction<Project, ProjectEditModel, Project>, Serializable {

    @Override
    public Project apply(Project p, ProjectEditModel m) {
        return Project.builder()
                .id(p.getId())
                .title(m.getTitle())
                .budget(m.getBudget())
                .priority(m.getPriority())
                .build();
    }
}
