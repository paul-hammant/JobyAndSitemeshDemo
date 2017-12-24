package com.paulhammant.joobyandsitemeshdemo;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.jooby.test.JoobyRule;
import org.jooby.test.MockRouter;
import org.junit.ClassRule;
import org.junit.Test;

public class AppThatUsesSitemeshAtSendStageTest {

  @ClassRule
  public static JoobyRule app = new JoobyRule(new AppThatUsesSitemeshAtSendStage());

  @Test
  public void integrationTest() {
    get("/")
        .then()
        .assertThat()
        .body(equalTo("<html><body><table border=\"1\">\n" +
                "    <h2>greeting is an excellent title</h2>\n" +
                "    <head>\n" +
                "    <tr><td colspan=\"3\">XXXXXXXXX</td></tr>    <tr><td>X</td>    <td>Hello</td>\n" +
                "    <td>X</td></tr>    <tr><td colspan=\"3\">XXXXXXXXX</td></tr></table></body></html>"))
        .statusCode(200)
        .contentType("text/html;charset=UTF-8");
  }

}
