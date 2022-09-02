package de.test.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BasePage extends WebPage {

    public BasePage(PageParameters pageParameters) {
        super(pageParameters);
    }

//    @Override
//    public int getRenderCount() {
//        return 1;
//    }
}
