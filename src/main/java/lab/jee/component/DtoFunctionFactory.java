package lab.jee.component;

import jakarta.enterprise.context.ApplicationScoped;
import lab.jee.experiment.dto.function.ExperimentToResponseFunction;
import lab.jee.experiment.dto.function.ExperimentsToResponseFunction;
import lab.jee.project.dto.function.ProjectToResponseFunction;
import lab.jee.project.dto.function.ProjectsToResponseFunction;
import lab.jee.researcher.dto.function.*;

@ApplicationScoped
public class DtoFunctionFactory {

    public ResearcherToResponseFunction researcherToResponse() {
        return new ResearcherToResponseFunction();
    }

    public ResearchersToResponseFunction researchersToResponse() {
        return new ResearchersToResponseFunction();
    }

    public RequestToResearcherFunction requestToResearcher() {
        return new RequestToResearcherFunction();
    }

    public UpdateResearcherPasswordWithRequestFunction updateResearcherPasswordWithRequest() {
        return new UpdateResearcherPasswordWithRequestFunction();
    }

    public UpdateResearcherWithRequestFunction updateResearcherWithRequest() {
        return new UpdateResearcherWithRequestFunction();
    }

    public ProjectToResponseFunction projectToResponse() {
        return new ProjectToResponseFunction();
    }

    public ProjectsToResponseFunction projectsToResponse() {
        return new ProjectsToResponseFunction();
    }

    public ExperimentToResponseFunction experimentToResponse() {
        return new ExperimentToResponseFunction();
    }

    public ExperimentsToResponseFunction experimentsToResponse() {
        return new ExperimentsToResponseFunction();
    }

}
