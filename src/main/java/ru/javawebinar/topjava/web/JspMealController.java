package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @GetMapping(params = {"action=delete", "id"})
    public String delete(@RequestParam("id") int id, Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("meals - delete meal by id {} for user {}", id, userId);
        service.delete(id, userId);
        model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping(params = {"action=filter"})
    public String filter(HttpServletRequest request, Model model) {
        log.info("Meal filter");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", MealsUtil.getFilteredWithExcess(service.getBetweenDates(startDate, endDate, SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @GetMapping()
    public String meals(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("Meal getAll for user ", userId);
        model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping(params = {"action=create"})
    public String create(Model model) {
        log.info("Meal create new");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @GetMapping(params = {"action=update", "id"})
    public String update(@RequestParam("id") int id, Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("Meal id = {} update fo user {} ", id, userId);
        model.addAttribute("meal", service.get(id, userId));
        return "mealForm";
    }

    @PostMapping()
    public String createAndUpdate(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        log.info("Meal createAndUpdate for user ", userId);
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String strId = request.getParameter("id");
        if (StringUtils.isEmpty(strId)) {
            service.create(meal, userId);
        } else {
            meal.setId(Integer.parseInt(strId));
            service.update(meal, userId);
        }
        return "redirect:/meals";
    }

}
