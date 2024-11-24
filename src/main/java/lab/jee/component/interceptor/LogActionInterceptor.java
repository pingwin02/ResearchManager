package lab.jee.component.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import lab.jee.component.interceptor.binding.LogAction;
import lab.jee.experiment.entity.Experiment;
import lombok.extern.java.Log;

@Interceptor
@LogAction
@Priority(1)
@Log
public class LogActionInterceptor {

    private final SecurityContext securityContext;

    @Inject
    public LogActionInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        String username = securityContext.getCallerPrincipal().getName();
        String methodName = context.getMethod().getName();

        Object[] parameters = context.getParameters();

        String resourceId = "N/A";

        if (parameters.length > 0) {
            Object param = parameters[0];

            if (param instanceof Experiment experiment) {
                resourceId = experiment.getId().toString();
            } else {
                resourceId = param.toString();
            }
        }

        String logMessage = String.format("User %s invoked method %s() with resource id %s", username, methodName, resourceId);
        log.warning(logMessage);

        return context.proceed();
    }
}
