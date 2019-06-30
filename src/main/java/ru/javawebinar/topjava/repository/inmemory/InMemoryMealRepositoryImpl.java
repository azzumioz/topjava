package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, InMemoryMealBaseRepository> repository = new ConcurrentHashMap<>();

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
        return repository.get(userId).getAll().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFilterByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.get(userId).getAll().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

}

