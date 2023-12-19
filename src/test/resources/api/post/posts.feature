Feature: API Posts
  @GetAllPosts
  Scenario Outline: A user with a proper role should be able to retrieve all posts
    Given I am authenticated with "<User Role>" role
    When I make a request to retrieve all posts
    Then response should be "<Status Line>"
     And response should be valid and have a body
     And response should have a proper amount of posts
  Examples:
    | User Role     | Status Line    |
    | administrator | HTTP/1.1 200 OK|

  @CreateAPost
  @DeleteATestPost
  Scenario Outline: A user with proper role should be able to create a post
    Given I am authenticated with "<User Role>" role
    When I make a request to create a post with the following params
      | content      | title     | status  |
      | Post Content | TestPost  | publish |
    Then response should be "<Status Line>"
     And response should be valid and have a body
     And post should have been created with the proper params
    Examples:
      | User Role     | Status Line         |
      | administrator | HTTP/1.1 201 Created|

  @RetrieveAPost
  @CreateATestPost
  @DeleteATestPost
  Scenario Outline: A user with proper role should be able to retrieve a post
    Given I am authenticated with "<User Role>" role
    When I make a request to retrieve a post
    Then response should be "<Status Line>"
     And response should be valid and have a body
     And post should have been retrieved with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @UpdateAPost
  @CreateATestPost
  @DeleteATestPost
  Scenario Outline: A user with proper role should be able to update a post
    Given I am authenticated with "<User Role>" role
    When I make a request to update a post with the following params
      | content                        | title                   |
      | Test WAPI Post Content Updated | Test WAPI Title Updated |

    Then response should be "<Status Line>"
    And response should be valid and have a body
    And post should have been updated with the proper params
    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  #//  Examples:
  #//    | User Role     | Status Line     |
  #//    | administrator | HTTP/1.1 200 OK |
  #//@DeleteAPost
  #//Scenario: A user with proper role should be able to delete a post
  #//  Given I am authenticated with "<User Role>" role
  #//  When I make a request to delete a post
  #//  Then response should be "<Status Line>"
  #//   And response should be valid and have a body
  # //  And post should have been deleted