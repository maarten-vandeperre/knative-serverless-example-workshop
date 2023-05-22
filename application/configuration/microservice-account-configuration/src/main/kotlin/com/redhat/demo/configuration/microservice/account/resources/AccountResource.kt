package com.redhat.demo.configuration.microservice.account.resources

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.v1.account.SearchAccountsUseCase
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.time.LocalDateTime
import java.util.*

@Path("/api/accounts")
class AccountResource(
    private val personRepository: PersonRepository,
    private val searchAccountsUseCase: SearchAccountsUseCase
) {

    @GET
    fun getAccounts(): Response {
        return Response.ok(
            searchAccountsUseCase.execute(SearchAccountsUseCase.Request()).accounts
        ).build()
    }

    @POST
    @Path("/monolith-data-changed/via-sink")
    @Operation(summary = "Create a notion that a person got updated/created/deleted via Knative Kafka Sink")
    @Tag(name = "PEOPLE_API")
    fun monolithDataChangedViaSinkProcess(@Context httpHeaders: HttpHeaders, personData: String): Response {
        return internalProcess(httpHeaders, personData, "sink")
    }

    @POST
    @Path("/via-sink")
    @Operation(summary = "Create a notion that a person got updated/created/deleted via Knative Kafka Sink")
    @Tag(name = "PEOPLE_API")
    fun sinkProcess(@Context httpHeaders: HttpHeaders, personData: String): Response {
        return internalProcess(httpHeaders, personData, "sink")
    }

    @POST
    @Path("/")
    @Operation(summary = "Create a notion that a person got updated/created/deleted via Knative Kafka Channel")
    @Tag(name = "PEOPLE_API")
    fun channelProcess(@Context httpHeaders: HttpHeaders, personData: String): Response {
        return internalProcess(httpHeaders, personData, "kafka-channel")
    }

    @POST
    @Path("/via-broker")
    @Operation(summary = "Create a notion that a person got updated/created/deleted via Knative Kafka Broker")
    @Tag(name = "PEOPLE_API")
    fun brokerProcess(@Context httpHeaders: HttpHeaders, personData: String): Response {
        return internalProcess(httpHeaders, personData, "kafka-broker")
    }

    fun internalProcess(httpHeaders: HttpHeaders, personData: String, source: String): Response {
        println("person update event received")
        println(personData)
        println(httpHeaders.requestHeaders)
        println(httpHeaders.date)
        personRepository.save(
            PersonRepository.DbPerson(
                ref = UUID.randomUUID(),
                firstName = "kafka-test-" + source,
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