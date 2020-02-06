package com.eklompus.model;

import com.eklompus.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private final static String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    protected Document getDocument(String searchString, int page) {
        try {
            return Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                    .referrer("")
                    .get();

        } catch (IOException ignored) {}
        return null;
    }

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        Document doc;
        Elements divs;
        List<Vacancy> lst = new ArrayList<>();
        int page = 0;
        do {
            doc =getDocument(searchString, page++);
            if (doc == null)
                break;
            divs = doc.select("[data-qa=vacancy-serp__vacancy]");
            for (Element elem : divs) {
                Vacancy vacancy = new Vacancy();
                vacancy.setTitle(elem.select("[data-qa=vacancy-serp__vacancy-title]").stream()
                        .map(Element::text).findFirst().orElse(""));
                vacancy.setSalary(elem.select("[data-qa=vacancy-serp__vacancy-compensation]").stream()
                        .map(Element::text).findFirst().orElse(""));
                vacancy.setCity(elem.select("[data-qa=vacancy-serp__vacancy-address]").stream()
                        .map(Element::text).findFirst().orElse(""));
                vacancy.setCompanyName(elem.select("[data-qa=vacancy-serp__vacancy-employer]").stream()
                        .map(Element::text).findFirst().orElse(""));
                vacancy.setSiteName(String.format(URL_FORMAT, searchString, page - 1));
                vacancy.setUrl(elem.select("[data-qa=vacancy-serp__vacancy-title]").stream()
                        .map(p -> p.attr("href")).findFirst().orElse(""));
                lst.add(vacancy);
            }
        } while (divs.size() > 0);
        return lst;
    }
}
