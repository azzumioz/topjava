package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserStorageTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        List<Meal> meals1 = service.getWithMeals(UserTestData.ADMIN_ID).getMeals();
        List<Meal> meals2 = List.of(ADMIN_MEAL2, ADMIN_MEAL1);
        assertMatch(meals1, meals2);
    }
}
