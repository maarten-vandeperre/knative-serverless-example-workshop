package com.redhat.demo.configuration.monolith.config

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryAddressRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.JdbcTemplate
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresJdbcTemplate
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresPersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class RepositoryConfig() {
    private val postgresJdbcTemplate: JdbcTemplate?

    init {
        this.postgresJdbcTemplate = PostgresJdbcTemplate(
            "jdbc:postgresql://127.0.0.1:5432/knative_demo",
            "postgres",
            "postgres"
        )
    }

    @Produces
    fun inMemoryPersonRepository(): PersonRepository {
//        return InMemoryPersonRepository()
        return PostgresPersonRepository(postgresJdbcTemplate!!)
    }

    @Produces
    fun inMemoryAddressRepository(): AddressRepository {
        return InMemoryAddressRepository()
    }
}