package lab.jee.project.model.function;

import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToProjectFunction implements Function<ProjectCreateModel, Project>, Serializable {

    @Override
    public Project apply(ProjectCreateModel m) {
        return Project.builder()
                .id(m.getId())
                .title(m.getTitle())
                .budget(m.getBudget())
                .priority(m.getPriority())
                .build();
    }
}
