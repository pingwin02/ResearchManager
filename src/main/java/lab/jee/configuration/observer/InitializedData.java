package lab.jee.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.project.entity.Project;
import lab.jee.project.entity.ProjectPriority;
import lab.jee.project.service.ProjectService;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.entity.ResearcherRole;
import lab.jee.researcher.service.AvatarService;
import lab.jee.researcher.service.ResearcherService;
import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {

    private final ResearcherService researcherService;

    private final ProjectService projectService;

    private final ExperimentService experimentService;

    private final AvatarService avatarService;

    private final RequestContextController requestContextController;

    private final ServletContext servletContext;

    @Inject
    public InitializedData(
            ResearcherService researcherService,
            ProjectService projectService,
            ExperimentService experimentService,
            AvatarService avatarService,
            ServletContext servletContext,
            RequestContextController requestContextController
    ) {
        this.researcherService = researcherService;
        this.projectService = projectService;
        this.experimentService = experimentService;
        this.avatarService = avatarService;
        this.requestContextController = requestContextController;
        this.servletContext = servletContext;
    }


    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();

        Researcher clayre = Researcher.builder()
                .id(UUID.fromString("b8371a52-2d1a-4af9-a5ba-15934740e3e1"))
                .login("clayre")
                .firstName("Clayre")
                .lastName("Smith")
                .role(ResearcherRole.LEAD_RESEARCHER)
                .birthDate(LocalDate.of(1975, 6, 4))
                .email("clayre@jee.com")
                .password("password")
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
                .build();

        researcherService.create(clayre);
        researcherService.create(jonathan);
        researcherService.create(jason);
        researcherService.create(madison);
        researcherService.create(mark);

        prepareAvatarDirectory();

        avatarService.createAvatar(clayre.getId(), getResourceAsStream("clayre.png"));
        avatarService.createAvatar(jonathan.getId(), getResourceAsStream("jonathan.png"));
        avatarService.createAvatar(jason.getId(), getResourceAsStream("jason.png"));
        avatarService.createAvatar(madison.getId(), getResourceAsStream("madison.png"));
        avatarService.createAvatar(mark.getId(), getResourceAsStream("mark.png"));

        Project project = Project.builder()
                .id(UUID.fromString("9ba4a53d-e742-40cb-a613-f609696c5ef9"))
                .title("Vaccine against COVID-19")
                .budget(100_000)
                .priority(ProjectPriority.HIGH)
                .build();

        Project project2 = Project.builder()
                .id(UUID.fromString("9ba4a53d-e742-40aa-a613-f609696c5ef9"))
                .title("Not important project")
                .budget(500)
                .priority(ProjectPriority.LOW)
                .build();

        Project project_update = Project.builder()
                .id(UUID.fromString("9ba4a53d-e742-40aa-a613-f609696c5ef9"))
                .title("Vaccine against Polio")
                .budget(100_000_000)
                .priority(ProjectPriority.HIGH)
                .build();

        projectService.create(project);
        projectService.create(project2);

        Experiment experiment = Experiment.builder()
                .id(UUID.fromString("3180349a-7d53-4200-91dc-3b0b757dca98"))
                .description("Focused on the development of a vaccine against COVID-19")
                .success(true)
                .dateConducted(LocalDate.of(2021, 6, 1))
                .project(project)
                .researcher(clayre)
                .build();

        experimentService.create(experiment);

        Experiment experiment_update = Experiment.builder()
                .id(UUID.fromString("3180349a-7d53-4200-91dc-3b0b757dca98"))
                .description("Focused on the development of a cure against Polio")
                .success(false)
                .dateConducted(LocalDate.of(2020, 2, 3))
                .project(project)
                .researcher(clayre)
                .build();

        experimentService.update(experiment_update);

        Experiment experiment2 = Experiment.builder()
                .id(UUID.fromString("3180349a-7d53-4200-91da-3b0b757dca98"))
                .description("Some experiment")
                .success(true)
                .dateConducted(LocalDate.of(2024, 3, 5))
                .project(project2)
                .researcher(clayre)
                .build();

        experimentService.create(experiment2);
        projectService.update(project_update);

        projectService.delete(project.getId());
        researcherService.delete(clayre.getId());
        //experimentService.delete(experiment2.getId());

        requestContextController.deactivate();
    }

    private void prepareAvatarDirectory() {
        File avatarDir = new File(Paths.get(servletContext
                .getRealPath(servletContext
                        .getInitParameter("avatarDir"))).toString());
        if (!avatarDir.exists()) {
            avatarDir.mkdirs();
        } else {
            for (File file : avatarDir.listFiles()) {
                file.delete();
            }
        }
    }

    @SneakyThrows
    private InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }

}

