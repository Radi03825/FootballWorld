package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.GameDTO;
import bg.softuni.FootballWorld.model.dto.GameResultDTO;
import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.SkillsEntity;
import bg.softuni.FootballWorld.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Random RANDOM = new Random();

    private final PlayerRepository playerRepository;

    public GameService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerEntity> loadOpponents() {
        long playersCount = this.playerRepository.count();
        long size = 3;

        if (playersCount < 3) {
            size = playersCount;
        }

        Set<Long> randomIds = pickRandom(playersCount, size);

        return randomIds.stream().map(id -> this.playerRepository.findById(Long.valueOf(id)).get()).collect(Collectors.toList());
    }

    public GameResultDTO play(GameDTO gameDTO) {
        PlayerEntity myPlayer = this.playerRepository.findById(gameDTO.getFirstPlayer()).get();
        PlayerEntity opponent = this.playerRepository.findById(gameDTO.getSecondPlayer()).get();

        SkillsEntity myPlayerSkills = myPlayer.getSkills();
        SkillsEntity opponentSkills = opponent.getSkills();

        int myPlayerPointsCount = 0;
        int opponentPointsCount = 0;

        if (myPlayerSkills.getPace() > opponentSkills.getPace()) {
            myPlayerPointsCount++;
        } else if (myPlayerSkills.getPace() < opponentSkills.getPace()) {
            opponentPointsCount++;
        }

        if (myPlayerSkills.getShooting() > opponentSkills.getShooting()) {
            myPlayerPointsCount++;
        } else if (myPlayerSkills.getShooting() < opponentSkills.getShooting()) {
            opponentPointsCount++;
        }

        if (myPlayerSkills.getPassing() > opponentSkills.getPassing()) {
            myPlayerPointsCount++;
        } else if (myPlayerSkills.getPassing() < opponentSkills.getPassing()) {
            opponentPointsCount++;
        }

        if (myPlayerSkills.getDefending() > opponentSkills.getDefending()) {
            myPlayerPointsCount++;
        } else if (myPlayerSkills.getDefending() < opponentSkills.getDefending()) {
            opponentPointsCount++;
        }

        GameResultDTO gameResultDTO = new GameResultDTO();

        if (myPlayerPointsCount > opponentPointsCount) {
            gameResultDTO.setWinner(gameDTO.getFirstPlayer());
        } else if (myPlayerPointsCount < opponentPointsCount) {
            gameResultDTO.setWinner(gameDTO.getSecondPlayer());
        }

        gameResultDTO.setMyPlayer(gameDTO.getFirstPlayer());
        gameResultDTO.setMyPlayerPoints(myPlayerPointsCount);
        gameResultDTO.setOpponentPoints(opponentPointsCount);
        gameResultDTO.setMyPlayerFullName(myPlayer.getFirstName() + " " + myPlayer.getLastName());
        gameResultDTO.setOpponentFullName(opponent.getFirstName() + " " + opponent.getLastName());
        gameResultDTO.setMyPlayerImageUrl(myPlayer.getImage().getUrl());
        gameResultDTO.setOpponentImageUrl(opponent.getImage().getUrl());

        return gameResultDTO;
    }



    private Set<Long> pickRandom(Long playerCount, Long size) {
        final Set<Long> picked = new HashSet<>();
        while (picked.size() < size) {
            picked.add(RANDOM.nextLong(playerCount) + 1);
        }
        return picked;
    }
}