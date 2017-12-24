package com.paulhammant.joobyandsitemeshdemo;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import com.paulhammant.sitemeshpageparser.Page;
import com.paulhammant.sitemeshpageparser.parser.HTMLPageParser;
import com.typesafe.config.ConfigFactory;
import org.jooby.Env;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.Renderer;
import org.stringtemplate.v4.ST;

public class AppThatUsesSitemeshModule extends Jooby {

    public class SitemeshModule implements Module {

        public void configure(Env env, com.typesafe.config.Config config, Binder binder) {
            Multibinder.newSetBinder(binder, Renderer.class).addBinding().toInstance((v, ctx) -> {
                HTMLPageParser pp = new HTMLPageParser();
                Page pg = pp.parse(v.toString().toCharArray());

                String testDecorator = "<html><body><table border=\"1\">\n" +
                        "    <h2>$title$ is an excellent title</h2>\n" +
                        "    <head>\n" +
                        "    <tr><td colspan=\"3\">XXXXXXXXX</td></tr>" +
                        "    <tr><td>X</td>" +
                        "    <td>$body$</td>\n" +
                        "    <td>X</td></tr>" +
                        "    <tr><td colspan=\"3\">XXXXXXXXX</td></tr>" +
                        "</table></body></html>";

                ST decorator = new ST(testDecorator, '$', '$');
                decorator.add("title", pg.getTitle());
                decorator.add("body", pg.getBody());

                ctx.type(MediaType.html).send(decorator.render());
            });
        }

        public com.typesafe.config.Config config() {
            return ConfigFactory.parseResources(this.getClass(), "sitemesh.conf");
        }
    }

    {
        use(new SitemeshModule());
        get("/", (req, rsp) -> {
            rsp.type("text/html");
            // The intention is to see this decorated ...
            rsp.send("<html title=\"greeting\"><body>Hello</body></html>");
            // ... by that use() level registered SitemeshModule
        });
    }

    public static void main(final String[] args) {
        run(AppThatUsesSitemeshModule::new, args);
    }
}