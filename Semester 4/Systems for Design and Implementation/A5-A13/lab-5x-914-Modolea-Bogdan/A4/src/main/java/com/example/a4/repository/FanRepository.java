package com.example.a4.repository;

import com.example.a4.dto.FanAndNoTeams;
import com.example.a4.dto.FanGetAll;
import com.example.a4.dto.FansAndCounter;
import com.example.a4.dto.NumberNationalities;
import com.example.a4.entity.Fan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repository interface for fan
 */
@Repository
public interface FanRepository extends JpaRepository<Fan, Integer> {
    /**
     * filter fans by age
     * @param age
     * @param pageable
     * @return
     */
    @Query("SELECT new com.example.a4.dto.FanGetAll(" +
            "f.fid, f.name, f.age, f.nationality, f.occupation, f.placeOfBirth) " +
            "FROM Fan f " +
            "WHERE f.age > ?1"
    )
    Page<FanGetAll> filterFansByAge(int age, Pageable pageable);

    /**
     * get a fan and number of teams he supports
     * @param pageable
     * @return
     */
    @Query("SELECT new com.example.a4.dto.FanAndNoTeams(" +
            "f.fid, f.name, f.age, f.nationality, f.occupation, f.placeOfBirth, COUNT(t.team.tid), f.user.name) " +
            "FROM Fan f LEFT JOIN f.supporter t " +
            "GROUP BY f.fid"
    )
    Page<FanAndNoTeams> findFanAndNoTeams(Pageable pageable);

    /**
     * get the every nationality and its counter
     * @param pageable
     * @return
     */
    @Query("SELECT new com.example.a4.dto.NumberNationalities(" +
            "f.nationality, COUNT(f.fid)) " +
            "FROM Fan f " +
            "GROUP BY f.nationality"
    )
    Page<NumberNationalities> findNumberNationalities(Pageable pageable);

    /**
     * get the number of fans that appeared in every year
     * @return
     */
    @Query("SELECT new com.example.a4.dto.FansAndCounter(" +
            "f.fanSince, COUNT(0)) " +
            "FROM FanOfTeam f " +
            "GROUP BY f.fanSince"
    )
    List<FansAndCounter> getFansAndCounter();
}
