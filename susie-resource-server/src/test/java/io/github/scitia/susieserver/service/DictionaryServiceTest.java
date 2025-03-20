package io.github.scitia.susieserver.service;

import io.github.scitia.susieserver.config.TestConfiguration;
import io.github.scitia.susieserver.dictionary.domain.IssuePriority;
import io.github.scitia.susieserver.dictionary.domain.IssueStatus;
import io.github.scitia.susieserver.dictionary.domain.IssueType;
import io.github.scitia.susieserver.dictionary.repository.IssuePriorityRepository;
import io.github.scitia.susieserver.dictionary.repository.IssueStatusRepository;
import io.github.scitia.susieserver.dictionary.repository.IssueTypeRepository;
import io.github.scitia.susieserver.dictionary.service.issue.priority.IssuePriorityService;
import io.github.scitia.susieserver.dictionary.service.issue.status.IssueStatusService;
import io.github.scitia.susieserver.dictionary.service.issue.type.IssueTypeService;
import io.github.scitia.susieserver.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static io.github.scitia.susieserver.builder.UserBuilder.createCurrentLoggedInUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest(classes = TestConfiguration.class)
@Transactional
public class DictionaryServiceTest {

    @Autowired
    private IssueTypeRepository issueTypeRepository;

    @Autowired
    private IssueStatusRepository issueStatusRepository;

    @Autowired
    private IssuePriorityRepository issuePriorityRepository;

    @Autowired
    private IssueTypeService issueTypeService;

    @Autowired
    private IssueStatusService issueStatusService;

    @Autowired
    private IssuePriorityService issuePriorityService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        Mockito.when(userService.getCurrentLoggedUser()).thenReturn(createCurrentLoggedInUser());
    }

    @Test
    public void getAllIssueTypesTest() {

        //given
        long expectedTypesAmount = issueTypeRepository.count();

        //when
        List<IssueType> allTypes = issueTypeService.getAllIssueTypes();
        int actualTypesAmount = allTypes.size();

        //then
        assertEquals(expectedTypesAmount, actualTypesAmount);
        assertIterableEquals(issueTypeRepository.findAll(), allTypes);
    }

    @Test
    public void getAllIssueStatusesTest() {

        //given
        long expectedStatusesAmount = issueStatusRepository.count();

        //when
        List<IssueStatus> allStatuses = issueStatusService.getAllIssueStatuses();
        int actualStatusesAmount = allStatuses.size();

        //then
        assertEquals(expectedStatusesAmount, actualStatusesAmount);
        assertIterableEquals(issueStatusRepository.findAll(), allStatuses);
    }

    @Test
    public void getAllIssuePrioritiesTest() {

        //given
        long expectedPrioritiesAmount = issuePriorityRepository.count();

        //when
        List<IssuePriority> allPriorities = issuePriorityService.getAllIssuePriorities();
        int actualPrioritiesAmount = allPriorities.size();

        //then
        assertEquals(expectedPrioritiesAmount, actualPrioritiesAmount);
        assertIterableEquals(issuePriorityRepository.findAll(), allPriorities);
    }
}
