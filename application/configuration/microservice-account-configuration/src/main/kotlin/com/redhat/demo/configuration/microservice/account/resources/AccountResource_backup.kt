package com.redhat.demo.configuration.microservice.account.resources

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.security.cert.X509Certificate
import java.time.LocalDateTime
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Path("/")
class AccountResourceBackup(
    private val personRepository: PersonRepository
) {

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