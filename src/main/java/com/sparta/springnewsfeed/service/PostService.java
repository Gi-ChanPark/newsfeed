package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.FriendStatus;
import com.sparta.springnewsfeed.dto.post.request.PostRequestDto;
import com.sparta.springnewsfeed.dto.post.response.PostResponseDto;
import com.sparta.springnewsfeed.dto.post.response.PostThumbnailResponseDto;
import com.sparta.springnewsfeed.entity.Post;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.exception.ErrorCode;
import com.sparta.springnewsfeed.exception.custom.InvalidCredentialsException;
import com.sparta.springnewsfeed.exception.custom.NoEntityException;
import com.sparta.springnewsfeed.repository.FriendRepository;
import com.sparta.springnewsfeed.repository.PostRepository;
import com.sparta.springnewsfeed.repository.UserRepository;
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
                () -> new NoEntityException(ErrorCode.POST_NOT_FOUND)
        );
        return new PostResponseDto(post);
    }

    public PostResponseDto createPost(PostRequestDto requestDto, Long userId) {
        Post post = new Post(requestDto);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoEntityException(ErrorCode.USER_NOT_FOUND)
        );
        post.setUser(user);
        Post savePost = postRepository.save(post);
        user.getPosts().add(savePost);
        userRepository.save(user);
        return new PostResponseDto(savePost);
    }

    public PostResponseDto updateById(Long postId, PostRequestDto requestDto, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoEntityException(ErrorCode.POST_NOT_FOUND)
        );
        if (!post.getUser().getId().equals(userId)) {
            throw new InvalidCredentialsException(ErrorCode.USER_NOT_MATCH);
        }
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);
    }

    public void deleteById(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoEntityException(ErrorCode.POST_NOT_FOUND)
        );
        User user = post.getUser();
        if (user.getId().equals(userId)) {
            user.getPosts().remove(post);
            postRepository.delete(post);
        } else {
            throw new InvalidCredentialsException(ErrorCode.USER_NOT_MATCH);
        }
    }

    public List<PostThumbnailResponseDto> findMyPosts(Long userId) {
        List<Post> myPosts = postRepository.findByUserId(userId);
        if(myPosts.isEmpty()){
            throw new NoEntityException(ErrorCode.POST_NOT_FOUND);
        }
        List<PostThumbnailResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : myPosts) {
            PostThumbnailResponseDto postResponseDto = new PostThumbnailResponseDto(post);
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }


    public Page<PostThumbnailResponseDto> getNewsfeed(Long userId, int page) {
        List<User> friends = friendRepository.findFriends(FriendStatus.ACCEPTED, userId);
        Pageable pageable = PageRequest.of(page, 10);
        if(friends.isEmpty()){
            Page<Post> newsfeeds = postRepository.findAllByUserIdNotOrderByUpdatedAtDesc(userId,pageable);
            return newsfeeds.map(PostThumbnailResponseDto::new);
        }

        Page<Post> newsfeeds = postRepository.findPostsByUsers(friends, pageable);
        return newsfeeds.map(PostThumbnailResponseDto::new);
    }
}
