package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
//        List<UserMealWithExceed> mealExceededList = new ArrayList<>();
//        Map<LocalDate, Integer> caloriesDay = new HashMap<>();
//
//        for (UserMeal meal : mealList) {
//            int calDay = caloriesDay.containsKey(meal.getDate()) ? caloriesDay.get(meal.getDate()) + meal.getCalories() : meal.getCalories();
//            caloriesDay.put(meal.getDate(), calDay);
//        }
//        for (UserMeal meal : mealList) {
//            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
//                mealExceededList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesDay.get(meal.getDate()) > caloriesPerDay));
//            }
//        }
        Map<LocalDate, Integer> caloriesDay = mealList.stream().collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> (new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesDay.get(meal.getDate()) > caloriesPerDay)))
                .collect(Collectors.toList());
    }
}
