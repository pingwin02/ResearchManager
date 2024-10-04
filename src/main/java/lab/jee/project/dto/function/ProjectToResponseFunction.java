package lab.jee.project.dto.function;

import lab.jee.project.dto.GetProjectResponse;
import lab.jee.project.entity.Project;

import java.util.function.Function;

public class ProjectToResponseFunction implements Function<Project, GetProjectResponse> {

    @Override
    public GetProjectResponse apply(Project project) {
        return GetProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .budget(project.getBudget())
                .priority(project.getPriority())
                .build();
        }
}
