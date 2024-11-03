package com.voxloud.provisioning.strategy.fragmentparser.impl;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.UnknownDeviceModelException;
import com.voxloud.provisioning.strategy.fragmentparser.FragmentParser;
import com.voxloud.provisioning.strategy.fragmentparser.FragmentParsingStrategy;
import com.voxloud.provisioning.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * class {@code FragmentParsingStrategyImpl} provides {@code FragmentParser}
 * for the specified device type.
 */
@Component
@Log4j2
public class FragmentParsingStrategyImpl implements FragmentParsingStrategy {

    /**
     * creates {@code FragmentParser} for the specified device type.
     *
     * @param device device to which parser will be created.
     * @return parser for the specified device.
     */
    @Override
    public FragmentParser getParser(Device device) {
        switch (device.getModel()) {
            case CONFERENCE:
                return getJsonFragmentParser(device.getOverrideFragment());
            case DESK:
                return getPropertyFragmentParser(device.getOverrideFragment());
            default:
                log.error(Utils.UNKNOWN_DEVICE_MODEL);
                throw new UnknownDeviceModelException(Utils.UNKNOWN_DEVICE_MODEL);
        }
    }

    private PropertyFragmentParser getPropertyFragmentParser(String fragment) {
        return PropertyFragmentParser.of(fragment);
    }

    private JsonFragmentParser getJsonFragmentParser(String fragment) {
        return JsonFragmentParser.of(fragment);
    }
}
