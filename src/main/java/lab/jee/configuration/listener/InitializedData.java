package lab.jee.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.project.entity.Project;
import lab.jee.project.entity.ProjectPriority;
import lab.jee.project.service.ProjectService;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.entity.ResearcherRole;
import lab.jee.researcher.service.ResearcherService;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {

    private ResearcherService researcherService;

    private ProjectService projectService;

    private ExperimentService experimentService;

    private Path avatarDirectory;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        researcherService = (ResearcherService) event.getServletContext().getAttribute("researcherService");
        projectService = (ProjectService) event.getServletContext().getAttribute("projectService");
        experimentService = (ExperimentService) event.getServletContext().getAttribute("experimentService");
        avatarDirectory = Paths.get(System.getProperty("user.dir").split("target")[0],
                event.getServletContext().getInitParameter("avatarDir"));

        init();
    }

    @SneakyThrows
    private void init() {
        Researcher clayre = Researcher.builder()
                .id(UUID.fromString("b8371a52-2d1a-4af9-a5ba-15934740e3e1"))
                .login("clayre")
                .firstName("Clayre")
                .lastName("Smith")
                .role(ResearcherRole.LEAD_RESEARCHER)
                .birthDate(LocalDate.of(1975, 6, 4))
                .email("clayre@jee.com")
                .password("password")
                .avatarPath(avatarDirectory.resolve("b8371a52-2d1a-4af9-a5ba-15934740e3e1.png").toString())
                .build();

        Researcher jonathan = Researcher.builder()
                .id(UUID.fromString("88e9ccfb-b924-4988-bcb2-f84e7f7810d7"))
                .login("jonathan")
                .firstName("Jonathan")
                .lastName("Smith")
                .role(ResearcherRole.ASSISTANT)
                .birthDate(LocalDate.of(1982, 6, 8))
                .email("jonathan@jee.com")
                .password("password")
                .avatarPath(avatarDirectory.resolve("88e9ccfb-b924-4988-bcb2-f84e7f7810d7.png").toString())
                .build();

        Researcher jason = Researcher.builder()
                .id(UUID.fromString("b8c250ba-ac86-4563-a42a-563f7962f353"))
                .login("jason")
                .firstName("Jason")
                .lastName("Smith")
                .role(ResearcherRole.ASSISTANT)
                .birthDate(LocalDate.of(1983, 2, 18))
                .email("jason@jee.com")
                .password("password")
                .avatarPath(avatarDirectory.resolve("b8c250ba-ac86-4563-a42a-563f7962f353.png").toString())
                .build();

        Researcher madison = Researcher.builder()
                .id(UUID.fromString("5ba66330-942c-4ae6-9cb7-e2e2f9628b9a"))
                .login("madison")
                .firstName("Madison")
                .lastName("Smith")
                .role(ResearcherRole.ASSISTANT)
                .birthDate(LocalDate.of(1997, 6, 27))
                .email("madison@jee.com")
                .password("password")
                .avatarPath(avatarDirectory.resolve("5ba66330-942c-4ae6-9cb7-e2e2f9628b9a.png").toString())
                .build();

        Researcher mark = Researcher.builder()
                .id(UUID.fromString("d91b3081-fce5-4605-9082-e123b10f7ade"))
                .login("mark")
                .firstName("Mark")
                .lastName("Smith")
                .role(ResearcherRole.ASSISTANT)
                .birthDate(LocalDate.of(1998, 9, 7))
                .email("mark@jee.com")
                .password("password")
                .avatarPath(avatarDirectory.resolve("d91b3081-fce5-4605-9082-e123b10f7ade.png").toString())
                .build();

        researcherService.create(clayre);
        researcherService.create(jonathan);
        researcherService.create(jason);
        researcherService.create(madison);
        researcherService.create(mark);

        Project project = Project.builder()
                .id(UUID.fromString("9ba4a53d-e742-40cb-a613-f609696c5ef9"))
                .title("Vaccine against COVID-19")
                .budget(100_000)
                .priority(ProjectPriority.HIGH)
                .build();

        projectService.create(project);

        Experiment experiment = Experiment.builder()
                .id(UUID.fromString("3180349a-7d53-4200-91dc-3b0b757dca98"))
                .description("Focused on the development of a vaccine against COVID-19")
                .success(true)
                .dateConducted(LocalDate.of(2021, 6, 1))
                .project(project)
                .researcher(clayre)
                .build();

        experimentService.create(experiment);
    }

}