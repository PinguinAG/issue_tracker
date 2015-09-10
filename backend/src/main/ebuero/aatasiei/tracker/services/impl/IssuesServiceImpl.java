package ebuero.aatasiei.tracker.services.impl;

import com.google.common.collect.Lists;
import ebuero.aatasiei.tracker.model.entities.Issue;
import ebuero.aatasiei.tracker.repos.IssuesRepository;
import ebuero.aatasiei.tracker.services.interfaces.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@Service
public class IssuesServiceImpl implements IssuesService {

    private final IssuesRepository repository;

    @Autowired
    IssuesServiceImpl(IssuesRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Issue> getAllOrderedByCreationDate() {
        return Lists.newArrayList(repository.findAllByOrderByCreationDateAsc());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Issue> getAllOpenOrderedByCreationDate() {
        return Lists.newArrayList(repository.findAllOpenIssuesOrderByCreationDateAsc());
    }
}
