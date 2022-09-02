package de.test.client;

import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication;
import de.test.pages.TestPage;
import de.test.pages.TestPage2;
import de.test.core.security.BasicAuthenticationSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;

import java.io.Serializable;



@Slf4j
@org.springframework.stereotype.Component
public class MainApplication extends WicketBootSecuredWebApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return super.getConfigurationType();
    }

    @Override
    protected void init() {
        super.init();

        mountPage("/testpage", TestPage.class);
        mountPage("/testpage2", TestPage2.class);

        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

        getPageSettings().setVersionPagesByDefault(true);
        getPageSettings().setCallListenerAfterExpiry(true);
    }

    /**
     * @see Application#getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return TestPage.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return BasicAuthenticationSession.class;
    }
}
