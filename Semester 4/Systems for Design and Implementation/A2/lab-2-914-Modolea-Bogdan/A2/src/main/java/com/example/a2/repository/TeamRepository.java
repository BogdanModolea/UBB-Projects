package com.example.a2.repository;

import com.example.a2.dto.LeagueIdAndTeams;
import com.example.a2.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
//    @Query("select new com.example.a2.dto.LeagueIdAndTeams(l.lid, l.teams) from League l")
//    List<LeagueIdAndTeams> getAllTeams();

}
