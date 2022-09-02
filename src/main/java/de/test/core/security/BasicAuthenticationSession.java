package de.test.core.security;

import com.google.common.base.Optional;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.Serial;

public class BasicAuthenticationSession extends AuthenticatedWebSession {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(BasicAuthenticationSession.class);

    protected Optional<String> username = Optional.absent();
    protected Optional<String> accessToken = Optional.absent();
    protected Optional<String> externalId = Optional.absent();
    protected Optional<String> auth = Optional.absent();

    private final Roles roles = new Roles();


    public BasicAuthenticationSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    @Override
    public void invalidate() { //or signOut?
        super.invalidate();
        username = Optional.absent();
        accessToken = Optional.absent();
        externalId = Optional.absent();
        auth = Optional.absent();
        roles.clear();
    }

    @Override
    public boolean authenticate(String username, String password) {
        boolean authenticated;
        if (username == null || password == null) {
            authenticated = false;
        } else {
            LOG.debug("autenticate " + username);
            this.username = Optional.of((username));
            this.accessToken = Optional.of(password);
            authenticated = login();
        }
        return authenticated;
    }

    private boolean login() {
        boolean authenticated = true;
        LOG.debug("login " + username);

        LOG.debug("session logged in " + getId());
//        HttpSession httpSession = ((ServletWebRequest) RequestCycle.get().getRequest()).getContainerRequest().getSession();
//        httpSession.setMaxInactiveInterval(60 * 60);

        signIn(authenticated);
        return authenticated;
    }

    @Override
    public void onInvalidate() {
        super.onInvalidate();
        LOG.debug("session invalidate " + getId());
    }

    @Override
    public Roles getRoles() {
        return roles;
    }
}