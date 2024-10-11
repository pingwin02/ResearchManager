package lab.jee.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lab.jee.crypto.component.Pbkdf2PasswordHash;
import lab.jee.datastore.component.DataStore;
import lab.jee.experiment.repository.api.ExperimentRepository;
import lab.jee.experiment.repository.memory.ExperimentInMemoryRepository;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.project.repository.api.ProjectRepository;
import lab.jee.project.repository.memory.ProjectInMemoryRepository;
import lab.jee.project.service.ProjectService;
import lab.jee.researcher.repository.api.ResearcherRepository;
import lab.jee.researcher.repository.memory.ResearcherInMemoryRepository;
import lab.jee.researcher.service.AvatarService;
import lab.jee.researcher.service.ResearcherService;


@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");
        String avatarDir = event.getServletContext().getInitParameter("avatarDir");

        ResearcherRepository researcherRepository = new ResearcherInMemoryRepository(dataSource);
        ProjectRepository projectRepository = new ProjectInMemoryRepository(dataSource);
        ExperimentRepository experimentRepository = new ExperimentInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("avatarService",
                new AvatarService(researcherRepository, avatarDir));
        event.getServletContext().setAttribute("researcherService",
                new ResearcherService(researcherRepository, new Pbkdf2PasswordHash()));
        event.getServletContext().setAttribute("projectService",
                new ProjectService(projectRepository));
        event.getServletContext().setAttribute("experimentService",
                new ExperimentService(experimentRepository, projectRepository, researcherRepository));
    }

}
