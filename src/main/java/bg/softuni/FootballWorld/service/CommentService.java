package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.CommentCreateDTO;
import bg.softuni.FootballWorld.model.entity.CommentEntity;
import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.view.CommentView;
import bg.softuni.FootballWorld.repository.CommentRepository;
import bg.softuni.FootballWorld.repository.PlayerRepository;
import bg.softuni.FootballWorld.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, PlayerRepository playerRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public CommentView createComment(CommentCreateDTO commentCreateDTO) {

        CommentEntity comment = this.modelMapper.map(commentCreateDTO, CommentEntity.class);

        comment.setApproved(true);
        comment.setCreated(LocalDateTime.now());
        comment.setPlayer(this.playerRepository.findById(commentCreateDTO.getPlayer()).get());
        comment.setAuthor(this.userRepository.findByEmail(commentCreateDTO.getUsername()).get());

        this.commentRepository.save(comment);

        CommentView view = this.modelMapper.map(comment, CommentView.class);

        view.setUsername(comment.getAuthor().getUsername());

        return view;
    }


    public List<CommentView> getAllComments(Long playerId) {

        Optional<PlayerEntity> player = this.playerRepository.findById(playerId);

        List<CommentEntity> comments = this.commentRepository.findAllByPlayer(player.get());

        return comments.stream().map(c -> {
            CommentView map = this.modelMapper.map(c, CommentView.class);
            map.setUsername(c.getAuthor().getUsername());
            return map;
        }).collect(Collectors.toList());
    }
}
