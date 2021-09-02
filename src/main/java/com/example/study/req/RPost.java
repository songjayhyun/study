package com.example.study.req;

import com.example.study.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RPost {

    @Builder
    @Getter
    public static class ListGetRes {
        private Long id;
        private String title;
        private GetUserRes user;
    }

    @Builder
    @Getter
    public static class GetUserRes {
        private Long id;
        private String name;
    }

    @Builder
    @Getter
    public static class GetRes {
        private Long id;
        private String title;
        private String content;
        private GetUserRes user;
    }

    @Getter
    public static class CreationReq {
        @NotNull // null 방지
        @NotBlank // black + null까지 방지
        @Pattern(regexp = "^[가-힣\\s]+$") // 원하는 문자 or 숫자만 입력하게끔
        private String title;
        @NotBlank
        private String content;
        @JsonIgnore // json사용 시 user필드는 무시해라 // user는 null
        @Setter
        private User user;
    }

    @Getter
    public static class ModificationReq {
        @JsonIgnore
        @Setter
        private Long id;
        private String title;
        private String content;
        @JsonIgnore
        @Setter
        private User user;
    }
}