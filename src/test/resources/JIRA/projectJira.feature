Feature: Jira Validation

  Scenario Outline: Creating and Validating Board via RestAssured API
    When the user goes to JiraBoard localhost
    Then the user user sets prerequisite for the request and response
    And the user creates JiraBoard with "<name>", "<type>" and <filterId>
    Then the user validates the JiraBoard's "<name>", "<type>"
    Examples:
      | name       | type  | filterId |
      | TestBoard2 | scrum | 10000    |

  Scenario Outline: Creating and Validating Sprints via RestAssured API
    When the user goes to JiraSprint localhost
    Then the user user sets prerequisite for the request and response
    And the user creates Sprint with "<name>", "<startDate>", "<endDate>", boardId
    Then the user validates the Sprint's "<name>", "<startDate>", "<endDate>", boardId
    Examples:
      | name          | startDate  | endDate    |
      | TestSprintAbc | 2020-07-08 | 2020-08-08 |
      | TestSprintwww | 2020-09-08 | 2020-10-08 |
      | TestSprintrrr | 2020-11-08 | 2020-12-08 |

  Scenario Outline: Creating and Validating Issue via RestAssured API
    When the user goes to JiraIssue localhost
    Then the user user sets prerequisite for the request and response
    And the user creates issue with "<summary>", "<description>" and "<issueType>"
    Then the user executes Get request
    * the user validates the issue's "<summary>", "<description>" and "<issueType>"
    Examples:
      | summary               | description           | issueType |
      | Creating test Bug1    | Test Bug 1 in Jira    | Bug       |
      | Creating test Bug2    | Test Bug 2 in Jira    | Bug       |
      | Creating test Story3  | Test Story 3 in Jira  | Story     |
      | Creating test Bug4    | Test Bug 4 in Jira    | Bug       |
      | Creating test Story5  | Test Story 5 in Jira  | Story     |
      | Creating test Story5  | Test Story 5 in Jira  | Story     |
      | Creating test bug6    | Test bug 6 in Jira    | Bug       |
      | Creating test Story7  | Test Story 7 in Jira  | Story     |
      | Creating test story8  | Test Story 8 in Jira  | Story     |
      | Creating test Story9  | Test story 9 in Jira  | Story     |
      | Creating test bug10   | Test bugie 10 in Jira | Bug       |
      | Creating test Story11 | Test Story 11 in Jira | Story     |
      | Creating test bug12   | Test bug 12 in Jira   | Bug       |
      | Creating test Story13 | Test Story 13 in Jira | Story     |
      | Creating test Story14 | Test Story 14 in Jira | Story     |
      | Creating test bug15   | Test bugy 15 in Jira  | Bug       |
      | Creating test Story16 | Test Story 16 in Jira | Story     |
      | Creating test bug17   | Test bb 17 in Jira    | Bug       |
      | Creating test Story18 | Test Story 18 in Jira | Story     |
      | Creating test bug19   | Test b 19 in Jira     | Bug       |
      | Creating test Story20 | Test Story 20 in Jira | Story     |
      | Creating test Story21 | Test Story 21 in Jira | Story     |
      | Creating test bug22   | Test bug 22 in Jira   | Bug       |
      | Creating test Story23 | Test Story 23 in Jira | Story     |
      | Creating test bugy24  | Test BUg 24 in Jira   | Bug       |
      | Creating test Story25 | Test Story 25 in Jira | Story     |
      | Creating test bug26   | Test bug 26 in Jira   | Bug       |
      | Creating test Story27 | Test Story 27 in Jira | Story     |
      | Creating test bug28   | Test bug 28 in Jira   | Bug       |
      | Creating test Story29 | Test Story 29 in Jira | Story     |
      | Creating test bug30   | Test bug 30 in Jira   | Bug       |

  Scenario: Replace Issue to Sprint
    When the user moves Issue to Sprint
    Then the user validates Issue is moved to Sprint