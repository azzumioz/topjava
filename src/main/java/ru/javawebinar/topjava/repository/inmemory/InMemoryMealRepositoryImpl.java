package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, InMemoryMealBaseRepository> repository = new ConcurrentHashMap<>();

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        InMemoryMealBaseRepository meals = repository.computeIfAbsent(userId, uid -> new InMemoryMealBaseRepository());
        return meals.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryMealBaseRepository meals = repository.get(userId);
        return meals != null && meals.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        InMemoryMealBaseRepository meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getFiltered(userId, meal -> true);
    }

    @Override
    public Collection<Meal> getAllFilterByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    private Collection<Meal> getFiltered(int userId, Predicate<Meal> filter) {
        InMemoryMealBaseRepository meals = repository.get(userId);
        return meals == null ? Collections.emptyList() :  meals.getAll().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

}

