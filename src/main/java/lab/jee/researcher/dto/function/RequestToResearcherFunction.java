package lab.jee.researcher.dto.function;

import lab.jee.researcher.dto.PutResearcherRequest;
import lab.jee.researcher.entity.Researcher;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToResearcherFunction implements BiFunction<UUID, PutResearcherRequest, Researcher> {

    @Override
    public Researcher apply(UUID id, PutResearcherRequest request) {
        return Researcher.builder()
                .id(id)
                .login(request.getLogin())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthDate(request.getBirthDate())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }
}
