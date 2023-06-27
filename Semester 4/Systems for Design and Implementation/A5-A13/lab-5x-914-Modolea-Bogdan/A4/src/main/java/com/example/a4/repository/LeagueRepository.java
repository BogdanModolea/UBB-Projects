package com.example.a4.repository;

import com.example.a4.dto.LeaguesAndNoTeams;
import com.example.a4.dto.NationalitiesAndLeagues;
import com.example.a4.entity.League;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * repository interface for league
 */
public interface LeagueRepository extends JpaRepository<League, Integer> {
    /**
     * get a league with the number of nationalities that support it
     * @param pageable
     * @return
     */
    @Query("SELECT new com.example.a4.dto.NationalitiesAndLeagues(" +
            "l.lid, l.abbreviation, l.region, COUNT(f.id)) " +
            "FROM Team t JOIN t.league l " +
            "JOIN t.supporter f " +
            "GROUP BY l.lid "
    )
    Page<NationalitiesAndLeagues> getLeaguesByNations(Pageable pageable);

    /**
     * get a league by name
     * @param league
     * @return
     */
    @Query(
            "FROM League l " +
            "WHERE l.abbreviation like ?1"
    )
    League getLeagueByName(String league);

    /**
     * get a league and its number of teams
     * @param pageable
     * @return
     */
    @Query("SELECT new com.example.a4.dto.LeaguesAndNoTeams(" +
            "l.lid, l.abbreviation, l.region, l.year, l.bestPlayer, l.audience, l.description, COUNT(t.tid), l.user.name) " +
            "FROM League l LEFT JOIN l.teams t " +
            "GROUP BY l.lid "
    )
    Page<LeaguesAndNoTeams> getLeaguesAndNoTeams(Pageable pageable);
}
