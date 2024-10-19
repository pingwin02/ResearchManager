package lab.jee.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lab.jee.experiment.entity.Experiment;
import lab.jee.project.entity.Project;
import lab.jee.researcher.entity.Researcher;
import lab.jee.serialization.component.CloningUtility;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<Project> projects = new HashSet<>();

    private final Set<Experiment> experiments = new HashSet<>();

    private final Set<Researcher> researchers = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Project> findAllProjects() {
        return projects.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createProject(Project project) throws IllegalArgumentException {
        if (projects.stream().anyMatch(p -> p.getId().equals(project.getId()))) {
            throw new IllegalArgumentException("Project with id " + project.getId() + " already exists");
        }

        projects.add(cloningUtility.clone(project));
    }

    public synchronized void updateProject(Project project) throws IllegalArgumentException {
        if (projects.removeIf(p -> p.getId().equals(project.getId()))) {
            projects.add(cloningUtility.clone(project));
        } else {
            throw new IllegalArgumentException("Project with id " + project.getId() + " not found");
        }

        experiments.stream()
                .filter(e -> e.getProject() != null && e.getProject().getId().equals(project.getId()))
                .forEach(e -> e.setProject(project));
    }

    public synchronized void deleteProject(UUID id) throws IllegalArgumentException {
        if (!projects.removeIf(p -> p.getId().equals(id))) {
            throw new IllegalArgumentException("Project with id " + id + " not found");
        }

        experiments.removeIf(e -> e.getProject() != null && e.getProject().getId().equals(id));
    }

    public synchronized List<Experiment> findAllExperiments() {
        return experiments.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createExperiment(Experiment experiment) throws IllegalArgumentException {
        if (experiments.stream().anyMatch(e -> e.getId().equals(experiment.getId()))) {
            throw new IllegalArgumentException("Experiment with id " + experiment.getId() + " already exists");
        }

        experiments.add(cloneWithRelationships(experiment));
    }

    public synchronized void updateExperiment(Experiment experiment) throws IllegalArgumentException {
        Experiment cloned = cloneWithRelationships(experiment);

        if (experiments.removeIf(e -> e.getId().equals(experiment.getId()))) {
            experiments.add(cloned);
        } else {
            throw new IllegalArgumentException("Experiment with id " + experiment.getId() + " not found");
        }
    }

    public synchronized void deleteExperiment(UUID id) throws IllegalArgumentException {
        if (!experiments.removeIf(e -> e.getId().equals(id))) {
            throw new IllegalArgumentException("Experiment with id " + id + " not found");
        }
    }

    public synchronized List<Researcher> findAllResearchers() {
        return researchers.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createResearcher(Researcher researcher) throws IllegalArgumentException {
        if (researchers.stream().anyMatch(r -> r.getId().equals(researcher.getId()))) {
            throw new IllegalArgumentException("Researcher with id " + researcher.getId() + " already exists");
        }

        researchers.add(cloningUtility.clone(researcher));
    }

    public synchronized void updateResearcher(Researcher researcher) throws IllegalArgumentException {
        if (researchers.removeIf(r -> r.getId().equals(researcher.getId()))) {
            researchers.add(cloningUtility.clone(researcher));
        } else {
            throw new IllegalArgumentException("Researcher with id " + researcher.getId() + " not found");
        }
    }

    public synchronized void deleteResearcher(UUID id) throws IllegalArgumentException {
        if (!researchers.removeIf(r -> r.getId().equals(id))) {
            throw new IllegalArgumentException("Researcher with id " + id + " not found");
        }

        experiments.stream()
                .filter(e -> e.getResearcher() != null && e.getResearcher().getId().equals(id))
                .forEach(e -> e.setResearcher(null));

        experiments.removeIf(e -> e.getResearcher() == null && e.getProject() == null);
    }

    private Experiment cloneWithRelationships(Experiment experiment) throws IllegalArgumentException {
        Experiment cloned = cloningUtility.clone(experiment);

        if (cloned.getProject() != null) {
            cloned.setProject(projects.stream()
                    .filter(p -> p.getId().equals(experiment.getProject().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Project with id " + experiment.getProject().getId() + " not found")));
        }

        if (cloned.getResearcher() != null) {
            cloned.setResearcher(researchers.stream()
                    .filter(r -> r.getId().equals(experiment.getResearcher().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Researcher with id " + experiment.getResearcher().getId() + " not found")));
        }

        return cloned;
    }
}


