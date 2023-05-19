package com.redhat.demo.configuration.microservice.account.resources

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.time.LocalDateTime
import java.util.*

@Path("/")
class AccountResource(
    private val personRepository: PersonRepository
) {
    @POST
    @Operation(summary = "Create a notion that a person got updated/created/deleted")
    @Tag(name = "PEOPLE_API")
    fun process(@Context httpHeaders: HttpHeaders, personData: String): Response {
        println("person update event received")
        println(personData)
        println(httpHeaders)
        personRepository.save(
            PersonRepository.DbPerson(
                ref = UUID.randomUUID(),
                firstName = "kafka-test",
                lastName = LocalDateTime.now().toString(),
                birthDate = null,
                addressRef = null
            )
        )
        return Response.ok().build()
    }
}

class PersonData {
    var ref: String? = null
}