package lab.jee.project.dto.function;

import lab.jee.project.dto.GetProjectsResponse;
import lab.jee.project.entity.Project;

import java.util.List;
import java.util.function.Function;

public class ProjectsToResponseFunction implements Function<List<Project>, GetProjectsResponse> {

    @Override
    public GetProjectsResponse apply(List<Project> projects) {
        return GetProjectsResponse.builder()
                .projects(projects.stream()
                        .map(project -> GetProjectsResponse.Project.builder()
                                .id(project.getId())
                                .title(project.getTitle())
                                .build())
                        .toList())
                .build();
    }
}
