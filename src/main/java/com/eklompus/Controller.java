package com.eklompus;

import com.eklompus.model.Model;

public class Controller {
    Model model;

    public Controller(Model model) {
        if (model == null)
            throw new IllegalArgumentException();
        this.model = model;
    }

    public void onCitySelect(String cityName) {
        model.selectCity(cityName);
    }
}
