package lab.jee.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lab.jee.component.DtoFunctionFactory;
import lab.jee.experiment.controller.simple.ExperimentSimpleController;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.project.controller.simple.ProjectSimpleController;
import lab.jee.project.service.ProjectService;
import lab.jee.researcher.controller.simple.ResearcherSimpleController;
import lab.jee.researcher.service.ResearcherService;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ExperimentService experimentService = (ExperimentService) event
                .getServletContext().getAttribute("experimentService");
        ProjectService projectService = (ProjectService) event
                .getServletContext().getAttribute("projectService");
        ResearcherService researcherService = (ResearcherService) event
                .getServletContext().getAttribute("researcherService");

        event.getServletContext().setAttribute("experimentController",
                new ExperimentSimpleController(experimentService, new DtoFunctionFactory()));

        event.getServletContext().setAttribute("projectController",
                new ProjectSimpleController(projectService, new DtoFunctionFactory()));

        event.getServletContext().setAttribute("researcherController",
                new ResearcherSimpleController(researcherService, new DtoFunctionFactory()));

    }
}
