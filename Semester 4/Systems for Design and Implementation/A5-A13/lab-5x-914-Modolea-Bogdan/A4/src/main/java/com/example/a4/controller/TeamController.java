package com.example.a4.controller;

import com.example.a4.dto.*;
import com.example.a4.entity.Fan;
import com.example.a4.entity.FanOfTeam;
import com.example.a4.entity.League;
import com.example.a4.entity.Team;
import com.example.a4.entity.user.UserInfo;
import com.example.a4.exception.EntityNotFoundException;
import com.example.a4.service.FanService;
import com.example.a4.service.LeagueService;
import com.example.a4.service.TeamService;
import com.example.a4.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService service;

    /**
     * add a new team
     * @param teamRequest
     * @param authorizationHeader
     * @return
     * @throws EntityNotFoundException
     */
    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody @Valid TeamRequest teamRequest, @RequestHeader("Authorization") String authorizationHeader) throws EntityNotFoundException {
        return new ResponseEntity<>(service.saveTeam(teamRequest), HttpStatus.CREATED);
    }

    /**
     * add a new fan to a team
     * @param teamID
     * @param fanOfTeam
     * @return
     * @throws EntityNotFoundException
     */
    @PostMapping("/{teamID}/fans")
    public ResponseEntity<Team> addTeamFans(@PathVariable int teamID, @RequestBody FanAndTeam fanOfTeam) throws EntityNotFoundException {
        return new ResponseEntity<>(service.saveTeamFans(teamID, fanOfTeam), HttpStatus.CREATED);
    }

    /**
     * get all teams
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping
    public ResponseEntity<List<TeamGetAll>> findAllTeams() throws EntityNotFoundException {
        return ResponseEntity.ok(service.getTeams());
    }

    /**
     * get all teams sorted by a field
     * @param field
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/page/{field}")
    public ResponseEntity<List<TeamGetAll>> findAllTeamsWithSort(@PathVariable String field) throws EntityNotFoundException {
        List<TeamGetAll> allTeams = service.findTeamsWithSorting(field);
        return ResponseEntity.ok(allTeams);
    }

    /**
     * get all teams from the page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<Team>> findAllTeamsWithPagination(@PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException {
        Page<Team> allTeams = service.findTeamsWithPagination(offset, pageSize);
        return ResponseEntity.ok(allTeams);
    }

    /**
     * get all teams from the page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/stats/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<TeamAndNoFans>> findAllTeamsAndNoFans(@PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException {
        Page<TeamAndNoFans> allTeams = service.findTeamsWithNoFans(offset, pageSize);
        return ResponseEntity.ok(allTeams);
    }

    /**
     * get team by id
     * @param teamID
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{teamID}")
    public ResponseEntity<TeamByID> findTeamByID(@PathVariable int teamID) throws EntityNotFoundException {
        return ResponseEntity.ok(service.getTeamById(teamID));
    }

    /**
     * update a team
     * @param teamID
     * @param teamRequest
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    @PutMapping("/{teamID}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<Team> updateTeam(@PathVariable int teamID, @RequestBody @Valid TeamRequest teamRequest, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        return ResponseEntity.ok(service.updateTeam(teamID, teamRequest, authorizationHeader));
    }

    /**
     * delete a team
     * @param teamID
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{teamID}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<Void> deleteTeam(@PathVariable int teamID, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        service.deleteTeam(teamID, authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    /**
     * delete a fan of a team
     * @param teamID
     * @param fanID
     * @return
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/{teamID}/fans/{fanID}")
    public ResponseEntity<Void> deleteTeamFan(@PathVariable int teamID, @PathVariable int fanID) throws EntityNotFoundException {
        service.deleteTeamFan(teamID, fanID);
        return ResponseEntity.noContent().build();
    }

    /**
     * get a team by name
     * @param teamName
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/getTeamByName/{teamName}")
    public ResponseEntity<Team> getTeamByName(@PathVariable String teamName) throws EntityNotFoundException {
        return ResponseEntity.ok(service.getTeamByName(teamName));
    }
}
