package com.example.a4.controller;

import com.example.a4.dto.*;
import com.example.a4.entity.ChatHistory;
import com.example.a4.entity.user.UserInfo;
import com.example.a4.exception.EntityNotFoundException;
import com.example.a4.service.ChatHistoryService;
import com.example.a4.service.JwtService;
import com.example.a4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ChatHistoryService chatHistoryService;

    /**
     * register a new user
     * @param userInfo
     * @return
     */
    @PostMapping("/register")
    public DateAndCode addUser(@RequestBody UserInfo userInfo){
        userInfo.setId(userService.getFirstFreeID());
        userInfo.setRoles("ROLE_USER");
        userInfo.setRecordsOnPage(100);
        userInfo.setEnabled(false);
        return userService.addUser(userInfo);
    }

    /**
     * activate a user within 10 minutes
     * @param code
     * @param userInfo
     * @return
     */
    @PutMapping("/register/verify/{code}")
    public String activateUser(@PathVariable String code, @RequestBody UserInfo userInfo) {
        return userService.verifyUser(code, userInfo);
    }

    /**
     * authenticate a user
     * @param authRequest
     * @return
     */
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(authRequest.getUsername());
        else
            throw new UsernameNotFoundException("invalid user request");
    }

    /**
     * get user by username
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    public Optional<UserInfo> getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    /**
     * get user's username by token
     * @param token
     * @return
     */
    @GetMapping("/getUsername/{token}")
    public String getUsernameFromToken(@PathVariable String token) {
        return jwtService.extractUsername(token);
    }

    /**
     * get the number of leagues that a user added
     * @param username
     * @return
     */
    @GetMapping("/{username}/leagues")
    public int getLeagueCounter(@PathVariable String username){
        return userService.getLeagueCounter(username);
    }

    /**
     * get the number of teams that a user added
     * @param username
     * @return
     */
    @GetMapping("/{username}/teams")
    public int getTeamCounter(@PathVariable String username){
        return userService.getTeamCounter(username);
    }

    /**
     * get the number of fans that a user added
     * @param username
     * @return
     */
    @GetMapping("/{username}/fans")
    public int getFanCounter(@PathVariable String username){
        return userService.getFanCounter(username);
    }

    /**
     * get the user role
     * @param username
     * @return
     */
    @GetMapping("/{username}/role")
    public String getUserRole(@PathVariable String username) {
        return userService.getUserRole(username);
    }

    /**
     * get the page number offset, with pageSize entities
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/stats/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<UserInfo>> findAllUsers(@PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException {
        Page<UserInfo> allUsers = userService.findUsersWithPagination(offset, pageSize);
        return ResponseEntity.ok(allUsers);
    }

    /**
     * update for a user their role and number of entities per page
     * @param username
     * @param rolePage
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    @PutMapping("/update/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserInfo> updateRoleAndPage(@PathVariable String username, @RequestBody RolePage rolePage, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        return ResponseEntity.ok(userService.updateRolePage(username, rolePage, authorizationHeader));
    }

    /**
     * update for a user their number of entities per page
     * @param username
     * @param noPages
     * @return
     * @throws Exception
     */
    @PutMapping("/update/{username}/pages/{noPages}")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<UserInfo> updatePage(@PathVariable String username, @PathVariable int noPages) throws Exception {
        return ResponseEntity.ok(userService.updatePage(username, noPages));
    }

    /**
     * save the messages from the chat in the database
     * @param chatHistoryRequest
     * @return
     */
    @PostMapping("/chat/postmessage")
    public ResponseEntity<ChatHistory> publishMessage(@RequestBody ChatHistoryRequest chatHistoryRequest) {
        return ResponseEntity.ok(chatHistoryService.saveChat(chatHistoryRequest));
    }
}
