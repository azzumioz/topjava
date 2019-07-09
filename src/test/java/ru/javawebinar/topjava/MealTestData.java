package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID1 = 0;
    public static final int MEAL_ID2 = 1;
    public static final int MEAL_NOT_FOUND = START_SEQ;

    public static final Meal MEALADMIN1 = new Meal(MEAL_ID1, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal MEALADMIN2 = new Meal(MEAL_ID2, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }

}
