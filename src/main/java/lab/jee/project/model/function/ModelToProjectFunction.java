package lab.jee.project.model.function;

import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToProjectFunction implements Function<ProjectCreateModel, Project>, Serializable {

    @Override
    public Project apply(ProjectCreateModel projectModel) {
        return Project.builder()
                .id(projectModel.getId())
                .title(projectModel.getTitle())
                .budget(projectModel.getBudget())
                .priority(projectModel.getPriority())
                .build();
    }
}
