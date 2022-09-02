package de.test.core;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PageLinkWithCheck extends BookmarkablePageLink<String> {

    public <C extends Page> PageLinkWithCheck(String id, Class<C> pageClass, PageParameters parameters) {
        super(id, pageClass, parameters);
        setOutputMarkupId(true);

        add(new AjaxEventBehavior("click") {

            @Override
            protected void onEvent(AjaxRequestTarget target) {
                doAction(pageClass, parameters);
            }

            private  void doAction(Class<C> pageClass, PageParameters parameters) {
                setResponsePage(pageClass, parameters);
            }
        });
    }
}
