package lab.jee.project.model.function;

import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateProjectWithModelFunction implements BiFunction<Project, ProjectEditModel, Project>, Serializable {

    @Override
    public Project apply(Project project, ProjectEditModel projectEditModel) {
        return Project.builder()
                .id(project.getId())
                .title(projectEditModel.getTitle())
                .budget(projectEditModel.getBudget())
                .priority(projectEditModel.getPriority())
                .build();
    }
}
