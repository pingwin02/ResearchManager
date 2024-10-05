package lab.jee.researcher.dto.function;

import lab.jee.researcher.dto.GetResearcherResponse;
import lab.jee.researcher.entity.Researcher;

import java.util.function.Function;

public class ResearcherToResponseFunction implements Function<Researcher, GetResearcherResponse> {

    @Override
    public GetResearcherResponse apply(Researcher researcher) {
        return GetResearcherResponse.builder()
                .id(researcher.getId())
                .login(researcher.getLogin())
                .firstName(researcher.getFirstName())
                .lastName(researcher.getLastName())
                .role(researcher.getRole())
                .birthDate(researcher.getBirthDate())
                .email(researcher.getEmail())
                .build();
    }
}
