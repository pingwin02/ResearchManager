package lab.jee.experiment.service;

import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.repository.api.ExperimentRepository;
import lab.jee.project.entity.Project;
import lab.jee.project.repository.api.ProjectRepository;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.entity.ResearcherRole;
import lab.jee.researcher.repository.api.ResearcherRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ExperimentService {

    private final ExperimentRepository experimentRepository;
    private final ProjectRepository projectRepository;
    private final ResearcherRepository researcherRepository;
    private final SecurityContext securityContext;

    @Inject
    public ExperimentService(
            ExperimentRepository experimentRepository,
            ProjectRepository projectRepository,
            ResearcherRepository researcherRepository,
            @SuppressWarnings("CdiInjectionPointsInspection")
            SecurityContext securityContext) {
        this.experimentRepository = experimentRepository;
        this.projectRepository = projectRepository;
        this.researcherRepository = researcherRepository;
        this.securityContext = securityContext;
    }

    public void create(Experiment experiment) {
        if (experimentRepository.find(experiment.getId()).isPresent()) {
            throw new IllegalArgumentException("Experiment already exists");
        }
        if (projectRepository.find(experiment.getProject().getId()).isEmpty()) {
            throw new IllegalArgumentException("Project does not exist");
        }
        if (researcherRepository.find(experiment.getResearcher().getId()).isEmpty()) {
            throw new IllegalArgumentException("Researcher does not exist");
        }

        experimentRepository.create(experiment);
    }

    public void createForCallerPrincipal(Experiment experiment) {
        Researcher researcher = researcherRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(() -> new IllegalStateException("Researcher not found"));
        experiment.setResearcher(researcher);
        create(experiment);
    }

    public void update(Experiment experiment) {
        checkAdminRoleOrOwner(find(experiment.getId()));
        experimentRepository.update(experiment);
    }

    public void delete(UUID id) {
        checkAdminRoleOrOwner(find(id));
        experimentRepository.delete(id);
    }

    public Optional<Experiment> find(UUID id) {
        return experimentRepository.find(id);
    }

    private Optional<Experiment> find(UUID id, Researcher researcher) {
        return experimentRepository.findByIdAndResearcher(id, researcher);
    }

    private Optional<Experiment> find(UUID id, Project project) {
        return experimentRepository.findByIdAndProject(id, project);
    }

    public Optional<Experiment> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(ResearcherRole.LEAD_RESEARCHER)) {
            return find(id);
        } else {
            Researcher researcher = researcherRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(() -> new IllegalStateException("Researcher not found"));
            return find(id, researcher);
        }
    }

    private List<Experiment> findAll() {
        return experimentRepository.findAll();
    }

    public List<Experiment> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(ResearcherRole.LEAD_RESEARCHER)) {
            return findAll();
        } else {
            Researcher researcher = researcherRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(() -> new IllegalStateException("Researcher not found"));
            return experimentRepository.findAllByResearcher(researcher);
        }
    }

    public Optional<List<Experiment>> findAllByResearcher(UUID id) {
        return researcherRepository.find(id)
                .map(experimentRepository::findAllByResearcher);
    }

    public Optional<List<Experiment>> findAllByProject(UUID id) {
        return projectRepository.find(id)
                .map(experimentRepository::findAllByProject);
    }

    private void checkAdminRoleOrOwner(Optional<Experiment> experiment) throws EJBAccessException {
        if (securityContext.isCallerInRole(ResearcherRole.LEAD_RESEARCHER)) {
            return;
        }
        if (securityContext.isCallerInRole(ResearcherRole.ASSISTANT)
                && experiment.isPresent()
                && experiment.get().getResearcher().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Access denied");
    }
}
