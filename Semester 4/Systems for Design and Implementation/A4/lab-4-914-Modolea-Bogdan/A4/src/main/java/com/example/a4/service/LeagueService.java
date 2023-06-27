package com.example.a4.service;

import com.example.a4.dto.NationalitiesAndLeagues;
import com.example.a4.entity.Fan;
import com.example.a4.entity.FanOfTeam;
import com.example.a4.entity.League;
import com.example.a4.entity.Team;
import com.example.a4.repository.FanOfTeamRepository;
import com.example.a4.repository.FanRepository;
import com.example.a4.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeagueService {
    @Autowired
    private LeagueRepository repository;
    @Autowired
    private FanOfTeamRepository fanOfTeamRepository;
    @Autowired
    private FanRepository fanRepository;

    public League saveLeague(League league) {
        return repository.save(league);
    }

    public List<League> saveLeagues(List<League> leagues) {
        return repository.saveAll(leagues);
    }

    public List<League> getLeaguesWithoutNull() {
        return repository.findAll();
    }

    public List<League> getLeagues() {
        return repository.findAll();
    }

    public List<League> getLeaguesTask() {
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

    public List<NationalitiesAndLeagues> getTaskReport(){
        List<League> leagues = repository.findAll();
        List<FanOfTeam> fansOfTeams = fanOfTeamRepository.findAll();
        List<Fan> fans = fanRepository.findAll();
        List<NationalitiesAndLeagues> ans = new ArrayList<>();
        HashMap<League, Set<Fan>> hm = new HashMap<>();
        HashMap<League, Set<String>> leagueNationality = new HashMap<>();


        for(Fan fan : fans){
            for(FanOfTeam fanOfTeam : fansOfTeams){
                if(fan == fanOfTeam.getFan()){
                    for(League league : leagues){
                        if(league.getTeams().contains(fanOfTeam.getTeam())){
                            if(hm.get(league) == null){
                                hm.put(league, new HashSet<>());
                                hm.get(league).add(fan);
                            }
                            else{
                                hm.get(league).add(fan);
                            }
                        }
                    }
                }
            }
        }

        for(Map.Entry<League, Set<Fan>> entry : hm.entrySet()){
            League league = entry.getKey();
            Set<Fan> leagueFans = entry.getValue();
            for(Fan leagueFan : leagueFans){
                if(leagueNationality.get(league) == null) {
                    leagueNationality.put(league, new HashSet<>());
                    leagueNationality.get(league).add(leagueFan.getNationality());
                }
                else
                    leagueNationality.get(league).add(leagueFan.getNationality());
            }
        }

        for(Map.Entry<League, Set<String>> entry : leagueNationality.entrySet())
            ans.add(new NationalitiesAndLeagues(entry.getKey().getAbbreviation(), entry.getValue().size()));

        return ans;
    }
}
