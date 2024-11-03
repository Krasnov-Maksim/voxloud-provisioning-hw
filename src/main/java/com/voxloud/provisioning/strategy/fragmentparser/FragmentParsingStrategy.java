package com.voxloud.provisioning.strategy.fragmentparser;

import com.voxloud.provisioning.entity.Device;

/**
 * provides {@code FragmentParser} for device.
 */
public interface FragmentParsingStrategy {
    FragmentParser getParser(Device device);
}
