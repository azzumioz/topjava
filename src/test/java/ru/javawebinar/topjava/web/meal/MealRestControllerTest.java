package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.*;
import static ru.javawebinar.topjava.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;


class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    protected MealService mealService;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1, Meal.class));
    }

    @Test
    void testDelete() throws Exception {
        List<Meal> expected = List.of(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(authUserId()), expected);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEALS_TO, MealTo.class));
    }

    @Test
    void testCreate() throws Exception {
        Meal expected = new Meal(of(2019, Month.MAY, 30, 10, 0), "newMeal", 500);
        ResultActions actions = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Meal returned = readFromJson(actions, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(authUserId()), expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("new_description");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    void testGetBetween2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(REST_URL + "filter2")
                .param("startDateTime", "2015-05-31T09:00:00")
                .param("endDateTime", "2015-05-31T14:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<MealTo> returned = readListFromJsonMvcResult(mvcResult, MealTo.class);
        assertMatch(returned, MealsUtil.createWithExcess(MEAL5, true), MealsUtil.createWithExcess(MEAL4, true));
    }

    @Test
    void testGetBetween() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", "2015-05-31")
                .param("startTime", "09:00")
                .param("endDate", "2015-05-31")
                .param("endTime", "14:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<MealTo> returned = readListFromJsonMvcResult(mvcResult, MealTo.class);
        assertMatch(returned, MealsUtil.createWithExcess(MEAL5, true), MealsUtil.createWithExcess(MEAL4, true));
    }

}