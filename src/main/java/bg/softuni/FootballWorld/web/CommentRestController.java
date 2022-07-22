package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.dto.CommentCreateDTO;
import bg.softuni.FootballWorld.model.view.CommentView;
import bg.softuni.FootballWorld.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping(value = "/{playerId}/comments")
    public ResponseEntity<CommentView> createComment(@PathVariable("playerId") Long playerId,
                                                     @AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody @Valid CommentCreateDTO commentCreateDTO) {

        CommentView comment = commentService.createComment(commentCreateDTO, userDetails);

        return ResponseEntity
                .created(URI.create(String.format("/api/%d/comments/%d", playerId, comment.getId())))
                .body(comment);
    }
}
