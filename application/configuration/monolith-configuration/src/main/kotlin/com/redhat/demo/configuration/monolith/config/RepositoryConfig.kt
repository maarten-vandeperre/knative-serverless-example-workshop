package com.redhat.demo.configuration.monolith.config

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryAddressRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryPersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class RepositoryConfig {
    @Produces
    fun inMemoryPersonRepository(): PersonRepository {
        return InMemoryPersonRepository()
    }

    @Produces
    fun inMemoryAddressRepository(): AddressRepository {
        return InMemoryAddressRepository()
    }
}