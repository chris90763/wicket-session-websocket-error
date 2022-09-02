package de.test.pages;

import de.test.core.PageLinkWithCheck;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.java.util.function.serializable.SerializableConsumer;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TestPanel extends GenericPanel<String> {
    private IModel<AtomicInteger> counterModel = new Model<>(new AtomicInteger(0));
    private AbstractDefaultAjaxBehavior loadAsync;

    public TestPanel(String id, IModel<String> model, Class<? extends Page> clazz, Integer renderCount, SerializableConsumer<AjaxRequestTarget> callback) {
        super(id, model);


        Label counter = new Label("counter", counterModel);
        counter.setOutputMarkupId(true);
        add(counter);
        add(new Label("label", model));
        Label click = new Label("button", new Model<>("click for counter"));
        click.add(new AjaxEventBehavior("click") {

            @Override
            protected void onEvent(AjaxRequestTarget target) {
                counterModel.getObject().incrementAndGet();
                target.add(counter);
            }
        });
        add(click);

        add(new WebSocketBehavior() {

        });

        BookmarkablePageLink<String> link = new PageLinkWithCheck("link", clazz, new PageParameters());
        add(link);

        add(new Label("random", new Random().nextInt(101)));

        add(new Label("renderCount", renderCount));
        Label refresh = new Label("refresh", "refresh Panel");
        refresh.setOutputMarkupId(true);
        refresh.add(new AjaxEventBehavior("click") {

            @Override
            protected void onEvent(AjaxRequestTarget target) {
                callback.accept(target);
            }
        });
        add(refresh);

        Model<String> lazyModel = new Model<>("");
        Label lazy = new Label("lazy", lazyModel);
        lazy.setOutputMarkupId(true);
        add(lazy);
        loadAsync = new TaskAjaxBehavior(lazy, lazyModel);
        add(loadAsync);
    }



    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(
                loadAsync.getCallbackScript()
        ));
    }

    private class TaskAjaxBehavior extends AbstractDefaultAjaxBehavior
//            implements IAjaxIndicatorAware
    {
        private Label label;
        private Model<String> lazyModel;

        public TaskAjaxBehavior(Label label, Model<String> lazyModel) {
            this.label = label;
            this.lazyModel = lazyModel;
        }

        @Override
        protected void respond(AjaxRequestTarget target) {
            //just doing stuff
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lazyModel.setObject("initialized");
            target.add(label);
        }

//        @Override
//        public String getAjaxIndicatorMarkupId() {
//            return "todoIndicator";
//        }
    }
}
