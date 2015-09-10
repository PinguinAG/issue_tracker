package ebuero.aatasiei.tracker.services.impl;

import com.google.common.collect.Lists;
import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.repos.DeveloperRepository;
import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
import ebuero.aatasiei.tracker.services.interfaces.ProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNonNull;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
@Service
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository repository;

    // FIXME: remove circular dependency
    @Autowired
    private ProjectPlanService projectPlanService;

    @Autowired
    DeveloperServiceImpl(DeveloperRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Developer> getAllDevelopers() {
        return Lists.newArrayList(repository.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Developer createDeveloper(String name) {
        checkArgumentNonNull(name, "Name can not be null!");

        Developer savedDeveloper = repository.save(new Developer(name));

        projectPlanService.recomputeProjectPlan();

        return savedDeveloper;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<Developer> getDeveloper(Long id) {
        checkArgumentNonNull(id, "Id can not be null!");

        return repository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteDeveloper(Developer developer) {
        checkDeveloperWasPersisted(developer);

        repository.delete(developer);

        projectPlanService.recomputeProjectPlan();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDeveloperCount() {
        return repository.count();
    }

    private void checkDeveloperWasPersisted(Developer developer) {
        final boolean invalidArgument = Objects.nonNull(developer) && Objects.nonNull(developer.getId());
        checkArgument(invalidArgument, String.format("Invalid argument: %s", String.valueOf(developer)));
    }
}
