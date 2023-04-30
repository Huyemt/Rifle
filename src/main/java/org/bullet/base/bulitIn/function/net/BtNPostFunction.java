package org.bullet.base.bulitIn.function.net;

import org.bullet.base.components.BtBulitInFunction;
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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class BtNPostFunction extends BtBulitInFunction {

    public BtNPostFunction(BulletRuntime runtime) {
        super("npost", runtime);
        args.put("url", null);
        args.put("params", new BtDictionary());
        args.put("headers", new BtDictionary());
        args.put("cookies", new BtDictionary());
        args.put("data", new BtDictionary());
        args.put("allow_rediect", true);
        args.put("timeout", 20000);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object urlV = args.get("url");
        Object paramsV = args.get("params");
        Object headersV = args.get("headers");
        Object cookiesV = args.get("cookies");
        Object dataV = args.get("data");
        Object allowRediectV = args.get("allow_rediect");
        Object timeoutV = args.get("timeout");

        if (!(urlV instanceof String)) {
            throw new BulletException("Request address must be a string");
        }

        if (!(paramsV instanceof BtDictionary)) {
            throw new BulletException("Params must be a dictionary");
        }

        if (!(headersV instanceof BtDictionary)) {
            throw new BulletException("Headers must be a dictionary");
        }

        if (!(cookiesV instanceof BtDictionary)) {
            throw new BulletException("Cookies must be a dictionary");
        }

        if (!(dataV instanceof BtDictionary || dataV instanceof String)) {
            throw new BulletException("Request body must be a dictionary or a string");
        }

        if (!(allowRediectV instanceof Boolean)) {
            throw new BulletException("Allow rediect must be boolean");
        }

        if (!(timeoutV instanceof BigDecimal)) {
            throw new BulletException("Timeout must be a number");
        }

        BtDictionary result = new BtDictionary();
        HttpResponse response;

        String url = urlV.toString();
        Params params = new Params();
        Headers headers = new Headers();
        Cookies cookies = new Cookies();
        RequestBody requestBody = new RequestBody();
        HttpConfig config = new HttpConfig();

        config.allowsRedirect((boolean) allowRediectV);
        config.timeout(((BigDecimal) timeoutV).intValueExact());

        params.setAll(((BtDictionary) paramsV).toMap());

        headers.setAll(((BtDictionary) headersV).toMap());

        for (Map.Entry<String, Object> entry : ((BtDictionary) cookiesV).toMap().entrySet()) {
            cookies.add(HttpCookie.parse(entry.getKey().concat("=").concat(entry.getValue().toString())).get(0));
        }

        if (dataV instanceof String) {
            requestBody.json = dataV.toString();
        } else {
            requestBody.setAll(((BtDictionary) dataV).toMap());
        }

        try {
            response = Http4J.post(url, params, headers, cookies, requestBody, config);
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
