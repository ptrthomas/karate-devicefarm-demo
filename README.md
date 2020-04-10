# Karate and AWS DeviceFarm Demo

This is a project that demonstrates the following:
* [Karate](https://github.com/intuit/karate) for Web-Browser automation using the [W3C WebDriver Protocol](https://w3c.github.io/webdriver/)
* [AWS DeviceFarm](https://docs.aws.amazon.com/devicefarm/latest/testgrid/what-is-testgrid.html) (for desktop browser testing) as a remote WebDriver instance
* The use of a custom [`Target`](https://github.com/intuit/karate/tree/master/karate-core#configure-drivertarget) implementation that abstracts the code to start a WebDriver session out of your test. It does the following:
  * uses the [AWS DeviceFarm SDK](https://mvnrepository.com/artifact/software.amazon.awssdk/devicefarm) to provision a DeviceFarm URL / instance
  * shapes the [`webDriverSession`](https://github.com/intuit/karate/tree/master/karate-core#webdriversession) and other driver config.
  * the code can be viewed here: [`DeviceFarmTarget.java`](src/test/java/devicefarm/DeviceFarmTarget.java)

## Prerequisites:
* Follow the [instructions here](https://docs.aws.amazon.com/devicefarm/latest/testgrid/getting-started-local.html) to create a DeviceFarm project on AWS and set up your AWS access and secret keys in your environment.
* Change the `urn` in `karate-config.js` to match the DeviceFarm URN of the DeviceFarm project.

Now you can run `SearchRunner.java` as a JUnit test.
