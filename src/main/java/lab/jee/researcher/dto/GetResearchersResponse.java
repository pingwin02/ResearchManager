package lab.jee.researcher.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GetResearchersResponse {

    @Singular
    private List<Researcher> researchers;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Researcher {

        private UUID id;

        private String login;

    }
}
