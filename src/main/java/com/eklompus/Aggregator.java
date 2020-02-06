package com.eklompus;

import com.eklompus.model.HHStrategy;
import com.eklompus.model.Model;
import com.eklompus.model.Provider;
import com.eklompus.view.HtmlView;
import com.eklompus.view.View;

public class Aggregator {
    public static void main(String[] args) {
        View view = new HtmlView();
        Model model = new Model(view, new Provider(new HHStrategy()));
        Controller controller = new Controller(model);
        controller.onCitySelect("Москва");
    }
}
