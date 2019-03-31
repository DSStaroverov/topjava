package ru.javawebinar.topjava.web.meal;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {


    @GetMapping()
    public String getAll(Model model){
        model.addAttribute("meals", super.getAll());
        return "meals";
    }


    @GetMapping("/create")
    public String create(Model model){
        Meal meal = new Meal();
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model){
        Meal meal = super.get(getId(request));
        model.addAttribute("meal",meal);
        return "mealForm";
    }

    @PostMapping()
    public String createOrUpdate(HttpServletRequest request){
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal,getId(request));
        }

        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, Model model){
        int userId = SecurityUtil.authUserId();
        service.delete(getId(request),userId);
        return getAll(model);
    }



    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
