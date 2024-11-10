package lab.jee.researcher.model.function;

import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.model.ResearchersModel;

import java.util.List;
import java.util.function.Function;

public class ResearchersToModelFunction implements Function<List<Researcher>, ResearchersModel> {

    @Override
    public ResearchersModel apply(List<Researcher> researchers) {
        return ResearchersModel.builder()
                .researchers(researchers.stream()
                        .map(researcher -> ResearchersModel.Researcher.builder()
                                .id(researcher.getId())
                                .login(researcher.getLogin())
                                .build())
                        .toList())
                .build();
    }
}
