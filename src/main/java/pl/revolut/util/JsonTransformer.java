package pl.revolut.util;

import spark.ResponseTransformer;

/**
 * The Json http request transformer
 */
public class JsonTransformer implements ResponseTransformer {

    @Override
    public String render(Object o) {
        return JsonHelper.write(o);
    }

}
