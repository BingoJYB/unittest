package com.bingo.di;

import javax.inject.Inject;

public class Service {

    @Inject
    String s;

    @Inject
    public Service(String s) {
        this.s = s;
    }
}
