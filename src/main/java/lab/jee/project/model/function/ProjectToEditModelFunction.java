package lab.jee.project.model.function;

import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectEditModel;

import java.util.function.Function;

public class ProjectToEditModelFunction implements Function<Project, ProjectEditModel> {

    @Override
    public ProjectEditModel apply(Project p) {
        return ProjectEditModel.builder()
                .title(p.getTitle())
                .budget(p.getBudget())
                .priority(p.getPriority())
                .build();
    }
}
