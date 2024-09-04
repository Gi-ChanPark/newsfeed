package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.exception.InvalidCredentialsException;
import com.sparta.springnewsfeed.dto.PostRequestDto;
import com.sparta.springnewsfeed.dto.PostResponseDto;
import com.sparta.springnewsfeed.entity.Post;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.repository.PostRepository;
import com.sparta.springnewsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    public PostResponseDto createPost(PostRequestDto requestDto, Long id) {
        Post post = new Post(requestDto);
        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerException("삭제된 유저 입니다.")
        );
        post.setUser(user);
        Post savePost = postRepository.save(post);
        user.getPosts().add(savePost);
        userRepository.save(user);
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

    public void deleteById(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );
        if (post.getUser().getId().equals(id)) {
            postRepository.delete(post);
        } else {
            throw new InvalidCredentialsException("권한이 없습니다.");
        }
    }

    public List<PostResponseDto> findMyPost(Long userid) {
        List<Post> myPosts = postRepository.findByUserId(userid).orElseThrow(
                () -> new NullPointerException("작성한 게시물이 없습니다.")
        );
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : myPosts) {
            PostResponseDto postResponseDto = new PostResponseDto(post);
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }
}
