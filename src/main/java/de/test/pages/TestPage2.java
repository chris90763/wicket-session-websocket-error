package de.test.pages;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class TestPage2 extends BasePage {

    public TestPage2(PageParameters pageParameters) {
        super(pageParameters);
        add(new TestPanel("panel", new Model<>("Test2"), TestPage.class, getRenderCount(), (target) -> target.add(TestPage2.this)));
    }
}
