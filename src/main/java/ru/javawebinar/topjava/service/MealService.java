package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealService {
    Collection<Meal> getAll(int userId);
    Meal save(Meal meal,int userId);
    void delete(int id,int userId);
    Meal get(int id,int userId);
}