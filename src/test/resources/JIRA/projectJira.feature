Feature:Create a Story

  Background: Login Function



  @FUrkan
  Scenario Outline: Create jira Story
    Given The user enter  "<Summary>", "<Description>" and "<name>"
    When The user goes to the LocalHost8080
    When The user click last created one
    Then click viewWorkflow
    And validate information
    Examples:
      | Summary                   | Description              | name  |
      | Creating a story from API | Story created throuh API | Story |
      | Login Functionality       | Checking Functions       | Story |
      | Validation Products       | Validate exists prodocts | Story |
      | Regression Testing        | Regression               | Story |
      | Smoke Testing             | Smoke                    | Story |