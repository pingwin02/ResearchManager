package lab.jee.researcher.controller.api;

import lab.jee.researcher.dto.*;

import java.io.InputStream;
import java.util.UUID;

public interface ResearcherController {

    GetResearcherResponse getResearcher(UUID id);

    GetResearchersResponse getResearchers();

    void updateResearcher(UUID id, PatchResearcherRequest request);

    void createResearcher(UUID id, PutResearcherRequest request);

    void updateResearcherPassword(UUID id, PutPasswordRequest request);

    void deleteResearcher(UUID id);

    byte[] getResearcherAvatar(UUID id);

    void updateResearcherAvatar(UUID id, InputStream avatar);

    void deleteResearcherAvatar(UUID id);
}
