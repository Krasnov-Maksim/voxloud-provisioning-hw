package com.voxloud.provisioning.util;

public class TestUtils {
    public static final String EOL = System.lineSeparator();
    public static final String SPACE2 = "  ";
    public static final String EXPECTED_DESK_WITHOUT_FRAGMENT =
            "username=john" + EOL
                    + "password=doe" + EOL
                    + "domain=sip.voxloud.com" + EOL
                    + "port=5060" + EOL
                    + "codecs=G711,G729,OPUS";

    public static final String EXPECTED_DESK_WITH_FRAGMENT =
            "username=walter" + EOL
                    + "password=white" + EOL
                    + "domain=sip.anotherdomain.com" + EOL
                    + "port=5161" + EOL
                    + "codecs=G711,G729,OPUS" + EOL
                    + "timeout=10";

    public static final String EXPECTED_CONFERENCE_WITHOUT_FRAGMENT =
            "{" + EOL
                    + SPACE2 + "\"username\" : \"sofia\"," + EOL
                    + SPACE2 + "\"password\" : \"red\"," + EOL
                    + SPACE2 + "\"domain\" : \"sip.voxloud.com\"," + EOL
                    + SPACE2 + "\"port\" : \"5060\"," + EOL
                    + SPACE2 + "\"codecs\" : [\"G711\",\"G729\",\"OPUS\"]" + EOL
                    + "}";

    public static final String EXPECTED_CONFERENCE_WITH_FRAGMENT =
            "{" + EOL
                    + SPACE2 + "\"username\" : \"eric\"," + EOL
                    + SPACE2 + "\"password\" : \"blue\"," + EOL
                    + SPACE2 + "\"domain\" : \"sip.anotherdomain.com\"," + EOL
                    + SPACE2 + "\"port\" : \"5161\"," + EOL
                    + SPACE2 + "\"codecs\" : [\"G711\",\"G729\",\"OPUS\"]," + EOL
                    + SPACE2 + "\"timeout\" : 10" + EOL
                    + "}";
    public static final String MAC_ADDRESS_NOT_VALID = "aa-wrong-mac-ff";
    public static final String MAC_ADDRESS_DESK_WITHOUT_FRAGMENT = "aa-bb-cc-dd-ee-ff";
    public static final String MAC_ADDRESS_DESK_WITH_FRAGMENT = "a1-b2-c3-d4-e5-f6";
    public static final String MAC_ADDRESS_CONFERENCE_WITHOUT_FRAGMENT = "f1-e2-d3-c4-b5-a6";
    public static final String MAC_ADDRESS_CONFERENCE_WITH_FRAGMENT = "1a-2b-3c-4d-5e-6f";
    public static final String DESK_OVERRIDE_FRAGMENT =
            "domain=sip.anotherdomain.com\\nport=5161\\ntimeout=10";
}
