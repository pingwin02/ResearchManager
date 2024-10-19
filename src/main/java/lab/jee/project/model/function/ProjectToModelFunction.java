package lab.jee.project.model.function;

import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectModel;

import java.io.Serializable;
import java.util.function.Function;

public class ProjectToModelFunction implements Function<Project, ProjectModel>, Serializable {

    @Override
    public ProjectModel apply(Project project) {
        return ProjectModel.builder()
                .id(project.getId())
                .title(project.getTitle())
                .budget(project.getBudget())
                .priority(project.getPriority())
                .build();
    }
}
