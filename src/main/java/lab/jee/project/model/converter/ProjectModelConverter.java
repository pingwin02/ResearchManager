package lab.jee.project.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectModel;
import lab.jee.project.service.ProjectService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = ProjectModel.class, managed = true)
public class ProjectModelConverter implements Converter<ProjectModel> {

    private final ProjectService service;

    private final ModelFunctionFactory factory;

    @Inject
    public ProjectModelConverter(ProjectService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public ProjectModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isBlank()) {
            return null;
        }

        Optional<Project> project = service.find(UUID.fromString(s));
        return project.map(factory.projectToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, ProjectModel projectModel) {
        return projectModel == null ? null : projectModel.getId().toString();
    }
}
