package com.example.a4.repository;

import com.example.a4.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * repository interface for user
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    /**
     * get user by username
     * @param username
     * @return
     */
    Optional<UserInfo> findByName(String username);

    /**
     * get the first free id in the database
     * @return
     */
    @Query("SELECT MIN(t1.id + 1) " +
            "FROM UserInfo t1 " +
            "LEFT JOIN UserInfo t2 " +
            "ON t1.id + 1 = t2.id " +
            "where t2.id = null")
    int getFirstFreeID();

    /**
     * get the number of leagues added by a user
     * @param username
     * @return
     */
    @Query("SELECT COUNT(u.lid) " +
            "FROM League u " +
            "WHERE u.user.name = ?1")
    int getLeagueCounter(String username);

    /**
     * get the number of teams added by a user
     * @param username
     * @return
     */
    @Query("SELECT COUNT(u.tid) " +
            "FROM Team u " +
            "WHERE u.user.name = ?1")
    int getTeamCounter(String username);

    /**
     * get the number of fans added by a user
     * @param username
     * @return
     */
    @Query("SELECT COUNT(u.fid) " +
            "FROM Fan u " +
            "WHERE u.user.name = ?1")
    int getFanCounter(String username);

    /**
     * get the role of a user
     * @param username
     * @return
     */
    @Query("SELECT u.roles " +
            "FROM UserInfo u " +
            "WHERE u.name = ?1")
    String getRoleByUsername(String username);
}
