package lab.jee.researcher.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.model.ResearcherModel;
import lab.jee.researcher.service.ResearcherService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = ResearcherModel.class, managed = true)
public class ResearcherModelConverter implements Converter<ResearcherModel> {

    private final ResearcherService service;

    private final ModelFunctionFactory factory;

    @Inject
    public ResearcherModelConverter(ResearcherService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public ResearcherModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isBlank()) {
            return null;
        }

        Optional<Researcher> researcher = service.find(UUID.fromString(s));
        return researcher.map(factory.researcherToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, ResearcherModel researcherModel) {
        return researcherModel == null ? null : researcherModel.getLogin();
    }
}