package com.eklompus.view;

import com.eklompus.Controller;
import com.eklompus.vo.Vacancy;

import java.util.List;

public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
