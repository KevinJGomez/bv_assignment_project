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



    /*
     * To generate a token for the user
     *
     * */
    public String generateToken(RequestUser requestUser) {
        try{
            logger.info(" AuthService: generateToken Method intiated.....");
            return jwtTokenUtil.generateToken(requestUser);
        }catch(Exception e) {
            logger.info(" AuthService: generateToken Method Failed.....Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Get the user by username
     *
     * */
    public RequestUser findByUsername(String uuid, String username) {
        try {
            logger.info(uuid + " AuthService: findByUsername Method initiated...Username: " + username);

            RequestUser user = userRepository.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return user;
        } catch (Exception e) {
            logger.error(uuid + ": ERROR: AuthService findByUsername: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /*
     * To Save the user
     *
     * */
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

    /*
     * To extract username from Token with the help of JwtTokenUtil
     *
     * */
    public String getUsernameFromToken(String token) {
        try{
            logger.info(" AuthService: getUsernameFromToken Method initiated.....");
            return jwtTokenUtil.extractUsername(token);
        }catch(Exception e){
            logger.info(" AuthService: getUsernameFromToken Method Failed.....Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /*
     * To validate the received token
     *
     * */
    public ReturnTokenValidity validateToken(String uuid, String token) {
        ReturnTokenValidity send = new ReturnTokenValidity();
        try{
            logger.info(uuid + " AuthService: validateToken Method Initiated...");

            String username = jwtTokenUtil.extractUsername(token);
            RequestUser user = findByUsername("1123", username);

            String checkToken = String.valueOf(jwtTokenUtil.validateToken(token, user));
            String checkRole = jwtTokenUtil.extractRole(token);
            logger.info(uuid + " AuthService: validateToken Method | Received Information.....");
            send.setValidToken(checkToken);
            send.setRole(checkRole);
            send.setUsername(username);
            return send;
        }catch(Exception e){
            logger.info(uuid + " AuthService: validateToken Method Failed......Error: " + e.getMessage());
            e.printStackTrace();
            send.setValidToken("false");
            return send;
        }
    }
}
