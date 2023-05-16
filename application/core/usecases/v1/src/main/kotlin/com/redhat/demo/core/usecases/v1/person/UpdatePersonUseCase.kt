package com.redhat.demo.core.usecases.v1.person

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import java.util.*

interface UpdatePersonUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?,
        val firstName: String?,
        val lastName: String?,
        val birthDate: String?
    )

    data class Response(
        val ref: String
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultUpdatePersonUseCase(
    private val personRepository: PersonRepository
) : UpdatePersonUseCase {
    override fun execute(requestData: UpdatePersonUseCase.Request): UpdatePersonUseCase.Response {
        if (requestData.ref == null) {
            throw UpdatePersonUseCase.ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw UpdatePersonUseCase.ValidationException("Ref is not a UUID format")
            }
        }
        if (requestData.firstName == null) {
            throw UpdatePersonUseCase.ValidationException("First name should not be null")
        }
        if (requestData.lastName == null) {
            throw UpdatePersonUseCase.ValidationException("Last name should not be null")
        }
        if(!personRepository.exists(UUID.fromString(requestData.ref))){
            throw UpdatePersonUseCase.ValidationException("No person with ref is found")
        }
        return UpdatePersonUseCase.Response(
            personRepository.save(
                PersonRepository.DbPerson(
                    ref = UUID.fromString(requestData.ref),
                    firstName = requestData.firstName,
                    lastName = requestData.lastName,
                    birthDate = requestData.birthDate
                )
            )
        )
    }

}