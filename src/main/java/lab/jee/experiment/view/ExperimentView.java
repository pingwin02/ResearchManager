package lab.jee.experiment.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentModel;
import lab.jee.experiment.service.ExperimentService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named
public class ExperimentView implements Serializable {

    private final ModelFunctionFactory factory;
    private ExperimentService service;
    @Setter
    @Getter
    private UUID id;

    @Getter
    private ExperimentModel experiment;

    @Inject
    public ExperimentView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(ExperimentService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Experiment> experiment = service.findForCallerPrincipal(id);
        if (experiment.isPresent()) {
            this.experiment = factory.experimentToModel().apply(experiment.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Experiment not found");
        }
    }
}
