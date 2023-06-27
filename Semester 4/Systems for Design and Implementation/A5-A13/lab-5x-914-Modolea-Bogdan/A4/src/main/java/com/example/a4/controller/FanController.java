package com.example.a4.controller;

import com.example.a4.dto.*;
import com.example.a4.entity.Fan;
import com.example.a4.entity.FanOfTeam;
import com.example.a4.exception.EntityNotFoundException;
import com.example.a4.service.FanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/fans")
public class FanController {
    @Autowired
    private /*final*/ FanService service;

//    @Autowired
//    public FanController(FanService fanService){
//        service = fanService;
//    }

    /**
     * add a new fan
     * @param fanRequest
     * @return
     * @throws EntityNotFoundException
     */
    @PostMapping
    public ResponseEntity<Fan> addFan(@RequestBody @Valid FanRequest fanRequest) throws EntityNotFoundException {
        return new ResponseEntity<>(service.saveFan(fanRequest), HttpStatus.CREATED);
    }

    /**
     * add a fan to a team
     * @param fanID
     * @param fanOfTeam
     * @return
     * @throws EntityNotFoundException
     */
    @PostMapping("/{fanID}/teams")
    public ResponseEntity<Fan> addFanTeam(@PathVariable int fanID, @RequestBody FanOfTeam fanOfTeam) throws EntityNotFoundException {
        return new ResponseEntity<>(service.addFanToTeam(fanID, fanOfTeam), HttpStatus.CREATED);
    }

    /**
     * get all fans
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping
    public ResponseEntity<List<FanGetAll>> findAllFans() throws EntityNotFoundException {
        return ResponseEntity.ok(service.getFans());
    }

    /**
     * get all fans from the page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<Fan>> findAllFansWithPagination(@PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException {
        Page<Fan> allFans = service.findFansWithPagination(offset, pageSize);
        return ResponseEntity.ok(allFans);
    }

    /**
     * get all fans from the page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/stats/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<FanAndNoTeams>> findFanAndNoTeam(@PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException {
        Page<FanAndNoTeams> allFans = service.findFanAndNoTeams(offset, pageSize);
        return ResponseEntity.ok(allFans);
    }

    /**
     * get a fan by id
     * @param fanID
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{fanID}")
    public ResponseEntity<FanByID> findFanByID(@PathVariable int fanID) throws EntityNotFoundException {
        return ResponseEntity.ok(service.getFanById(fanID));
    }

    /**
     * update a fan
     * @param fanID
     * @param fanRequest
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    @PutMapping("/{fanID}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<Fan> updateFan(@PathVariable int fanID, @RequestBody @Valid FanRequest fanRequest, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        return ResponseEntity.ok(service.updateFan(fanID, fanRequest, authorizationHeader));
    }

    /**
     * delete a fan
     * @param fanID
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{fanID}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<Void> deleteFan(@PathVariable int fanID, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        service.deleteFan(fanID, authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    /**
     * delete a team's fan connection
     * @param fanID
     * @param teamID
     * @return
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/{fanID}/teams/{teamID}")
    public ResponseEntity<Void> deleteFanTeam(@PathVariable int fanID, @PathVariable int teamID) throws EntityNotFoundException {
        service.deleteFanOfTeam(fanID, teamID);
        return ResponseEntity.noContent().build();
    }

    /**
     * filter fans with age greater than a value
     * @param age
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/filter/{age}/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<FanGetAll>> findByAgeGreaterThan(@PathVariable int age, @PathVariable int offset, @PathVariable int pageSize) throws EntityNotFoundException {
        return ResponseEntity.ok(service.filterFansByAge(age, offset, pageSize));
    }

    /**
     * get the number of each nationality
     * @param offset
     * @param pageSize
     * @return
     */
    @GetMapping("/numberNations/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<NumberNationalities>> findNumberNationalities(@PathVariable int offset, @PathVariable int pageSize) {
        return ResponseEntity.ok(service.findNumberNationalities(offset, pageSize));
    }

    /**
     * predict the number of fans per year
     * @return
     */
    @GetMapping("/get-fans-counter")
    public ResponseEntity<List<FansAndCounter>> findFansWithCounter() {
        return ResponseEntity.ok(service.findFansAndCounter());
    }

    /**
     * predict the number of fans for the next 3 years
     * @return
     */
    @GetMapping("/predict-fans")
    public ResponseEntity<List<Integer>> findPredictions() {
        return ResponseEntity.ok(service.getFansAndCounter());
    }
}
