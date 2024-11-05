package lab.jee.project.model.function;

import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectModel;

import java.io.Serializable;
import java.util.function.Function;

public class ProjectToModelFunction implements Function<Project, ProjectModel>, Serializable {

    @Override
    public ProjectModel apply(Project p) {
        return ProjectModel.builder()
                .id(p.getId())
                .title(p.getTitle())
                .budget(p.getBudget())
                .priority(p.getPriority())
                .build();
    }
}
