package com.example.study.req;

import com.example.study.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
        private String title;
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