package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.MealService;

public class MealRestController extends AbstractMealController {

    @Autowired
    public MealRestController(MealService service) {
        super(service);
    }
}
