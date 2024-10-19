package lab.jee.experiment.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentModel;
import lab.jee.experiment.service.ExperimentService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = ExperimentModel.class, managed = true)
public class ExperimentModelConverter implements Converter<ExperimentModel> {

    private final ExperimentService service;

    private final ModelFunctionFactory factory;

    @Inject
    public ExperimentModelConverter(ExperimentService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public ExperimentModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isBlank()) {
            return null;
        }

        Optional<Experiment> experiment = service.find(UUID.fromString(s));
        return experiment.map(factory.experimentToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, ExperimentModel experimentModel) {
        return experimentModel == null ? null : experimentModel.getId().toString();
    }
}
