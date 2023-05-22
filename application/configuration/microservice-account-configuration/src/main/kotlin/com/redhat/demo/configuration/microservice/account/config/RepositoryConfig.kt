package com.redhat.demo.configuration.microservice.account.config

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.core.repositories.JdbcTemplate
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryAccountRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryAddressRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryPersonRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbAccountRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbAddressRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbPersonRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresAccountRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresAddressRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresJdbcTemplate
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresPersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.enterprise.inject.Produces
import jakarta.inject.Qualifier
import org.eclipse.microprofile.config.inject.ConfigProperty


@ApplicationScoped
class RepositoryConfig(
    @ConfigProperty(name = "db.type") dbType: String,
    @ConfigProperty(name = "db.postgres.connection_string", defaultValue = "not-set") postgresConnectionUrl: String,
    @ConfigProperty(name = "db.postgres.user", defaultValue = "not-set") postgresUser: String,
    @ConfigProperty(name = "db.postgres.password", defaultValue = "not-set") postgresPassword: String?,
    @ConfigProperty(name = "db.mongo.connection_string", defaultValue = "not-set") mongoConnectionUrl: String,
    @ConfigProperty(name = "db.mongo.user", defaultValue = "not-set") mongoUser: String,
    @ConfigProperty(name = "db.mongo.password", defaultValue = "not-set") mongoPassword: String?
) {
    private val postgresJdbcTemplate: JdbcTemplate?
    private val mongoDatabase: MongoDatabase?
    private val databaseType: DatabaseType

    init {
        this.databaseType = when (dbType) {
            "IN_MEMORY" -> DatabaseType.IN_MEMORY
            "PHYSICAL" -> DatabaseType.PHYSICAL
            else -> throw IllegalStateException("$dbType is not yet supported")
        }
        if (databaseType == DatabaseType.PHYSICAL) {
            this.postgresJdbcTemplate = PostgresJdbcTemplate(postgresConnectionUrl!!, postgresUser!!, postgresPassword!!)
            this.mongoDatabase = MongoClients.create(mongoConnectionUrl).getDatabase("microservice-account")
        } else {
            this.postgresJdbcTemplate = null
            this.mongoDatabase = null
        }
    }

    @Produces
    @Postgres
    fun personPostgresRepository(): PersonRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryPersonRepository()
            DatabaseType.PHYSICAL -> PostgresPersonRepository(postgresJdbcTemplate!!)
        }
    }

    @Produces
    @Mongo
    @Default
    fun personMongoRepository(): PersonRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryPersonRepository()
            DatabaseType.PHYSICAL -> MongoDbPersonRepository(mongoDatabase!!.getCollection("people"))
        }
    }

    @Produces
    @Postgres
    fun addressPostgresRepository(): AddressRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryAddressRepository()
            DatabaseType.PHYSICAL -> PostgresAddressRepository(postgresJdbcTemplate!!)
        }
    }

    @Produces
    @Mongo
    @Default
    fun addressMongoRepository(): AddressRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryAddressRepository()
            DatabaseType.PHYSICAL -> MongoDbAddressRepository(mongoDatabase!!.getCollection("addresses"))
        }
    }

    @Produces
    fun accountRepository(addressRepository: AddressRepository, personRepository: PersonRepository): AccountRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryAccountRepository(addressRepository, personRepository)
            DatabaseType.PHYSICAL -> MongoDbAccountRepository(
                mongoDatabase!!.getCollection("people"),
                mongoDatabase!!.getCollection("addresses")
            )
        }
    }

    @Produces
    fun mongoDataBase(): MongoDatabase {
        return mongoDatabase!!
    }

    enum class DatabaseType {
        IN_MEMORY, PHYSICAL
    }
}


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class Postgres

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class Mongo