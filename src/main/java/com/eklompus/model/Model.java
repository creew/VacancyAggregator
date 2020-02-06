package com.eklompus.model;

import com.eklompus.view.View;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Model {
    private View view;
    private Provider[] providers;

    public Model(View view, Provider... providers) {
        this.view = view;
        this.providers = providers;
        if (view ==null || providers == null || providers.length < 1)
            throw new IllegalArgumentException();
    }

    public void selectCity(String city) {
        view.update(Arrays.stream(providers).flatMap(p -> p.getJavaVacancies(city).stream()).collect(Collectors.toList()));
    }
}
