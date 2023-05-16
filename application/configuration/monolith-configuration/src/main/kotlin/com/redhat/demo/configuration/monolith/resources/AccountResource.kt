package com.redhat.demo.configuration.monolith.resources

import com.redhat.demo.core.domain.v1.ReadAccount
import com.redhat.demo.core.usecases.v1.account.SearchAccountsUseCase
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Query

@GraphQLApi
class AccountResource(
    private val searchAccountsUseCase: SearchAccountsUseCase
) {

    @Query("allAccounts")
    @Description("Search all accounts in the system")
    fun allAccounts(): List<ReadAccount> {
        return searchAccountsUseCase.execute(SearchAccountsUseCase.Request()).accounts
    }
}