package edu.baylor.ecs.rt.controller;

import edu.baylor.ecs.rt.model.auth.User;
import edu.baylor.ecs.rt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    @ResponseBody
    public void create(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PostMapping("/edit")
    @ResponseBody
    public void edit( @RequestBody User user) {
    	System.out.println(user.getId());
        userService.updateUser(user);
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<User> findById(@PathVariable(value = "id") long id) {
        User customer = userService.findById(id);
        return new ResponseEntity(customer, HttpStatus.OK);
    }
}
