package lab.jee.component;

import jakarta.enterprise.context.ApplicationScoped;
import lab.jee.experiment.model.function.*;
import lab.jee.project.model.function.*;
import lab.jee.researcher.model.function.ResearcherToModelFunction;
import lab.jee.researcher.model.function.ResearchersToModelFunction;

@ApplicationScoped
public class ModelFunctionFactory {

    public ExperimentsToModelFunction experimentsToModel() {
        return new ExperimentsToModelFunction();
    }

    public ExperimentToEditModelFunction experimentToEditModel() {
        return new ExperimentToEditModelFunction(researcherToModel());
    }

    public ExperimentToModelFunction experimentToModel() {
        return new ExperimentToModelFunction();
    }

    public ModelToExperimentFunction modelToExperiment() {
        return new ModelToExperimentFunction();
    }

    public UpdateExperimentWithModelFunction updateExperiment() {
        return new UpdateExperimentWithModelFunction();
    }

    public ModelToProjectFunction modelToProject() {
        return new ModelToProjectFunction();
    }

    public ProjectsToModelFunction projectsToModel() {
        return new ProjectsToModelFunction();
    }

    public ProjectToEditModelFunction projectToEditModel() {
        return new ProjectToEditModelFunction();
    }

    public ProjectToModelFunction projectToModel() {
        return new ProjectToModelFunction();
    }

    public UpdateProjectWithModelFunction updateProject() {
        return new UpdateProjectWithModelFunction();
    }

    public ResearcherToModelFunction researcherToModel() {
        return new ResearcherToModelFunction();
    }

    public ResearchersToModelFunction researchersToModel() {
        return new ResearchersToModelFunction();
    }

}
