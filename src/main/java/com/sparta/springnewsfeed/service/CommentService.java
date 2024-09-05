package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.entity.Comment;
import com.sparta.springnewsfeed.entity.Post;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.exception.ErrorCode;
import com.sparta.springnewsfeed.exception.custom.InvalidCredentialsException;
import com.sparta.springnewsfeed.exception.custom.NoEntityException;
import com.sparta.springnewsfeed.repository.CommentRepository;
import com.sparta.springnewsfeed.repository.PostRepository;
import com.sparta.springnewsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentSaveResponseDto saveComment(AuthUser authUser, Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoEntityException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new NoEntityException(ErrorCode.USER_NOT_FOUND));
        Comment newComment = new Comment(commentSaveRequestDto.getContent(), post, user);
        Comment savedComment = commentRepository.save(newComment);
        return new CommentSaveResponseDto(authUser, savedComment.getId(), savedComment.getContent());
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(AuthUser authUser, Long commentId, CommentUpdateRequstDto commentUpdateRequstDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoEntityException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(authUser.getId())) {
            throw new InvalidCredentialsException(ErrorCode.USER_NOT_MATCH);
        }
        comment.update(commentUpdateRequstDto.getContent());
        return new CommentUpdateResponseDto(authUser, comment.getId(), comment.getContent());
    }

    @Transactional
    public void deleteComment(AuthUser authUser, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoEntityException(ErrorCode.COMMENT_NOT_FOUND));
        User user = comment.getUser();
        if (!user.getId().equals(authUser.getId())) {
            throw new InvalidCredentialsException(ErrorCode.USER_NOT_MATCH);
        }

        if (!commentRepository.existsById(commentId)) {
            throw new NoEntityException(ErrorCode.COMMENT_NOT_FOUND);
        }
        commentRepository.deleteById(commentId);
    }
}
