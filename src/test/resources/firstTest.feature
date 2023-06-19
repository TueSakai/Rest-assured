@critical
Feature: First test
  Cucumber will generate an error message if a step definition registers an unknown parameter type, but the suite will run.
  @regression @smoke @function
  Scenario: Hello world
    Given GET demo request 1
    Given GET demo request 2
    When POST demo request 1
    When POST demo request 2
    Then POST demo request 3
    Then Test SOAP API POST
    Then Test SOAP API GET