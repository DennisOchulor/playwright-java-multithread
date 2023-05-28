# playwright-java-multithread
Make multithreading with [Playwright Java](https://github.com/microsoft/playwright-java) easier.

Taken from https://github.com/microsoft/playwright-java#is-playwright-thread-safe
> **Is Playwright thread-safe?** No, Playwright is not thread safe, i.e. all its methods as well as methods on all objects created by it (such as BrowserContext, Browser, Page etc.) are expected to be called on the same thread where Playwright object was created or proper synchronization should be implemented to ensure only one thread calls Playwright methods at any given time. Having said that it's okay to create multiple Playwright instances each on its own thread.

Since Playwright isn't thread-safe, it is often difficult to multithread with it in an intuitive manner. This mini library aims to provide a simple and easy to use API for multithreading with Playwright Java.

## Summary
1. [PlaywrightThread](https://github.com/DennisOchulor/playwright-java-multithread/#playwrightthread)

## PlaywrightThread
At the core of this library is the [PlaywrightThread](https://github.com/DennisOchulor/playwright-java-multithread/blob/main/src/main/java/com/github/dennisochulor/playwright_java_multithread/PlaywrightThread.java) which acts as an extension of the Java Thread class. It binds a Playwright instance and its corresponding Browsers directly onto a thread. Methods to access the underlying Playwright and Browsers instances are provided 
