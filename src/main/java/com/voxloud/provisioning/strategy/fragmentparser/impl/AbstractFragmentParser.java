package com.voxloud.provisioning.strategy.fragmentparser.impl;

import com.voxloud.provisioning.strategy.fragmentparser.FragmentParser;
import lombok.Getter;

public abstract class AbstractFragmentParser implements FragmentParser {
    @Getter
    private final String fragment;

    protected AbstractFragmentParser(String fragment) {
        this.fragment = fragment;
    }
}
