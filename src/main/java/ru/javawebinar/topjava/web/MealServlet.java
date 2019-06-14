package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Storage storage;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public void init() {
        storage = new MapStorage();
        storage.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        storage.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        storage.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        storage.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        storage.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idString = request.getParameter("id");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("date"), formatter), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        if (idString.equals("-1")) {
            storage.add(meal);
        } else {
            storage.update(Integer.parseInt(idString), meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        int id;
        Meal meal;
        switch (action) {
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                meal = storage.get(id);
                request.setAttribute("meal", meal);
                break;
            case "add":
                meal = new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 0);
                meal.setId(-1);
                request.setAttribute("meal", meal);
                break;
            default:
                List<MealTo> mealsWithExcess = MealsUtil.getFilteredWithExcess(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("listMeals", mealsWithExcess);
                request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
                return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

}
