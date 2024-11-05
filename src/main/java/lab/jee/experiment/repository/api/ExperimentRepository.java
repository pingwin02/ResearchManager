package lab.jee.experiment.repository.api;

import lab.jee.experiment.entity.Experiment;
import lab.jee.project.entity.Project;
import lab.jee.repository.api.Repository;
import lab.jee.researcher.entity.Researcher;

import java.util.List;
import java.util.UUID;

public interface ExperimentRepository extends Repository<Experiment, UUID> {

    List<Experiment> findAllByResearcher(Researcher researcher);

    List<Experiment> findAllByProject(Project project);
}
