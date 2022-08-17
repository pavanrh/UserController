Feature: User feature
  Background: Create user
    Given user details

  Scenario: Verify that new user is created
    When creating a user
    Then user must be created

    Scenario: Verify user response body matches string
      When get user


  Scenario: Verify name field with blank
    When create a user with blank name field
    Then display error message name is required

  Scenario: Verify address field with blank
    When create a user with blank address field
    Then display error message address is required

  Scenario: Verify that user can be updated
    When updating a user
    Then user is updated

  Scenario: Verify existing user name is updated
    When updating the user name
    Then user name is updated

  Scenario: Verify existing user address is updated
    When Updating the user address
    Then user address is updated

  Scenario: Verify existing user marks is updated
    When updating the marks
    Then user marks is updated

  Scenario: Verify updated user
    When get updated user
    Then display updated user

  Scenario: Verify updating user name with blank
    When update user with blank name
    Then display error name is required

  Scenario: Verify updating user address with blank
    When update user with blank address
    Then display error address is required

  Scenario: Verify the user is deleted
    When delete a user
    Then user is deleted

    Scenario: Verify multiple users are created
      When creating multiple users
      Then multiple users are created

      Scenario: Verify multiple username with a blank
        When create multiple users with blank name
        Then error message name is required

  Scenario: Verify multiple user address with a blank
    When create multiple users with blank address
    Then error message address is required





