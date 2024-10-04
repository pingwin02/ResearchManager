package lab.jee.project.controller.api;

import lab.jee.project.dto.GetProjectResponse;
import lab.jee.project.dto.GetProjectsResponse;

import java.util.UUID;

public interface ProjectController {

    GetProjectResponse getProject(UUID id);

    GetProjectsResponse getProjects();
}
