package lab.jee.researcher.dto.function;

import lab.jee.researcher.dto.GetResearchersResponse;
import lab.jee.researcher.entity.Researcher;

import java.util.List;
import java.util.function.Function;

public class ResearchersToResponseFunction implements Function<List<Researcher>, GetResearchersResponse> {

    @Override
    public GetResearchersResponse apply(List<Researcher> researchers) {
        return GetResearchersResponse.builder()
                .researchers(researchers.stream()
                        .map(researcher -> GetResearchersResponse.Researcher.builder()
                                .id(researcher.getId())
                                .login(researcher.getLogin())
                                .build())
                        .toList())
                .build();
    }
}