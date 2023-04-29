package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtDictionary;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.http4j.Http4J;
import org.huyemt.http4j.HttpResponse;
import org.huyemt.http4j.resource.Headers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Huyemt
 */

public class BtNGetFunction extends BtFunction {

    public BtNGetFunction(BulletRuntime runtime) {
        super("nget", runtime);
    }

    @Override
    public Object invokeFV(Object... args) throws BulletException {

        /*
        nget(url(string), headers(dict), cookies(dict), data(dict), allowRediect(boolean))
         */

        try {
            if (args.length > 5) {
                throw new BulletException("Parameter is out of the specified range");
            }

            if (args.length < 1) {
                throw new BulletException("Missing parameter");
            }

            if (!(args[0] instanceof String)) {
                throw new BulletException("The url must be of string type");
            }

            String url = args[0].toString();
            BtDictionary result = new BtDictionary();
            HttpResponse response;

            if (args.length == 1) {
                response = Http4J.get(url);
                result.vector.put("url", response.url);
                result.vector.put("content", response.html);
                result.vector.put("headers", BtDictionary.parse(response.headers.getHeaders()));
                result.vector.put("cookies", BtDictionary.parse(response.cookies.getCookieMap()));
                result.vector.put("status_code", new BigDecimal(response.status_code));

                return result;
            } else if (args.length == 2) {
                if (!(args[1] instanceof BtDictionary)) {
                    throw new BulletException("The headers must be of string type");
                }
            }
        } catch (IOException e) {
            throw new BulletException(e.getMessage());
        }
    }
}
