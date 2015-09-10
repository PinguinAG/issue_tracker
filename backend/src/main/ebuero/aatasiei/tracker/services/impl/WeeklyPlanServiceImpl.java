package ebuero.aatasiei.tracker.services.impl;

import com.google.common.collect.Lists;
import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;
import ebuero.aatasiei.tracker.repos.WeeklyPlanRepository;
import ebuero.aatasiei.tracker.services.interfaces.WeeklyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@Service
public class WeeklyPlanServiceImpl implements WeeklyPlanService {

    private final WeeklyPlanRepository repository;

    @Autowired
    WeeklyPlanServiceImpl(WeeklyPlanRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<WeeklyPlan> getAllWeeklyPlans() {
        return Lists.newArrayList(repository.findAllByOrderByIndexAsc());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void clearWeeklyPlans() {
        repository.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public WeeklyPlan createWeeklyPlan(int index) {
        return repository.save(new WeeklyPlan(index));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public WeeklyPlan update(WeeklyPlan weeklyPlan) {
        requireNonNull(weeklyPlan);
        return repository.save(weeklyPlan);
    }
}
