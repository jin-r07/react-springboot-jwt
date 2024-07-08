package com.example.jwt_demo.services;

import com.example.jwt_demo.dto.ReqRes;
import com.example.jwt_demo.entity.User;
import com.example.jwt_demo.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ManageUserService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReqRes registerUser(ReqRes reqRes) {
        ReqRes res = new ReqRes();
        try {
            User user = new User();
            user.setEmail(reqRes.getEmail());
            user.setRole(reqRes.getRole());
            user.setPassword(passwordEncoder.encode(reqRes.getPassword()));
            User result = usersRepo.save(user);
            if (result.getId() >0) {
                res.setUsers(result);
                res.setMessage("User register successful");
                res.setStatusCode(200);
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setError(e.getMessage());
        }
        return res;
    }

    public ReqRes loginUser(ReqRes loginReqRes) {
        ReqRes res = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginReqRes.getEmail(), loginReqRes.getPassword()));
            var user = usersRepo.findByEmail(loginReqRes.getEmail()).orElseThrow();
            var jwt = jwtUtil.generateToken(user);
            var refreshToken = jwtUtil.refreshToken(new HashMap<>(), user);
            res.setStatusCode(200);
            res.setToken(jwt);
            res.setRole(user.getRole());
            res.setRefreshToken(refreshToken);
            res.setExpirationTime("24Hrs");
            res.setMessage("User login successful");

        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqRes) {
        ReqRes res = new ReqRes();
        try {
            String userEmail = jwtUtil.extractUsername(refreshTokenReqRes.getToken());
            User user = usersRepo.findByEmail(userEmail).orElseThrow();
            if (jwtUtil.isTokenValid(refreshTokenReqRes.getToken(), user)) {
                var jwt = jwtUtil.generateToken(user);
                res.setStatusCode(200);
                res.setToken(jwt);
                res.setRefreshToken(refreshTokenReqRes.getToken());
                res.setExpirationTime("24Hrs");
                res.setMessage("Token Refreshed successfully");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ReqRes getAllUsers() {
        ReqRes res = new ReqRes();
        try {
            List<User> list = usersRepo.findAll();
            if (!list.isEmpty()) {
                res.setUserList(list);
                res.setStatusCode(200);
                res.setMessage("List fetched successfully");
            } else {
                res.setStatusCode(404);
                res.setMessage("No users found");
            }
            return res;
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
            return res;
        }
    }

    public ReqRes getUserById(long id) {
        ReqRes res = new ReqRes();
        try {
            User userId = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            res.setUsers(userId);
            res.setStatusCode(200);
            res.setMessage("User with id " + id + " found");
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ReqRes deleteUser(long id) {
        ReqRes res = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findById(id);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(id);
                res.setStatusCode(200);
                res.setMessage("User with id " + id + " deleted successfully");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ReqRes updateUser(long id, User upddatedUser) {
        ReqRes res = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findById(id);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setEmail(upddatedUser.getEmail());
                existingUser.setRole(upddatedUser.getRole());

                if (upddatedUser.getPassword() != null && !upddatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(upddatedUser.getPassword()));
                }
                User saveUser = usersRepo.save(existingUser);
                res.setUsers(saveUser);
                res.setStatusCode(200);
                res.setMessage("User updated successfully");
            } else {
                res.setStatusCode(404);
                res.setMessage("User not found");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ReqRes getMyInfo(String email) {
        ReqRes res = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                res.setUsers(userOptional.get());
                res.setStatusCode(200);
                res.setMessage("Info fetched successfully");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}
