package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserStorageTest extends UserServiceTest {


    @Test
    public void getWithMeals() {
        List<Meal> meals2 = USER.getMeals();
        List<Meal> meals1  = service.getWithMeals(USER_ID).getMeals();

//        User user = service.getWithMeals(UserTestData.ADMIN_ID);
//        List<Meal> meals1 = service.getWithMeals(UserTestData.ADMIN_ID).getMeals();
//        List<Meal> meals2 = List.of(ADMIN_MEAL2, ADMIN_MEAL1);
        assertMatch(meals1, meals2);
    }
}
