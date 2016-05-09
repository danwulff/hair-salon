import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {

  public static void main (String[] args){
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    //index
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //list all stylists
    get("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //page for stylist creation form
    get("/stylists/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylists-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //create new stylist object
    post("/stylists", (request, response) -> {
      String name = request.queryParams("name");
      Stylist newStylist = new Stylist(name);
      newStylist.save();
      response.redirect("/stylists");
      return null;
    });

    //list all stylists
    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("clients", stylist.getClients());
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //individual stylist edit form
    get("/stylists/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //change stylist name
    post("/stylists/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int stylistId = Integer.parseInt(request.params(":id"));
      String name = request.queryParams("name");

      Stylist stylist = Stylist.find(stylistId);
      stylist.update(name);

      model.put("stylist", stylist);

      response.redirect("/stylists/" + stylistId);
      return null;
    });

    //delete stylist
    post("/stylists/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int stylistId = Integer.parseInt(request.params(":id"));

      Stylist stylist = Stylist.find(stylistId);
      stylist.delete();

      response.redirect("/stylists/");
      return null;
    });

    //page for client creation format
    get("/stylists/:id/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/client-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //create new client object
    post("/stylists/:id/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int stylistId = Integer.parseInt(request.queryParams("stylist_id"));
      String clientName = request.queryParams("name");

      Client myClient = new Client(clientName, stylistId);
      myClient.save();

      response.redirect("/stylists/" + stylistId);
      return null;
    });

    //page for client creation format
    get("/stylists/:id/clients/:client_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":client_id")));
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //page for client creation format
    get("/stylists/:id/clients/:client_id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":client_id")));
      model.put("client", client);
      model.put("template", "templates/client-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //create new client object
    post("/stylists/:id/clients/:client_id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int clientId = Integer.parseInt(request.queryParams("client_id"));
      Client myClient = Client.find(clientId);
      int stylistId = myClient.getStylistId();
      myClient.delete();
      response.redirect("/stylists/" + stylistId + "/clients");
      return null;
    });
  }
}
