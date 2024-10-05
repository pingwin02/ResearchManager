package lab.jee.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab.jee.experiment.controller.api.ExperimentController;
import lab.jee.project.controller.api.ProjectController;
import lab.jee.researcher.controller.api.ResearcherController;
import lab.jee.researcher.dto.PatchResearcherRequest;
import lab.jee.researcher.dto.PutResearcherRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private final Jsonb jsonb = JsonbBuilder.create();
    private ExperimentController experimentController;
    private ProjectController projectController;
    private ResearcherController researcherController;

    /**
     * Extracts UUID from path using provided pattern. Pattern needs to contain UUID in first regular expression group.
     *
     * @param pattern regular expression pattern with
     * @param path    request path containing UUID
     * @return extracted UUID
     */
    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    /**
     * Creates URL using host, port and context root from servlet request and any number of path elements. If any of
     * path elements starts or ends with '/' character, that character is removed.
     *
     * @param request servlet request
     * @param paths   any (can be none) number of path elements
     * @return created url
     */
    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        experimentController = (ExperimentController) getServletContext().getAttribute("experimentController");
        projectController = (ProjectController) getServletContext().getAttribute("projectController");
        researcherController = (ResearcherController) getServletContext().getAttribute("researcherController");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.EXPERIMENTS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(experimentController.getExperiments()));
                return;
            } else if (path.matches(Patterns.EXPERIMENT.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.EXPERIMENT, path);
                response.getWriter().write(jsonb.toJson(experimentController.getExperiment(uuid)));
                return;
            } else if (path.matches(Patterns.PROJECTS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(projectController.getProjects()));
                return;
            } else if (path.matches(Patterns.PROJECT.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PROJECT, path);
                response.getWriter().write(jsonb.toJson(projectController.getProject(uuid)));
                return;
            } else if (path.matches(Patterns.RESEARCHERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(researcherController.getResearchers()));
                return;
            } else if (path.matches(Patterns.RESEARCHER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.RESEARCHER, path);
                response.getWriter().write(jsonb.toJson(researcherController.getResearcher(uuid)));
                return;
            } else if (path.matches(Patterns.RESEARCHER_EXPERIMENTS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.RESEARCHER_EXPERIMENTS, path);
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                return;
            } else if (path.matches(Patterns.PROJECT_EXPERIMENTS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PROJECT_EXPERIMENTS, path);
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                return;
            } else if (path.matches(Patterns.RESEARCHER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.RESEARCHER_AVATAR, path);
                byte[] avatar = researcherController.getResearcherAvatar(uuid);
                response.setContentLength(avatar.length);
                response.getOutputStream().write(avatar);
                return;
            }

        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RESEARCHER.pattern())) {
                UUID uuid = extractUuid(Patterns.RESEARCHER, path);
                researcherController.createResearcher(uuid, jsonb.fromJson(request.getReader(), PutResearcherRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "researchers", uuid.toString()));
                return;
            } else if (path.matches(Patterns.RESEARCHER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.RESEARCHER_AVATAR, path);
                researcherController.updateResearcherAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RESEARCHER.pattern())) {
                UUID uuid = extractUuid(Patterns.RESEARCHER, path);
                researcherController.deleteResearcher(uuid);
                return;
            } else if (path.matches(Patterns.RESEARCHER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.RESEARCHER_AVATAR, path);
                researcherController.deleteResearcherAvatar(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a PATCH request.
     *
     * @param request  {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the PATCH cannot be handled
     * @throws IOException      if an input or output error occurs while the servlet is handling the PATCH request
     */
    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RESEARCHER.pattern())) {
                UUID uuid = extractUuid(Patterns.RESEARCHER, path);
                researcherController.updateResearcher(uuid, jsonb.fromJson(request.getReader(),
                        PatchResearcherRequest.class));
                return;
            } else if (path.matches(Patterns.RESEARCHER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.RESEARCHER_AVATAR, path);
                researcherController.updateResearcherAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Gets path info from the request and returns it. No null is returned, instead empty string is used.
     *
     * @param request original servlet request
     * @return path info (not null)
     */
    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        private static final Pattern PROJECTS = Pattern.compile("/projects/?");

        private static final Pattern PROJECT = Pattern.compile("/projects/(%s)".formatted(UUID.pattern()));

        private static final Pattern PROJECT_EXPERIMENTS =
                Pattern.compile("/projects/(%s)/experiments/?".formatted(UUID.pattern()));

        private static final Pattern RESEARCHERS = Pattern.compile("/researchers/?");

        private static final Pattern RESEARCHER = Pattern.compile("/researchers/(%s)".formatted(UUID.pattern()));

        private static final Pattern RESEARCHER_EXPERIMENTS =
                Pattern.compile("/researchers/(%s)/experiments/?".formatted(UUID.pattern()));

        private static final Pattern RESEARCHER_AVATAR =
                Pattern.compile("/researchers/(%s)/avatar".formatted(UUID.pattern()));

        private static final Pattern EXPERIMENTS = Pattern.compile("/experiments/?");

        private static final Pattern EXPERIMENT = Pattern.compile("/experiments/(%s)".formatted(UUID.pattern()));
    }

}
