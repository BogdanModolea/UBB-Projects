package com.example.a2.service;

import com.example.a2.entity.League;
import com.example.a2.entity.Team;
import com.example.a2.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {
    @Autowired
    private LeagueRepository repository;

    public League saveLeague(League league) {
        return repository.save(league);
    }

    public List<League> saveLeagues(List<League> leagues) {
        return repository.saveAll(leagues);
    }

    public List<League> getWithoutNullLeagues() {
        return repository.findAll();
    }

    public List<League> getLeagues() {
        List<League> leagues = repository.findAll();

        for(League league : leagues)
            league.setTeams(null);

        return leagues;
    }

    public League getLeagueById(int id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteLeague(int id) {
        repository.deleteById(id);
        return "League " + id + " deleted.\n";
    }

    public League updateLeague(League league) {
        League current = repository.findById(league.getLid()).orElse(null);

        current.setAbbreviation(league.getAbbreviation());
        current.setAudience(league.getAudience());
        current.setTeams(league.getTeams());
        current.setRegion(league.getRegion());
        current.setYear(league.getYear());
        current.setBestPlayer(league.getBestPlayer());

        return repository.save(current);
    }
}
