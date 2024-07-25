package com.bv.assignment.leave_request_api.service;

import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.models.ReturnTokenValidity;
import com.bv.assignment.leave_request_api.repository.UserRepository;
import com.bv.assignment.leave_request_api.util.JwtTokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LogManager.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String generateToken(RequestUser requestUser) {
        return jwtTokenUtil.generateToken(requestUser);
    }

    public RequestUser findByUsername(String uuid, String username) {
        try {
            logger.info(uuid + " AuthService: findByUsername Method Called...Username: " + username);

            RequestUser user = userRepository.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return user;
        } catch (Exception e) {
            logger.error(uuid + ": ERROR: AuthService findByUsername: ");
            e.printStackTrace();
            return null;
        }
    }

    public RequestUser saveUser(String uuid, RequestUser user) {
        try {
            logger.info(uuid + " AuthService: saveUser Method Called...Username: " + user.getUsername());

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(user.getRole());

            return userRepository.save(user);
        } catch (Exception e) {
            logger.error(uuid + ": ERROR: AuthService saveUser: " + user.getUsername());
            e.printStackTrace();
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        return jwtTokenUtil.extractUsername(token);
    }

    public String getRoleFromToken(String token) {
        return jwtTokenUtil.extractRole(token);
    }

    public ReturnTokenValidity validateToken(String token) {
        ReturnTokenValidity send = new ReturnTokenValidity();
        try{
            String username = jwtTokenUtil.extractUsername(token);
            RequestUser user = findByUsername("1123", username);

            String checkToken = String.valueOf(jwtTokenUtil.validateToken(token, user));
            String checkRole = jwtTokenUtil.extractRole(token);

            send.setValidToken(checkToken);
            send.setRole(checkRole);
            send.setUsername(username);
            return send;
        }catch(Exception e){
            e.printStackTrace();
            send.setValidToken("false");
            return send;
        }
    }
}
