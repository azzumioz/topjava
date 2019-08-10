package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User admin = service.getWithMeals(ADMIN_ID);
        UserTestData.assertMatch(admin, UserTestData.ADMIN);
        assertMatch(admin.getMeals(), ADMIN_MEAL2, ADMIN_MEAL1);
//        List<Meal> expected = List.of(ADMIN_MEAL2, ADMIN_MEAL1);
//        assertMatch(actual, expected);
    }
}
