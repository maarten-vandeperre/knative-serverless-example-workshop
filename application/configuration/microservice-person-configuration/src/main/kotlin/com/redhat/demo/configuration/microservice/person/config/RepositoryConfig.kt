package com.redhat.demo.configuration.microservice.person.config

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.core.repositories.JdbcTemplate
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryAddressRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryPersonRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbAddressRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbPersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.enterprise.inject.Produces
import jakarta.inject.Qualifier
import org.eclipse.microprofile.config.inject.ConfigProperty


@ApplicationScoped
class RepositoryConfig(
    @ConfigProperty(name = "db.type") dbType: String,
    @ConfigProperty(name = "db.mongo.connection_string", defaultValue = "not-set") mongoConnectionUrl: String,
) {
    private val mongoDatabase: MongoDatabase?
    private val databaseType: DatabaseType

    init {
        this.databaseType = when (dbType) {
            "IN_MEMORY" -> DatabaseType.IN_MEMORY
            "PHYSICAL" -> DatabaseType.PHYSICAL
            else -> throw IllegalStateException("$dbType is not yet supported")
        }
        if (databaseType == DatabaseType.PHYSICAL) {
            this.mongoDatabase = MongoClients.create(mongoConnectionUrl).getDatabase("microservice-person")
        } else {
            this.mongoDatabase = null
        }
    }

    @Produces
    @Default
    fun personRepository(): PersonRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryPersonRepository()
            DatabaseType.PHYSICAL -> MongoDbPersonRepository(mongoDatabase!!.getCollection("people"))
        }
    }

    enum class DatabaseType {
        IN_MEMORY, PHYSICAL
    }
}