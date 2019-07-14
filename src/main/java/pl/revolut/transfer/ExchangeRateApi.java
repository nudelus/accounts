package pl.revolut.transfer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExchangeRateApi {

    private static final String FORINT = "HUF";
    private static final String EURO = "EUR";
    private static final String ZLOTY = "PLN";

    private static final Map<CurrencyPair,Double> exchangeRate = new HashMap<>();

    static  {
        exchangeRate.put(new CurrencyPair(FORINT,EURO),0.003D);
        exchangeRate.put(new CurrencyPair(EURO,FORINT),322.9260D);
        exchangeRate.put(new CurrencyPair(ZLOTY,EURO),0.2325D);
        exchangeRate.put(new CurrencyPair(EURO,ZLOTY),4.2534D);
        exchangeRate.put(new CurrencyPair(FORINT,ZLOTY),0.0129D);
        exchangeRate.put(new CurrencyPair(ZLOTY,FORINT),75.5095D);
    }

    public static Double getExchangeRate(String sourceCurrency, String targetCurrency) {
        Double exchangeRateValue = exchangeRate.get(new CurrencyPair(sourceCurrency,targetCurrency));

        if(exchangeRateValue == null) {
            throw new IllegalArgumentException("No currency rate for given currencies");
        }

        return exchangeRateValue;
    }


    private static class CurrencyPair {

        private String source;
        private String target;

        public CurrencyPair(String source, String target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CurrencyPair that = (CurrencyPair) o;
            return Objects.equals(source, that.source) &&
                    Objects.equals(target, that.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, target);
        }
    }

}
