package com.example.jwt_demo.controller;

import com.example.jwt_demo.dto.ReqRes;
import com.example.jwt_demo.entity.User;
import com.example.jwt_demo.services.ManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private ManageUserService manageUserService;

    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reqRes) {
        return ResponseEntity.ok(manageUserService.registerUser(reqRes));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes reqRes) {
        return ResponseEntity.ok(manageUserService.loginUser(reqRes));
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes reqRes) {
        return ResponseEntity.ok(manageUserService.refreshToken(reqRes));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers() {
        return ResponseEntity.ok(manageUserService.getAllUsers());
    }

    @GetMapping("/admin/get-user/{id}")
    public ResponseEntity<ReqRes> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(manageUserService.getUserById(id));
    }

    @DeleteMapping("/admin/delete-user-user/{id}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(manageUserService.deleteUser(id));
    }

    @GetMapping("/admin/update-user/{id}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Long id, @RequestBody User reqRes) {
        return ResponseEntity.ok(manageUserService.updateUser(id, reqRes));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes res = manageUserService.getMyInfo(email);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}