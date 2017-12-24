package com.paulhammant.joobyandsitemeshdemo;

import com.paulhammant.sitemeshpageparser.Page;
import com.paulhammant.sitemeshpageparser.parser.HTMLPageParser;
import org.jooby.*;
import org.stringtemplate.v4.ST;

public class AppThatUsesSitemeshAtSendStage extends Jooby {

    {
        get("/", (req, rsp) -> {
            rsp.type("text/html");
            HTMLPageParser pp = new HTMLPageParser();
            // The intention is to see this decorated ....
            Page pg = pp.parse("<html title=\"greeting\"><body>Hello</body></html>".toCharArray());
            rsp.send(decorate(pg.getTitle(), pg.getBody()));
            // ... by the Sitemesh decoration that's to operate before the send() is invoked
        });
    }

    private static String decorate(String title, String body) {

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
        decorator.add("title", title);
        decorator.add("body", body);
        return decorator.render();
    }

    public static void main(final String[] args) {
        run(AppThatUsesSitemeshAtSendStage::new, args);
    }
}