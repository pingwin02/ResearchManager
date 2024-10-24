package lab.jee.project.dto.function;

import lab.jee.project.dto.GetProjectResponse;
import lab.jee.project.entity.Project;

import java.util.function.Function;

public class ProjectToResponseFunction implements Function<Project, GetProjectResponse> {

    @Override
    public GetProjectResponse apply(Project p) {
        return GetProjectResponse.builder()
                .id(p.getId())
                .title(p.getTitle())
                .budget(p.getBudget())
                .priority(p.getPriority())
                .build();
    }
}
