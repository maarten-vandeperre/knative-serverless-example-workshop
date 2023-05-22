package com.redhat.demo.configuration.microservice.account.resources

import com.redhat.demo.configuration.microservice.account.config.Mongo
import com.redhat.demo.configuration.microservice.account.config.Postgres
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response

@Path("/sync-account-data")
class SyncResource {

    @Inject
    @Postgres
    private lateinit var personPostgresRepository: PersonRepository

    @Inject
    @Mongo
    private lateinit var personMongoRepository: PersonRepository


    @Inject
    @Postgres
    private lateinit var addressPostgresRepository: AddressRepository

    @Inject
    @Mongo
    private lateinit var addressMongoRepository: AddressRepository

    @GET
    fun sync(): Response {
        println("start sync")
        println("post" + personPostgresRepository.javaClass.name)
        personPostgresRepository.search().forEach { personMongoRepository.save(it) } //TODO to use case
        addressPostgresRepository.search().forEach { addressMongoRepository.save(it) } //TODO to use case
        println("mongo" + personMongoRepository.javaClass.name)
        println("end sync")
        return Response.ok().build()
    }
}
