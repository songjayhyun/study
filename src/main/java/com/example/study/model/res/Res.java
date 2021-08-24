package com.example.study.model.res;

import com.example.study.model.entity.Test;
import lombok.Getter;

@Getter
public class Res {
    private final Long Id;
    private final String name;

    public Res(final Test test) {
        Id = test.getId();
        this.name = test.getName();
    }
}
