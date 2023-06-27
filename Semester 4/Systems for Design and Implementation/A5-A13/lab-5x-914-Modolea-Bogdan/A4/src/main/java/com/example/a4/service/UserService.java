package com.example.a4.service;

import com.example.a4.dto.DateAndCode;
import com.example.a4.dto.RolePage;
import com.example.a4.entity.user.UserInfo;
import com.example.a4.repository.UserInfoRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * the user service implementation
 */
@Service
public class UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Environment env;

    /**
     * register a new user
     * @param userInfo
     * @return
     */
    public DateAndCode addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        String randomCode = RandomString.make(16);
        userInfo.setVerificationCode(randomCode);
        userInfo.setConfirmationCodeSentAt(LocalDateTime.now());

        userInfoRepository.save(userInfo);
        return new DateAndCode(userInfo.getVerificationCode(), userInfo.getConfirmationCodeSentAt());
    }

    /**
     * verify a user within 10 minutes
     * @param code
     * @param userInfo
     * @return
     */
    public String verifyUser(String code, UserInfo userInfo) {
        System.out.println(userInfo.getConfirmationCodeSentAt());
        if (Objects.equals(code, userInfo.getVerificationCode()) && Duration.between(userInfo.getConfirmationCodeSentAt(), LocalDateTime.now()).toMinutes() <= 10) {
            userInfo.setEnabled(true);
            UserInfo user = userInfoRepository.findByName(userInfo.getName()).get();
            user.setEnabled(true);
            System.out.println(user);
            userInfoRepository.save(user);
            return "user validated";
        }

        return "user not valid";
    }

    /**
     * get the first free id in the database
     * @return
     */
    public int getFirstFreeID() {
        return userInfoRepository.getFirstFreeID();
    }

    /**
     * get a user by username
     * @param username
     * @return
     */
    public Optional<UserInfo> getUser(String username) {
        return userInfoRepository.findByName(username);
    }

    /**
     * get the number of leagues a user has added
     * @param username
     * @return
     */
    public int getLeagueCounter(String username) {
        return userInfoRepository.getLeagueCounter(username);
    }

    /**
     * get the number of teams a user has added
     * @param username
     * @return
     */
    public int getTeamCounter(String username) {
        return userInfoRepository.getTeamCounter(username);
    }

    /**
     * get the number of fans a user has added
     * @param username
     * @return
     */
    public int getFanCounter(String username) {
        return userInfoRepository.getFanCounter(username);
    }

    /**
     * get a user role based on username
     * @param username
     * @return
     */
    public String getUserRole(String username) {
        return userInfoRepository.getRoleByUsername(username);
    }

    /**
     * get all users on the page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     */
    public Page<UserInfo> findUsersWithPagination(int offset, int pageSize) {
        return userInfoRepository.findAll(PageRequest.of(offset, pageSize));
    }

    /**
     * update a user's role and number of entities that can see on page
     * @param username
     * @param rolePage
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    public UserInfo updateRolePage(String username, RolePage rolePage, String authorizationHeader) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameAdmin = authentication.getName();

        UserInfo userInfo = userInfoRepository.findByName(username).get();
        userInfo.setRoles(rolePage.getRole());
        userInfo.setRecordsOnPage(rolePage.getNumberOfPages());

        UserInfo adminInfo = userInfoRepository.findByName(usernameAdmin).get();

        System.out.println(usernameAdmin);
        System.out.println(userInfo.getRoles());

        if (Objects.equals(adminInfo.getRoles(), "ROLE_ADMIN"))
            return userInfoRepository.save(userInfo);
        throw new Exception("Not allowed to edit user!");
    }

    /**
     * update a user's number of entities that can see on page
     * @param username
     * @param noPages
     * @return
     * @throws Exception
     */
    public UserInfo updatePage(String username, int noPages) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserInfo userInfo = userInfoRepository.findByName(username).get();
        userInfo.setRecordsOnPage(noPages);

        return userInfoRepository.save(userInfo);
    }

    /**
     * update the number of entities per page for every registered user
     * @param noPages
     * @throws SQLException
     */
    public void updateAllUsersPage(int noPages) throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(
                    env.getRequiredProperty("spring.datasource.url"),
                    env.getRequiredProperty("spring.datasource.username"),
                    env.getRequiredProperty("spring.datasource.password"));
            List<String> statementsStrings = Arrays.asList(
                    String.format("UPDATE esport2.user_info SET records_on_page = %d WHERE id > 0", noPages)
            );
            statementsStrings.forEach(statementString -> {
                try {
                    Statement statement = conn.createStatement();
                    statement.execute(statementString);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}