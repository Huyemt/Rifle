package org.bullet.base.bulitIn.function.net;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtDictionary;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.http4j.Http4J;
import org.huyemt.http4j.HttpResponse;
import org.huyemt.http4j.resource.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpCookie;
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
            if (args.length > 6) {
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
            Headers headers = new Headers();
            Cookies cookies = new Cookies();
            RequestBody requestBody = new RequestBody();
            Params params = new Params();
            HttpConfig config = new HttpConfig();

            if (args.length > 1) {
                if (!(args[1] instanceof BtDictionary)) {
                    throw new BulletException("The params must be of dictionary type");
                }

                params.setAll(((BtDictionary) args[1]).toMap());

                if (args.length > 2) {
                    if (!(args[2] instanceof BtDictionary)) {
                        throw new BulletException("The headers must be of dictionary type");
                    }

                    headers.setAll(((BtDictionary) args[2]).toMap());

                    if (args.length > 3) {
                        if (!(args[3] instanceof BtDictionary)) {
                            throw new BulletException("The cookies must be of dictionary type");
                        }

                        for (Map.Entry<String, Object> entry : ((BtDictionary) args[3]).toMap().entrySet()) {
                            cookies.add(HttpCookie.parse(entry.getKey().concat("=").concat(entry.getValue().toString())).get(0));
                        }

                        if (args.length > 4) {
                            if (!(args[4] instanceof BtDictionary || args[4] instanceof String)) {
                                throw new BulletException("The requestBody must be of dictionary type or string type");
                            }

                            if (args[4] instanceof String) {
                                requestBody.json = args[4].toString();
                            } else {
                                requestBody.setAll(((BtDictionary) args[4]).toMap());
                            }

                            if (args.length > 5) {
                                if (!(args[5] instanceof Boolean)) {
                                    throw new BulletException("The allowsRedirect must be of boolean type");
                                }

                                config.allowsRedirect((boolean) args[5]);
                            }
                        }
                    }
                }
            }

            response = Http4J.get(url, params, headers, cookies, requestBody, config);

            result.vector.put("url", response.url);
            result.vector.put("content", response.html);
            result.vector.put("headers", BtDictionary.parse(response.headers.getHeaders()));
            result.vector.put("cookies", BtDictionary.parse(response.cookies.getCookieMap()));
            result.vector.put("status_code", new BigDecimal(response.status_code));

            return result;
        } catch (IOException e) {
            throw new BulletException(e.getMessage());
        }
    }
}
