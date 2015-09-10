package ebuero.aatasiei.tracker.services.impl;

import ebuero.aatasiei.tracker.model.entities.Bug;
import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.model.enums.BugPriority;
import ebuero.aatasiei.tracker.model.enums.BugStatus;
import ebuero.aatasiei.tracker.repos.BugRepository;
import ebuero.aatasiei.tracker.services.impl.helpers.AbstractIssueService;
import ebuero.aatasiei.tracker.services.interfaces.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNonNull;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@Service
public class BugServiceImpl extends AbstractIssueService<Bug, BugRepository> implements BugService {

    @Autowired
    BugServiceImpl(BugRepository repository) {
        super(repository);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Bug createBug(String title, String description, Developer assignedDeveloper, BugPriority priority, BugStatus status) {

        checkArgumentNonNull(title, "Title can not be null!");
        checkArgumentNonNull(status, "Status can not be null!");
        checkArgumentNonNull(priority, "Priority can not be null!");

        Bug bug = new Bug(title);

        populateIssue(bug, description, assignedDeveloper);

        bug.setPriority(priority);
        bug.setStatus(status);

        return repository.save(bug);
    }
}
