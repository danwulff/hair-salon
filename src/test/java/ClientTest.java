import org.junit.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class ClientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteClientsQuery = "DELETE FROM clients *;";
      String deleteStylistsQuery = "DELETE FROM stylists *;";
      con.createQuery(deleteClientsQuery).executeUpdate();
      con.createQuery(deleteStylistsQuery).executeUpdate();
    }
  }

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

  /*@Test
  public void save_savesCategoryIdIntoDB_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Client myClient = new Client("Mow the lawn", myCategory.getId());
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getCategoryId(), myCategory.getId());
  }*/
}
