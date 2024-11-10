package lab.jee.researcher.model.function;

import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.model.ResearcherModel;

import java.util.function.Function;

public class ResearcherToModelFunction implements Function<Researcher, ResearcherModel> {

    @Override
    public ResearcherModel apply(Researcher researcher) {
        return ResearcherModel.builder()
                .id(researcher.getId())
                .login(researcher.getLogin())
                .build();
    }
}
