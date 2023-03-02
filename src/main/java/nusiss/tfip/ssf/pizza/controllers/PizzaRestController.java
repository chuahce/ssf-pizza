package nusiss.tfip.ssf.pizza.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nusiss.tfip.ssf.pizza.models.Order;
import nusiss.tfip.ssf.pizza.services.PizzaService;

@RestController
@RequestMapping(path="/order")
public class PizzaRestController {

  @Autowired
  private PizzaService pizzaSvc;

  @GetMapping(path="{OrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getOrder(@PathVariable String orderId) {
    Optional<Order> opt = pizzaSvc.getOrderByOrderId(orderId);
    if (opt.isEmpty()) {
      JsonObject resp = Json.createObjectBuilder()
          .add("message", "Order %s not found".formatted(orderId))
          .build();
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(resp.toString());
    }
    return ResponseEntity.ok(opt.get().toJSON().toString());
  }
}