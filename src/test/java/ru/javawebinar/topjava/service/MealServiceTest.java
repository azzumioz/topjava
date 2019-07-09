package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal mealAdmin = service.get(MEAL_ID1, ADMIN_ID);
        assertMatch(mealAdmin, MEALADMIN1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL_NOT_FOUND, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID1, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), MEALADMIN2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(MEAL_NOT_FOUND, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        service.getBetweenDates(LocalDate.of(2015, Month.JANUARY, 1), LocalDate.of(2015, Month.DECEMBER, 1), ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> mealsBetween = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.JANUARY, 1, 14, 0), LocalDateTime.of(2015, Month.DECEMBER, 1, 14, 0), ADMIN_ID);
        mealsBetween.sort(Comparator.comparing(Meal::getDateTime));
        assertMatch(mealsBetween, MEALADMIN1, MEALADMIN2);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        all.sort(Comparator.comparing(Meal::getDateTime));
        assertMatch(all, MEALADMIN1, MEALADMIN2);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEALADMIN2);
        updated.setDescription("new Meal");
        updated.setCalories(300);
        updated.setDateTime(LocalDateTime.of(2018, Month.JANUARY, 1, 10, 0));
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(MEAL_ID2, ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = new Meal(MEALADMIN2);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID2, ADMIN_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 10, 0), "newMeal", 500);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), MEALADMIN2, MEALADMIN1, newMeal);
    }

}