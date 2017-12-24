package com.paulhammant.joobyandsitemeshdemo;

import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

public class AppThatUsesSitemeshModuleTest {

  @ClassRule
  public static JoobyRule app = new JoobyRule(new AppThatUsesSitemeshModule());

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
