package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.mongodb.client.MongoCollection
import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import org.bson.Document
import java.util.*

class MongoDbAccountRepository(
    private val collection: MongoCollection<Document>
) : AccountRepository {
    override fun search(): List<AccountRepository.DbAccount> {
        return collection.find().toList().map {
            AccountRepository.DbAccount(
                personRef = UUID.fromString(it.getString("personRef")),
                firstName = it.getString("firstName"),
                lastName = it.getString("lastName"),
                birthDate = it.getString("birthDate"),
                addressRef = it.getString("addressRef").let { UUID.fromString(it) },
                addressLine1 = it.getString("addressLine1"),
                addressLine2 = it.getString("addressLine2"),
                addressLine3 = it.getString("addressLine3"),
                countryIsoCode = it.getString("countryIsoCode")
            )
        }
    }
}