package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CommentUpdateRequestDto;
import com.bilgeadam.dto.request.CreateCommentRequestDto;
import com.bilgeadam.dto.request.DeleteCommentRequestDto;
import com.bilgeadam.dto.request.UpdatePointRequestDto;
import com.bilgeadam.entity.Base;
import com.bilgeadam.entity.Comment;
import com.bilgeadam.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT)
public class CommentController {
    private final CommentService commentService;

    @PostMapping(CREATE)
    public ResponseEntity<Comment> createComment(@RequestBody @Valid CreateCommentRequestDto dto){
        return ResponseEntity.ok(commentService.createComment(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Comment> updateComment(@RequestBody @Valid CommentUpdateRequestDto dto){
        return ResponseEntity.ok(commentService.updateComment(dto));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<Boolean> deleteComment(@RequestBody DeleteCommentRequestDto dto){
        return ResponseEntity.ok(commentService.deleteComment(dto));
    }
}
