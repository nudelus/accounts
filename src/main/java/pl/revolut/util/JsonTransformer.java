package pl.revolut.util;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    @Override
    public String render(Object o) {
        return JsonHelper.write(o);
    }

}
