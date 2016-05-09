import org.junit.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class ClientTest {

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Test
  public void Client_instantiatesCorrectly_true(){
    Client myClient = new Client("Stephen", 1);
    assertEquals(true, myClient instanceof Client);
  }

  @Test
  public void getName_clientInstantiatesWithName_String(){
    Client myClient = new Client("Stephen", 1);
    assertEquals("Stephen", myClient.getName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Client.all().size());
  }

  @Test
  public void equals_returnsTrueIfNameAndIdAreTheSame_true() {
    Client firstClient = new Client("Stephen", 1);
    Client secondClient = new Client("Stephen", 1);
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_savesClientObjectCorrectly_true() {
    Client myClient = new Client("Stephen", 1);
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

  @Test
  public void save_assignsIdToObject_Id() {
    Client myClient = new Client("Stephen", 1);
    myClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(myClient.getId(), savedClient.getId());
  }

  @Test
  public void find_findsClientInDatabase_True() {
    Client myClient = new Client("Stephen", 1);
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertTrue(myClient.equals(savedClient));
  }

  @Test
  public void update_updatesClientName_true() {
    Client myClient = new Client("Stephen", 1);
    myClient.save();
    myClient.update("Dave");
    assertEquals("Dave", Client.find(myClient.getId()).getName());
  }

  @Test
  public void delete_deletesClient_true() {
    Client myClient = new Client("Stephen", 1);
    myClient.save();
    int myClientId = myClient.getId();
    myClient.delete();
    assertEquals(null, Client.find(myClientId));
  }

  @Test
  public void save_savesStylistIdIntoDB_true() {
    Stylist myStylist = new Stylist("Sarah");
    myStylist.save();
    Client myClient = new Client("Stephen", myStylist.getId());
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getStylistId(), myStylist.getId());
  }
}
