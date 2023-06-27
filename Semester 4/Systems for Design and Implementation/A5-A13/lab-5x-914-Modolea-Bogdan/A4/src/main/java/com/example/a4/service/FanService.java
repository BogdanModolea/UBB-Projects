package com.example.a4.service;

import com.example.a4.dto.*;
import com.example.a4.entity.Fan;
import com.example.a4.entity.FanOfTeam;
import com.example.a4.entity.League;
import com.example.a4.entity.Team;
import com.example.a4.entity.user.UserInfo;
import com.example.a4.exception.EntityNotFoundException;
import com.example.a4.repository.FanOfTeamRepository;
import com.example.a4.repository.FanRepository;
import com.example.a4.repository.TeamRepository;
import com.example.a4.repository.UserInfoRepository;
import com.example.a4.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

/**
 * the fan service implementation
 */
@Service
public class FanService {
    @Autowired
    private FanRepository fanRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * add a new fan
     * @param fanRequest
     * @return
     * @throws EntityNotFoundException
     */
    public Fan saveFan(FanRequest fanRequest) throws EntityNotFoundException {
        Page<Fan> data = findFansWithPagination(0, 1);
        int totalElems = (int) data.getTotalElements();
        Fan lastFan = findFansWithPagination((totalElems - 1), 1).getContent().get(0);

        Optional<UserInfo> userInfo = userInfoRepository.findByName(fanRequest.getUsername());
        System.out.println(userInfo.get());
        System.out.println(fanRequest.getUsername());

        Fan newFan = new Fan();
        newFan.setFid(lastFan.getFid() + 1);
        newFan.setName(fanRequest.getName());
        newFan.setNationality(fanRequest.getNationality());
        newFan.setOccupation(fanRequest.getOccupation());
        newFan.setAge(fanRequest.getAge());
        newFan.setPlaceOfBirth(fanRequest.getPlaceOfBirth());
        newFan.setUser(userInfo.get());

        return fanRepository.save(newFan);
    }

    /**
     * get all fans
     * @return
     * @throws EntityNotFoundException
     */
    public List<FanGetAll> getFans() throws EntityNotFoundException {
        List<Fan> fans = fanRepository.findAll();
        if (fans.isEmpty())
            throw new EntityNotFoundException("No fans found!");

        return ObjectMapper.mapAll(fans, FanGetAll.class);
    }

    /**
     * get all fans on page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    public Page<Fan> findFansWithPagination(int offset, int pageSize) throws EntityNotFoundException {
        Page<Fan> fans = fanRepository.findAll(PageRequest.of(offset, pageSize));
        return fans;
    }

    /**
     * get all fams with their number of teams on page offset with pageSize entities per page
     * @param offset
     * @param pageSize
     * @return
     */
    public Page<FanAndNoTeams> findFanAndNoTeams(int offset, int pageSize) {
        Page<FanAndNoTeams> fanAndNoTeams = fanRepository.findFanAndNoTeams(PageRequest.of(offset, pageSize));
        return fanAndNoTeams;
    }

    /**
     * get the every nationality and its counter
     * @param offset
     * @param pageSize
     * @return
     */
    public Page<NumberNationalities> findNumberNationalities(int offset, int pageSize) {
        Page<NumberNationalities> numberNationalities = fanRepository.findNumberNationalities(PageRequest.of(offset, pageSize));
        return numberNationalities;
    }

    /**
     * get a fan by id
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    public FanByID getFanById(int id) throws EntityNotFoundException {
        Fan fan = fanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No fan found with id %d", id)));

        return new FanByID(
                fan.getFid(),
                fan.getName(),
                fan.getAge(),
                fan.getNationality(),
                fan.getOccupation(),
                fan.getPlaceOfBirth(),
                fan.getSupporter()
        );
    }

    /**
     * update a fan
     * @param id
     * @param fanRequest
     * @param authorizationHeader
     * @return
     * @throws Exception
     */
    public Fan updateFan(int id, FanRequest fanRequest, String authorizationHeader) throws Exception {
        Fan existingFan = fanRepository.findById(id).orElse(null);
        if (existingFan == null)
            throw new EntityNotFoundException(String.format("No fan found with id %d", id));

        String username = existingFan.getUser().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameToCompare = authentication.getName();
        UserInfo user = userInfoRepository.findByName(usernameToCompare).get();
        String role = user.getRoles();

        if ((Objects.equals(username, usernameToCompare) && Objects.equals(role, "ROLE_USER")) || (Objects.equals(role, "ROLE_MODERATOR")) || (Objects.equals(role, "ROLE_ADMIN"))) {
            existingFan.setPlaceOfBirth(fanRequest.getPlaceOfBirth());
            existingFan.setAge(fanRequest.getAge());
            existingFan.setName(fanRequest.getName());
            existingFan.setNationality(fanRequest.getNationality());
            existingFan.setOccupation(fanRequest.getOccupation());

            return fanRepository.save(existingFan);
        }
        throw new Exception("Not allowed to update fan");
    }

    /**
     * delete a fan
     * @param id
     * @param authorizationHeader
     * @throws Exception
     */
    public void deleteFan(int id, String authorizationHeader) throws Exception {
        Fan existingFan = fanRepository.findById(id).orElse(null);
        if (existingFan == null)
            throw new EntityNotFoundException(String.format("No fan found with id %d", id));

        String username = existingFan.getUser().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameToCompare = authentication.getName();
        UserInfo user = userInfoRepository.findByName(usernameToCompare).get();
        String role = user.getRoles();
        if ((Objects.equals(username, usernameToCompare) && Objects.equals(role, "ROLE_USER")) || (Objects.equals(role, "ROLE_MODERATOR")) || (Objects.equals(role, "ROLE_ADMIN"))) {
            fanRepository.delete(existingFan);
        } else
            throw new Exception("Not allowed to delete fan");
    }

    /**
     * delete a team's fan connection
     * @param fanID
     * @param teamID
     * @throws EntityNotFoundException
     */
    public void deleteFanOfTeam(int fanID, int teamID) throws EntityNotFoundException {
        Fan existingFan = fanRepository.findById(fanID).orElse(null);
        if (existingFan == null)
            throw new EntityNotFoundException(String.format("No fan found with id %d", fanID));

        Team existingTeam = teamRepository.findById(teamID).orElse(null);
        if (existingTeam == null)
            throw new EntityNotFoundException(String.format("No team found with id %d", teamID));

        FanOfTeam existingFanOfTeam = existingFan.getSupporter().stream()
                .filter(fanOfTeam -> fanOfTeam.getTeam().getTid() == teamID)
                .filter(fanOfTeam -> fanOfTeam.getFan().getFid() == fanID)
                .findFirst()
                .orElse(null);

        if (existingFanOfTeam == null)
            throw new EntityNotFoundException(String.format("Fan with id %d doesn't support team with id %d", fanID, teamID));

        existingFan.getSupporter().remove(existingFanOfTeam);
        fanRepository.save(existingFan);
    }

    /**
     * filter all fans by age
     * @param age
     * @param offset
     * @param pageSize
     * @return
     * @throws EntityNotFoundException
     */
    public Page<FanGetAll> filterFansByAge(int age, int offset, int pageSize) throws EntityNotFoundException {
        Page<FanGetAll> fans = fanRepository.filterFansByAge(age, PageRequest.of(offset, pageSize));
        if (fans.isEmpty())
            throw new EntityNotFoundException(String.format("No fan with age greater than %d was found!", age));

        return fans;
    }

    /**
     * add a fan to a team
     * @param id
     * @param fan
     * @return
     * @throws EntityNotFoundException
     */
    public Fan addFanToTeam(int id, FanOfTeam fan) throws EntityNotFoundException {
        Fan existingFan = fanRepository.findById(id).orElse(null);
        if (existingFan == null)
            throw new EntityNotFoundException(String.format("No fan found with id %d", id));

        Team existingTeam = teamRepository.findById(fan.getTeam().getTid()).orElse(null);
        if (existingTeam == null)
            throw new EntityNotFoundException(String.format("No team found with id %d", fan.getTeam().getTid()));

        FanOfTeam fanOfTeam = new FanOfTeam();
        fanOfTeam.setFanSince(fan.getFanSince());
        fanOfTeam.setOpinion(fan.getOpinion());
        fanOfTeam.setFan(existingFan);
        fanOfTeam.setTeam(existingTeam);

        existingFan.getSupporter().add(fanOfTeam);
        return fanRepository.save(existingFan);
    }

    /**
     * get every year and the number of fans that appeared
     * @return
     */
    public List<FansAndCounter> findFansAndCounter() {
        List<FansAndCounter> fansAndCounters = fanRepository.getFansAndCounter();
        List<FansAndCounter> ans = new ArrayList<>();
        int cnt = 0;
        int fanCnt = 0;

        double[] X = new double[101];

        for (int i = 2010; i <= 2023; i++) {
            if (fanCnt < fansAndCounters.size()) {
                if (fansAndCounters.get(fanCnt).getYear() == i) {
                    ans.add(fansAndCounters.get(fanCnt));
                    fanCnt++;
                } else {
                    ans.add(new FansAndCounter(i, 0));
                }
            } else {
                ans.add(new FansAndCounter(i, 0));
            }
            cnt++;
        }

        System.out.println(ans);

        return ans;
    }

    /**
     * predict the number of fans for the next 3 years
     * @return
     */
    public List<Integer> getFansAndCounter() {
        List<FansAndCounter> fansAndCounters = fanRepository.getFansAndCounter();

        double[] X = new double[101];
        double[] y = new double[101];
        int cnt = 0;
        int fanCnt = 0;

        for (int i = 2010; i <= 2023; i++) {
            if (fanCnt < fansAndCounters.size()) {
                if (fansAndCounters.get(fanCnt).getYear() == i) {
                    X[cnt] = fansAndCounters.get(fanCnt).getYear();
                    y[cnt] = fansAndCounters.get(fanCnt).getCounter();
                    fanCnt++;
                } else {
                    X[cnt] = i;
                    y[cnt] = 0;
                }
            } else {
                X[cnt] = i;
                y[cnt] = 0;
            }
            System.out.println(X[cnt] + " " + y[cnt] + "\n");
            cnt++;
        }

        double[][] X_train = new double[X.length][1];
        for (int i = 0; i < X.length; i++) {
            X_train[i][0] = X[i];
        }

        org.apache.commons.math3.stat.regression.SimpleRegression lr = new org.apache.commons.math3.stat.regression.SimpleRegression();
        lr.addObservations(X_train, y);

        double predictedFans1 = lr.predict(2024);
        double predictedFans2 = lr.predict(2025);
        double predictedFans3 = lr.predict(2026);

        List<Integer> ans = new ArrayList<>();
        ans.add((int) Math.round(predictedFans1));
        ans.add((int) Math.round(predictedFans2));
        ans.add((int) Math.round(predictedFans3));

        return ans;
    }
}
