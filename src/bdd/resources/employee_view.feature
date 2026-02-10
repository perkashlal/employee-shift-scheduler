Feature: Employee Swing View

Scenario: Show employees on startup
  Given the database contains the following employees
    | id | name  |
    | 1  | Alice |
    | 2  | Bob   |
  When the Employee View is shown
  Then the list contains employees
    | 1 | Alice |
    | 2 | Bob   |