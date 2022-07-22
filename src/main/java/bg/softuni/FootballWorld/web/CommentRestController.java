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
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/{playerId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentView> createComment(@PathVariable("playerId") Long playerId,
                                                     @AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody CommentCreateDTO commentCreateDTO) {

        commentCreateDTO.setPlayer(playerId);
        commentCreateDTO.setUsername(userDetails.getUsername());

        CommentView comment = commentService.createComment(commentCreateDTO);

        return ResponseEntity
                .created(URI.create(String.format("/api/%d/comments/%d", playerId, comment.getId())))
                .body(comment);
    }

    @GetMapping("/{playerId}/comments")
    public ResponseEntity<List<CommentView>> getComments(@PathVariable("playerId") Long playerId) {
        return ResponseEntity.ok(commentService.getAllComments(playerId));
    }
}
