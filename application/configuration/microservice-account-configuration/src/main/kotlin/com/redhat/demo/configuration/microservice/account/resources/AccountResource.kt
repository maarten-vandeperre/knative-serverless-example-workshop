package com.redhat.demo.configuration.microservice.account.resources

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/")
class AccountResource {
  @POST
  @Consumes(value = [MediaType.APPLICATION_JSON])
  @Operation(summary = "Create a notion that a person got updated/created/deleted")
  @Tag(name = "PEOPLE_API")
  fun process(@Context httpHeaders: HttpHeaders, personData: PersonData): Response {
    println("person update event received")
    println(personData.ref)
    println(httpHeaders)
    return Response.ok().build()
  }
}

class PersonData{
    var ref: String? = null
}