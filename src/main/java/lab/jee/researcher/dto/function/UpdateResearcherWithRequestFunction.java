package lab.jee.researcher.dto.function;

import lab.jee.researcher.dto.PatchResearcherRequest;
import lab.jee.researcher.entity.Researcher;

import java.util.function.BiFunction;

public class UpdateResearcherWithRequestFunction implements BiFunction<Researcher, PatchResearcherRequest, Researcher> {

    @Override
    public Researcher apply(Researcher entity, PatchResearcherRequest request) {
        return Researcher.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthDate(request.getBirthDate())
                .password(entity.getPassword())
                .email(request.getEmail())
                .build();
    }
}
