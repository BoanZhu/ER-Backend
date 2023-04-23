package ic.ac.er_backend.controller;

import ic.ac.er_backend.dto.CreateStrongEntityRequest;
import ic.ac.er_backend.dto.CreateStrongEntityResponse;
import ic.ac.er_backend.dto.TryToLoginRequest;
import ic.ac.er_backend.dto.TryToLoginResponse;
import io.github.MigadaTang.Entity;
import io.github.MigadaTang.Schema;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/er/login")
public class LoginController {

  @PostMapping(value = "/check_account", consumes = MediaType.APPLICATION_JSON_VALUE)
//  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public TryToLoginResponse checkAccount(@RequestBody @Valid TryToLoginRequest request) {
    String username = request.getUsername();
    String password = request.getPassword();
    if (username.equals("test") && password.equals("test")) {
      return new TryToLoginResponse("Login successfully");
    } else {
      return new TryToLoginResponse("Fail to login!");
    }
  }

}
