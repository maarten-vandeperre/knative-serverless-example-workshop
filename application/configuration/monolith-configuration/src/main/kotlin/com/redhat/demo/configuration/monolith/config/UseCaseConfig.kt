package com.redhat.demo.configuration.monolith.config

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.v1.address.*
import com.redhat.demo.core.usecases.v1.person.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class UseCaseConfig {

    @Produces
    fun createPersonUseCase(personRepository: PersonRepository): CreatePersonUseCase {
        return DefaultCreatePersonUseCase(personRepository)
    }

    @Produces
    fun updatePersonUseCase(personRepository: PersonRepository): UpdatePersonUseCase {
        return DefaultUpdatePersonUseCase(personRepository)
    }

    @Produces
    fun deletePersonUseCase(personRepository: PersonRepository): DeletePersonUseCase {
        return DefaultDeletePersonUseCase(personRepository)
    }

    @Produces
    fun getPersonUseCase(personRepository: PersonRepository): GetPersonUseCase {
        return DefaultGetPersonUseCase(personRepository)
    }

    @Produces
    fun searchPeopleUseCase(personRepository: PersonRepository): SearchPeopleUseCase {
        return DefaultSearchPeopleUseCase(personRepository)
    }

    @Produces
    fun createAddressUseCase(addressRepository: AddressRepository): CreateAddressUseCase {
        return DefaultCreateAddressUseCase(addressRepository)
    }

    @Produces
    fun updateAddressUseCase(addressRepository: AddressRepository): UpdateAddressUseCase {
        return DefaultUpdateAddressUseCase(addressRepository)
    }

    @Produces
    fun deleteAddressUseCase(addressRepository: AddressRepository): DeleteAddressUseCase {
        return DefaultDeleteAddressUseCase(addressRepository)
    }

    @Produces
    fun getAddressUseCase(addressRepository: AddressRepository): GetAddressUseCase {
        return DefaultGetAddressUseCase(addressRepository)
    }

    @Produces
    fun searchAddressesUseCase(addressRepository: AddressRepository): SearchAddressesUseCase {
        return DefaultSearchAddressUseCase(addressRepository)
    }
}