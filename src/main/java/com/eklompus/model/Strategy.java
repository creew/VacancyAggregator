package com.eklompus.model;

import com.eklompus.vo.Vacancy;

import java.util.List;

public interface Strategy {
    List<Vacancy> getVacancies(String searchString);
}
