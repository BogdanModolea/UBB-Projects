package com.example.a4.controller;

import com.example.a4.dto.LeagueGetAll;
import com.example.a4.dto.LeagueRequest;
import com.example.a4.dto.LeaguesAndNoTeams;
import com.example.a4.dto.NationalitiesAndLeagues;
import com.example.a4.entity.League;
import com.example.a4.entity.Team;
import com.example.a4.exception.EntityNotFoundException;
import com.example.a4.service.LeagueService;
import com.example.a4.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/leagues")
public class LeagueController {
    @Autowired
    private LeagueService service;

    /**
     * add a new league
     * @param leagueRequest
     * @return
     * @throws EntityNotFoundException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<League> addLeague(@RequestBody @Valid LeagueRequest leagueRequest) throws EntityNotFoundException {
        return new ResponseEntity<>(service.saveLeague(leagueRequest), HttpStatus.CREATED);
    }

    /**
     * find all leagues
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping
    public ResponseEntity<List<LeagueGetAll>> findAllLeagues() throws EntityNotFoundException{
        return ResponseEntity.ok(service.getLeagues());
    }

    /**
     * get all leagues from the page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<League>> findAllLeaguesWithPagination(@PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException {
        Page<League> allLeagues = service.findLeaguesWithPagination(offset, pageSize);
        return ResponseEntity.ok(allLeagues);
    }

    /**
     * get all leagues from the page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/stats/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<LeaguesAndNoTeams>> findAllLeaguesAndNoTeams(@PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException {
        Page<LeaguesAndNoTeams> allLeagues = service.findLeaguesAndNoTeams(offset, pageSize);
        return ResponseEntity.ok(allLeagues);
    }

    /**
     * get a league by id
     * @param leagueID
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{leagueID}")
    public ResponseEntity<League> findLeagueByID(@PathVariable int leagueID) throws EntityNotFoundException{
        return ResponseEntity.ok(service.getLeagueById(leagueID));
    }

    /**
     * update a league
     * @param leagueID
     * @param leagueRequest
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    @PutMapping("/{leagueID}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<League> updateLeague(@PathVariable int leagueID, @RequestBody @Valid LeagueRequest leagueRequest, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        return ResponseEntity.ok(service.updateLeague(leagueID, leagueRequest, authorizationHeader));
    }

    /**
     * delete a league
     * @param leagueID
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{leagueID}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<Void> deleteLeague(@PathVariable int leagueID, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        service.deleteLeague(leagueID, authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    /**
     * get the number of nationalities that support a league
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/number-of-nations-that-supports-league/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<NationalitiesAndLeagues>> getLeaguesByNations(@PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException{
        return ResponseEntity.ok(service.getLeaguesByNations(offset, pageSize));
    }

    /**
     * add a team to a league
     * @param leagueID
     * @param teams
     * @return
     * @throws EntityNotFoundException
     */
    @PostMapping("/{leagueID}/teams")
    public ResponseEntity<League> addTeamListToLeague(@PathVariable int leagueID, @RequestBody List<Integer> teams) throws EntityNotFoundException{
        return new ResponseEntity<>(service.addTeamListToLeague(leagueID, teams), HttpStatus.CREATED);
    }

    /**
     * get a league by name
     * @param league
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/get-league-by-name/{league}")
    public ResponseEntity<League> getLeagueByName(@PathVariable String league) throws EntityNotFoundException {
        return ResponseEntity.ok(service.getLeagueByName(league));
    }
}
