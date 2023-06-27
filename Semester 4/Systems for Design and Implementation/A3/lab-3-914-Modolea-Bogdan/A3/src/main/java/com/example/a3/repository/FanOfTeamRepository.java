package com.example.a3.repository;

import com.example.a3.entity.FanOfTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FanOfTeamRepository extends JpaRepository<FanOfTeam, Integer> {
//    @Modifying
//    @Query("delete from FanOfTeam ft where ft.id=:id")
//    public void deleteFanOfTeamByID(@Param("id") int id);
}
