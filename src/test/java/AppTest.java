import org.fluentlenium.adapter.FluentTest;
import org.sql2o.*;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Test public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Hair Salon");
  }

  @Test public void createStylistAndViewTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Create New Stylist"));
    fill("#name").with("Stephen");
    submit(".btn");
    assertThat(pageSource().contains("Stephen"));
  }

  @Test public void viewStylistPage() {
    goTo("http://localhost:4567/");
    click("a", withText("Create New Stylist"));
    fill("#name").with("Stephen");
    submit(".btn");
    click("a", withText("Stephen"));
    assertThat(pageSource().contains("Stylist Stephen's clients:"));
  }

  @Test public void createClientForStylistAndView() {
    goTo("http://localhost:4567/");
    click("a", withText("Create New Stylist"));
    fill("#name").with("Stephen");
    submit(".btn");
    click("a", withText("Stephen"));
    click("a", withText("Create client"));
    fill("#name").with("Stacy");
    submit(".btn");
    assertThat(pageSource().contains("Stacy"));
  }
}
