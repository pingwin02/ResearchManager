package lab.jee.experiment.repository.api;

import lab.jee.experiment.entity.Experiment;
import lab.jee.project.entity.Project;
import lab.jee.repository.api.Repository;
import lab.jee.researcher.entity.Researcher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExperimentRepository extends Repository<Experiment, UUID> {

    Optional<Experiment> findByIdAndResearcher(UUID id, Researcher researcher);

    Optional<Experiment> findByIdAndProject(UUID id, Project project);

    List<Experiment> findAllByResearcher(Researcher researcher);

    List<Experiment> findAllByProject(Project project);

    List<Experiment> findAllByProjectAndResearcher(Project project, Researcher researcher);

    List<Experiment> findByFilters(String description, Boolean success, LocalDate dateConducted);

}
