package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.dto.PostRequestDto;
import com.sparta.springnewsfeed.dto.PostResponseDto;
import com.sparta.springnewsfeed.entity.Post;
import com.sparta.springnewsfeed.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        Post savePost = postRepository.save(post);

        return new PostResponseDto(savePost);
    }

    public PostResponseDto updateById(Long postId, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);
    }

    public void deleteById(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );
        postRepository.delete(post);
    }

    public PostResponseDto findMyPost() {
        return null;
    }
}
