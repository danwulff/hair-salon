import java.util.Arrays;
import org.junit.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class StylistTest {

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
  public void Stylist_instantiatesCorrectly_true(){
    Stylist myStylist = new Stylist("Sarah");
    assertEquals(true, myStylist instanceof Stylist);
  }

  @Test
  public void getName_stylistInstantiatesWithName_String() {
    Stylist myStylist = new Stylist("Sarah");
    assertEquals("Sarah", myStylist.getName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Stylist.all().size());
  }

  @Test
  public void equals_returnsTrueIfNameAndIdAretheSame_true() {
    Stylist firstStylist = new Stylist("Sarah");
    Stylist secondStylist = new Stylist("Sarah");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Stylist myStylist = new Stylist("Sarah");
    myStylist.save();
    assertTrue(Stylist.all().get(0).equals(myStylist));
  }

  @Test
   public void save_assignsIdToObject_Id() {
     Stylist myStylist = new Stylist("Sarah");
     myStylist.save();
     Stylist savedStylist = Stylist.all().get(0);
     assertEquals(myStylist.getId(), savedStylist.getId());
   }

  @Test
  public void find_findStylistInDatabase_true() {
    Stylist myStylist = new Stylist("Sarah");
    myStylist.save();
    Stylist savedStylist = Stylist.find(myStylist.getId());
    assertTrue(myStylist.equals(savedStylist));
  }

  @Test
  public void update_updatesStylistName_true() {
    Stylist myStylist = new Stylist("Sarah");
    myStylist.save();
    myStylist.update("Bob");
    assertEquals("Bob", Stylist.find(myStylist.getId()).getName());
  }

  @Test
  public void delete_deletesStylist_true() {
    Stylist myStylist = new Stylist("Sarah");
    myStylist.save();
    int myStylistId = myStylist.getId();
    myStylist.delete();
    assertEquals(null, Stylist.find(myStylistId));
  }

  @Test
  public void getClients_retrievesAllClientsFromDatabase_clientsList() {
    Stylist myStylist = new Stylist("Sarah");
    myStylist.save();
    Client firstClient = new Client("Stephen", myStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Dave", myStylist.getId());
    secondClient.save();
    Client[] clients = new Client[] { firstClient, secondClient };
    assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
  }
}
