package com.bilgeadam.service;

import com.bilgeadam.dto.request.CommentUpdateRequestDto;
import com.bilgeadam.dto.request.CreateCommentRequestDto;
import com.bilgeadam.dto.request.DeleteCommentRequestDto;
import com.bilgeadam.dto.response.UserForCommentServiceResponseDto;
import com.bilgeadam.entity.Point;
import com.bilgeadam.exception.CommentManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.ICommentMapper;
import com.bilgeadam.repository.ICommentRepository;
import com.bilgeadam.entity.Comment;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends ServiceManager<Comment,String> {
    private final ICommentRepository commentRepository;
    private final PointService pointService;

    public CommentService(ICommentRepository commentRepository, PointService pointService) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.pointService = pointService;
    }
    public Comment createComment(CreateCommentRequestDto dto){
        UserForCommentServiceResponseDto user = pointService.tokenToUserprofileWithControls(dto.getToken());
        Comment comment = ICommentMapper.INSTANCE.toComment(dto);
        comment.setUsername(user.getUsername());
        comment.setUserprofileId(user.getId());
        comment.setCommentDate(LocalDate.now().toString());
        return save(comment);
    }
    public Comment updateComment(CommentUpdateRequestDto dto){
        UserForCommentServiceResponseDto user = pointService.tokenToUserprofileWithControls(dto.getToken());
        Optional<Comment> comment = commentRepository.findById(dto.getId());
        if (comment.isEmpty()) {
            throw new CommentManagerException(ErrorType.COMMENT_NOT_FOUND);
        }
        if (comment.get().getRecipeId().equals(dto.getRecipeId()) && comment.get().getUserprofileId().equals(user.getId())) {
            return update(ICommentMapper.INSTANCE.toComment(dto,comment.get()));
        }
        throw new CommentManagerException(ErrorType.AUTHORIZATION_ERROR);
    }
    public Boolean deleteComment(DeleteCommentRequestDto dto){
        UserForCommentServiceResponseDto user = pointService.tokenToUserprofileWithControls(dto.getToken());
        Optional<Comment> comment = commentRepository.findById(dto.getId());
        if (comment.isEmpty()) {
            throw new CommentManagerException(ErrorType.COMMENT_NOT_FOUND);
        }
        if (comment.get().getUserprofileId().equals(user.getId())) {
            delete(comment.get());
            return true;
        }
        throw new CommentManagerException(ErrorType.AUTHORIZATION_ERROR);
    }
    /**
     * this method will be activated after front end ask the list or average of
     * comments
     * @param recipeId --> which recipe you need
     * @return
     */
    // todo after project end change that to send infos to recipe as pointIds
    public List<Comment> getRecipesComments(String recipeId){
        return commentRepository.findAllByRecipeId(recipeId);
    }

}
