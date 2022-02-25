package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Currency;

public class ExchangeService {

    public double lookupExchangeRate(final Currency currency, final Currency localCurrency) {
        return Arrays.stream(Company.values())
                .map(Company::getFee)
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new RuntimeException("Error"));
    }

}
