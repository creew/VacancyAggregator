package com.eklompus.view;

import com.eklompus.Controller;
import com.eklompus.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class HtmlView implements View{
    private Controller controller;
    private final String filePath =  "./src/main/java/" + this.getClass().getPackage().getName().replace('.', '/') + "/vacancies.html";;

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            updateFile(getUpdatedFileContent(vacancies));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(Paths.get(filePath).toFile(), "UTF-8");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        try {
            Document doc = getDocument();
            Element templ = doc.getElementsByClass("template").first().clone();
            templ.removeAttr("style");
            templ.removeClass("template");
            doc.getElementsByClass("vacancy").forEach(p -> {
                if (p.tagName().equals("tr")) {
                    if (!(p.hasClass("template")))
                        p.remove();
                }
            });
            vacancies.forEach(p -> {
                Element el = templ.clone();
                el.getElementsByClass("city").first().text(p.getCity());
                el.getElementsByClass("companyName").first().text(p.getCompanyName());
                el.getElementsByClass("salary").first().text(p.getSalary());
                el.select("a[href]").attr("href", p.getUrl()).first().text(p.getTitle());
                Elements before = doc.select("tr.vacancy.template");
                before.before(el.outerHtml());
            });
            return doc.html();
        } catch (Exception e) {
            e.printStackTrace();
            return "Some exception occured";
        }
    }

    private void updateFile(String file) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(file);
        fw.close();
    }

}
