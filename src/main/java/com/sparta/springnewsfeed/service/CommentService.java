package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.entity.Comment;
import com.sparta.springnewsfeed.entity.Post;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.exception.InvalidCredentialsException;
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
    public CommentSaveResponseDto saveComment(Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("없는 게시물 입니다."));
        Comment newComment = new Comment(commentSaveRequestDto.getContent(), post);
        Comment savedComment = commentRepository.save(newComment);
        return new CommentSaveResponseDto(savedComment.getId(), savedComment.getContent());
    }
    @Transactional
    public CommentUpdateResponseDto updateComment(AuthUser authUser, Long commentId, CommentUpdateRequstDto commentUpdateRequstDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("없는 댓글입니다."));

        if(!comment.getUser().equals(authUser.getId())){
            throw new InvalidCredentialsException("권한이 없습니다.");
        }
        comment.update(commentUpdateRequstDto.getContent());
        return new CommentUpdateResponseDto(authUser, comment.getId(), comment.getContent());
    }
    @Transactional
    public void deleteComment(AuthUser authUser, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("없는 댓글 입니다."));
        User user = comment.getUser();
        if (!user.getId().equals(authUser.getId())){
            throw new InvalidCredentialsException("권한이 없습니다.");
        }

        if(!commentRepository.existsById(commentId)){
            throw new NullPointerException("없는 댓글입니다.");
        }
        commentRepository.deleteById(commentId);
    }
}
