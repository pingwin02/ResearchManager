package lab.jee.researcher.dto.function;

import lab.jee.researcher.dto.PutPasswordRequest;
import lab.jee.researcher.entity.Researcher;

import java.util.function.BiFunction;

public class UpdateResearcherPasswordWithRequestFunction implements BiFunction<Researcher, PutPasswordRequest, Researcher> {

    @Override
    public Researcher apply(Researcher entity, PutPasswordRequest request) {
        return Researcher.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .password(request.getPassword())
                .email(entity.getEmail())
                .role(entity.getRole())
                .avatar(entity.getAvatar())
                .build();
    }
}
