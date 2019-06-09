package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    Meal add(Meal meal);

    void update(int id, Meal meal);

    Meal get(int id);

    void delete(int id);

    List<Meal> getAll();

}
