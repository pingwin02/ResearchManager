package lab.jee.project.model.function;

import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectsModel;

import java.util.List;
import java.util.function.Function;

public class ProjectsToModelFunction implements Function<List<Project>, ProjectsModel> {

    @Override
    public ProjectsModel apply(List<Project> projects) {
        return ProjectsModel.builder()
                .projects(projects.stream()
                        .map(project -> ProjectsModel.Project.builder()
                                .id(project.getId())
                                .title(project.getTitle())
                                .build())
                        .toList())
                .build();
    }
}
