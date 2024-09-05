package com.sparta.springnewsfeed.dto;

import com.sparta.springnewsfeed.entity.Post;
import lombok.Getter;

@Getter
public class PostThumbnailResponseDto {
    private Long id;
    private String title;
    private String content;

    public PostThumbnailResponseDto(Post post) {
        this.id = post.getId();
        this.title = summary(post.getTitle(),20);
        this.content = summary(post.getContent(),65);
    }

    private String summary(String before, int num) {
        StringBuilder stringBuilder = new StringBuilder();
        if (before.length() > num) {
            String after = before.substring(0, num);
            stringBuilder.append(after).append(" ... ");
            return stringBuilder.toString();
        } else {
            return before;
        }
    }
}