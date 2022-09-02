package de.test.pages;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class TestPage extends BasePage {

    public TestPage(PageParameters pageParameters) {
        super(pageParameters);
        add(new TestPanel("panel", new Model<>("Test"), TestPage2.class, getRenderCount(), (target) -> target.add(TestPage.this)));
    }
}
