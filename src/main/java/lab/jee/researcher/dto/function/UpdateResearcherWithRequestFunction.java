package lab.jee.researcher.dto.function;

import lab.jee.researcher.dto.PatchResearcherRequest;
import lab.jee.researcher.entity.Researcher;

import java.util.function.BiFunction;

public class UpdateResearcherWithRequestFunction implements BiFunction<Researcher, PatchResearcherRequest, Researcher> {

    @Override
    public Researcher apply(Researcher r, PatchResearcherRequest req) {
        return Researcher.builder()
                .id(r.getId())
                .login(r.getLogin())
                .firstName(req.getFirstName() != null ? req.getFirstName() : r.getFirstName())
                .lastName(req.getLastName() != null ? req.getLastName() : r.getLastName())
                .birthDate(req.getBirthDate() != null ? req.getBirthDate() : r.getBirthDate())
                .password(r.getPassword())
                .email(req.getEmail() != null ? req.getEmail() : r.getEmail())
                .roles(r.getRoles())
                .avatarPath(r.getAvatarPath())
                .experiments(r.getExperiments())
                .build();
    }
}
