package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListStorage implements Storage {
    private List<Meal> list = new ArrayList<>();

    @Override
    public void add(Meal meal) {
        meal.setId(UUID.randomUUID().toString());
        list.add(meal);
    }

    @Override
    public void update(Meal meal) {
        list.set(getSeachKey(meal.getId()), meal);
    }

    @Override
    public Meal get(String id) {
        return list.get(getSeachKey(id));
    }

    @Override
    public void delete(String id) {
        list.remove(getSeachKey(id));
    }

    @Override
    public List<Meal> getAll() {
        return list;
    }

    private int getSeachKey(String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
