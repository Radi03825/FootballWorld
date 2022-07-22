package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.CommentCreateDTO;
import bg.softuni.FootballWorld.model.entity.CommentEntity;
import bg.softuni.FootballWorld.model.view.CommentView;
import bg.softuni.FootballWorld.repository.CommentRepository;
import bg.softuni.FootballWorld.repository.PlayerRepository;
import bg.softuni.FootballWorld.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public CommentView createComment(CommentCreateDTO commentCreateDTO, UserDetails userDetails) {

        CommentEntity comment = this.modelMapper.map(commentCreateDTO, CommentEntity.class);

        comment.setApproved(true);
        comment.setCreated(LocalDateTime.now());
        comment.setPlayer(this.playerRepository.findById(commentCreateDTO.getPlayerId()).get());
        comment.setAuthor(this.userRepository.findByUsername(userDetails.getUsername()).get());

        this.commentRepository.save(comment);

        CommentView view = this.modelMapper.map(comment, CommentView.class);

        view.setAuthorUsername(comment.getAuthor().getUsername());

        return view;
    }


}
