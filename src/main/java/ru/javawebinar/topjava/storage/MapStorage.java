package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapStorage implements Storage {
    private Map<Integer, Meal> map = new ConcurrentHashMap<>();
    private int id = 0;

    @Override
    public Meal add(Meal meal) {
        meal.setId(id);
        map.put(id, meal);
        synchronized (this) {
            id++;
        }
        return meal;
    }

    @Override
    public void update(int id, Meal meal) {
        meal.setId(id);
        map.put(id, meal);
    }

    @Override
    public Meal get(int id) {
        return map.get(id);
    }

    @Override
    public void delete(int id) {
        map.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return getMeals();
    }

    private List<Meal> getMeals() {
        return new ArrayList<>(map.values());
    }

}
