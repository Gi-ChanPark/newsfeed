package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.dto.CommentSaveRequestDto;
import com.sparta.springnewsfeed.dto.CommentSaveResponseDto;
import com.sparta.springnewsfeed.entity.Comment;
import com.sparta.springnewsfeed.entity.Post;
import com.sparta.springnewsfeed.repository.CommentRepository;
import com.sparta.springnewsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentSaveResponseDto saveComment(Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("없는 게시물 입니다."));
        Comment newComment = new Comment(commentSaveRequestDto.getContent(), post);
        Comment savedComment = commentRepository.save(newComment);
        return new CommentSaveResponseDto(savedComment.getId(), savedComment.getContent());
    }
}
