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

  //create a stylist and view list of stylists
  @Test public void createStylistAndViewList() {
    goTo("http://localhost:4567/");
    click("a", withText("Create New Stylist"));
    fill("#name").with("Stephen");
    submit(".btn");
    assertThat(pageSource().contains("Stephen"));
  }

  //view individual stylist page
  @Test public void viewAStylistPage() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    goTo("http://localhost:4567/stylists");
    click("a", withText("Stephen"));
    assertThat(pageSource().contains("Stylist Stephen's clients:"));
  }

  //view stylist edit page
  @Test public void viewStylistEditPage() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    String path = String.format("http://localhost:4567/stylists/%d", myStylist.getId());
    goTo(path);
    click("a", withText("Edit stylist"));
    assertThat(pageSource().contains("Change Stephen's name"));
  }

  //edit stylist name
  @Test public void editAStylistName() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    String path = String.format("http://localhost:4567/stylists/%d/edit", myStylist.getId());
    goTo(path);
    fill("#name").with("Sarah");
    submit(".btn");
    assertThat(pageSource().contains("Sarah"));
  }

  //delete stylist
  @Test public void deleteAStylist() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    String path = String.format("http://localhost:4567/stylists/%d/edit", myStylist.getId());
    goTo(path);
    click("button", withText("Delete this stylist"));
    assertEquals(0, Stylist.all().size());
  }

  //add a client to an individual stylist
  @Test public void createClientForStylistAndViewList() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    String path = String.format("http://localhost:4567/stylists/%d/clients/new", myStylist.getId());
    goTo(path);
    fill("#name").with("Betsy");
    submit(".btn");
    assertThat(pageSource().contains("Besty"));
  }

  //view individual client page
  @Test public void viewClientPage() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    Client myClient = new Client("Betsy", myStylist.getId());
    myClient.save();
    String path = String.format("http://localhost:4567/stylists/%d/clients/%d", myClient.getStylistId(), myClient.getId());
    goTo(path);
    assertThat(pageSource().contains("Client: Betsy"));
  }

  //view client edit page
  @Test public void viewClientEditPage() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    Client myClient = new Client("Betsy", myStylist.getId());
    myClient.save();
    String path = String.format("http://localhost:4567/stylists/%d/clients/%d/edit", myClient.getStylistId(), myClient.getId());
    goTo(path);
    assertThat(pageSource().contains("Change Betsy's name"));
  }

  //edit a client name
  @Test public void editClientAndView() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    Client myClient = new Client("Betsy", myStylist.getId());
    myClient.save();
    String path = String.format("http://localhost:4567/stylists/%d/clients/%d/edit", myClient.getStylistId(), myClient.getId());
    goTo(path);
    fill("#name").with("Bob");
    submit(".btn");
    assertThat(pageSource().contains("Bob"));
  }

  //delete a client
  @Test public void deleteAClientAnd() {
    Stylist myStylist = new Stylist("Stephen");
    myStylist.save();
    Client myClient = new Client("Betsy", myStylist.getId());
    myClient.save();
    String path = String.format("http://localhost:4567/stylists/%d/clients/%d/edit", myClient.getStylistId(), myClient.getId());
    goTo(path);
    click("button", withText("Delete this client"));
    assertThat(pageSource().contains("Bob"));
    assertEquals(0, myStylist.getClients().size());
  }
}
