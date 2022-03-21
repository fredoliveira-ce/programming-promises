package domain;

import java.util.Arrays;
import java.util.Comparator;

public class ExchangeService {

    public double lookupExchangeRate(final Currency currency) {
        return Arrays.stream(Company.values())
                .filter(company -> company.getCurrency().equals(currency))
                .map(Company::getFee)
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new RuntimeException("Error"));
    }

}
