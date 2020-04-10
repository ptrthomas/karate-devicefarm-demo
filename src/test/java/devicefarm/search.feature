Feature: web browser test

  Background:
  * def DeviceFarmTarget = Java.type('devicefarm.DeviceFarmTarget')
  * configure driverTarget = new DeviceFarmTarget({ arn: arn })
   # * configure driver = { type: 'chromedriver', start: false, webDriverUrl: 'http://localhost:4444/wd/hub', showDriverLog: true }

  Scenario: try to login to github
  and then do a google search

    Given driver 'https://github.com/login'
    And input('#login_field', 'dummy')
    And input('#password', 'world')
    When submit().click("input[name=commit]")
    Then match html('#js-flash-container') contains 'Incorrect username or password.'

    Given driver 'https://google.com'
    And input("input[name=q]", 'karate dsl')
    When submit().click("input[name=btnI]")
    Then waitForUrl('https://github.com/intuit/karate')
    And screenshot()
