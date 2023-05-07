package org.huyemt.crypto4j.digest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Huyemt
 */

public class URL {
    public URL() {

    }

    public final String encode(String content, boolean mark) throws URISyntaxException {
        return mark ? URLEncoder.encode(content, StandardCharsets.UTF_8).replace("+", "%20") : new URI(null, null, content, null).toASCIIString();
    }

    public final String decode(String content) {
        return URLDecoder.decode(content, StandardCharsets.UTF_8);
    }

}
