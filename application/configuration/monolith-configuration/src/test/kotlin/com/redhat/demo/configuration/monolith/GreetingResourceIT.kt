package com.redhat.demo.configuration.monolith

import io.quarkus.test.junit.QuarkusIntegrationTest

@QuarkusIntegrationTest
class GreetingResourceIT : GreetingResourceTest() { // Execute the same tests but in packaged mode.
}