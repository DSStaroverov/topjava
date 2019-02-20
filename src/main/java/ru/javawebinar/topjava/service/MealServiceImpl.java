package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public Meal save(Meal meal,int userId) {
        return repository.save(meal,userId);
    }

    @Override
    public void delete(int id,int userId) {
        repository.delete(id,userId);
    }

    @Override
    public Meal get(int id,int userId) {
        return repository.get(id,userId);
    }
}