package lab.jee.researcher.view;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.researcher.model.ResearchersModel;
import lab.jee.researcher.service.ResearcherService;

import java.io.Serializable;

@ViewScoped
@Named
public class ResearcherList implements Serializable {

    private final ModelFunctionFactory factory;
    private ResearcherService service;
    private ResearchersModel researchers;

    @Inject
    public ResearcherList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(ResearcherService service) {
        this.service = service;
    }

    public ResearchersModel getResearchers() {
        if (researchers == null) {
            researchers = factory.researchersToModel().apply(service.findAll());
        }
        return researchers;
    }

    public void deleteAction(ResearchersModel.Researcher researcher) {
        service.delete(researcher.getId());
        researchers = null;
    }
}
