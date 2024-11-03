package com.voxloud.provisioning.strategy.fragmentparser;

/**
 * The {@code FragmentParser} parses configuration fragment.
 */

public interface FragmentParser {
    <T> T parseFragment();
}
