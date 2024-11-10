package lab.jee.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.project.entity.Project;
import lab.jee.project.entity.ProjectPriority;
import lab.jee.project.service.ProjectService;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.entity.ResearcherRole;
import lab.jee.researcher.service.AvatarService;
import lab.jee.researcher.service.ResearcherService;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({ResearcherRole.LEAD_RESEARCHER, ResearcherRole.ASSISTANT})
@RunAs(ResearcherRole.LEAD_RESEARCHER)
@Log
public class InitializedData {

    private ResearcherService researcherService;
    private ProjectService projectService;
    private ExperimentService experimentService;
    private AvatarService avatarService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setResearcherService(ResearcherService researcherService) {
        this.researcherService = researcherService;
    }

    @EJB
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @EJB
    public void setExperimentService(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @EJB
    public void setAvatarService(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (researcherService.find("clayre").isEmpty()) {

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

            Experiment experiment2 = Experiment.builder()
                    .id(UUID.fromString("9db7491e-e2e0-4bad-8068-1af618d510f4"))
                    .description("Some experiment")
                    .success(false)
                    .dateConducted(LocalDate.of(2024, 3, 5))
                    .project(project2)
                    .researcher(clayre)
                    .build();

            experimentService.create(experiment);
            experimentService.create(experiment2);
        }
    }

    @SneakyThrows
    private InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }

}

