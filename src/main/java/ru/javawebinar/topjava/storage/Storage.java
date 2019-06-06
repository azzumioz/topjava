package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    void add(Meal meal);

    void update(Meal meal);

    Meal get(String id);

    void delete(String id);

    List<Meal> getAll();

}
