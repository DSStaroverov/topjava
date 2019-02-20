package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll(getAuthUserId());
    }

    public Meal save(Meal meal){
        return service.save(meal,getAuthUserId());
    }

    public void delete(int id){
        service.delete(id,getAuthUserId());
    }

    public Meal get(int id){
        return service.get(id,getAuthUserId());
    }

    public Collection<MealTo> getBetween(LocalDate fromDate, LocalTime fromTime, LocalDate toDate, LocalTime toTime){
        Collection<MealTo> mealToList=MealsUtil.getFilteredWithExcess(service.getAll(getAuthUserId()),MealsUtil.DEFAULT_CALORIES_PER_DAY,fromTime,toTime);
        return MealsUtil.getFilteredWithDate(mealToList,fromDate,toDate);
    }
}