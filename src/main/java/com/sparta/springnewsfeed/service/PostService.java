package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.FriendStatus;
import com.sparta.springnewsfeed.dto.PostRequestDto;
import com.sparta.springnewsfeed.dto.PostResponseDto;
import com.sparta.springnewsfeed.dto.PostThumbnailResponseDto;
import com.sparta.springnewsfeed.entity.Post;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.exception.InvalidCredentialsException;
import com.sparta.springnewsfeed.repository.FriendRepository;
import com.sparta.springnewsfeed.repository.PostRepository;
import com.sparta.springnewsfeed.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, FriendRepository friendRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    public PostResponseDto createPost(PostRequestDto requestDto, Long userId) {
        Post post = new Post(requestDto);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("삭제된 유저 입니다.")
        );
        post.setUser(user);
        Post savePost = postRepository.save(post);
        user.getPosts().add(savePost);
        userRepository.save(user);
        return new PostResponseDto(savePost);
    }

    public PostResponseDto updateById(Long postId, PostRequestDto requestDto, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );
        if (!post.getUser().getId().equals(userId)) {
            throw new InvalidCredentialsException("권한이 없습니다.");
        }
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);
    }

    public void deleteById(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("해당 게시물이 존재하지 않습니다.")
        );
        User user = post.getUser();
        if (user.getId().equals(userId)) {
            user.getPosts().remove(post);
            postRepository.delete(post);
        } else {
            throw new InvalidCredentialsException("권한이 없습니다.");
        }
    }

    public List<PostThumbnailResponseDto> findMyPosts(Long userId) {
        List<Post> myPosts = postRepository.findByUserId(userId).orElseThrow(
                () -> new NullPointerException("작성한 게시물이 없습니다.")
        );
        List<PostThumbnailResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : myPosts) {
            PostThumbnailResponseDto postResponseDto = new PostThumbnailResponseDto(post);
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }

    public Page<PostThumbnailResponseDto> getNewsfeed(Long userId, int page) {
        List<Long> friendIds = friendRepository.findFriends(String.valueOf(FriendStatus.ACCEPTED), userId);

        Pageable pageable = PageRequest.of(page, 10);

        Page<Post> newfeeds = postRepository.findPostsByIds(friendIds, pageable);
        return newfeeds.map(PostThumbnailResponseDto::new);
    }
}
