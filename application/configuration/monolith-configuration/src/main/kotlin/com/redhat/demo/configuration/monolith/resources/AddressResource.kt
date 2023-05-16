package com.redhat.demo.configuration.monolith.resources

import com.redhat.demo.core.usecases.v1.address.*
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/addresses")
class AddressResource(
    private val createAddressUseCase: CreateAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val searchAddressUseCase: SearchAddressesUseCase
) {
    @POST
    @Consumes(value = [MediaType.APPLICATION_JSON])
    fun createAddress(data: RequestData): Response {
        try {
            return Response.ok(
                createAddressUseCase.execute(
                    CreateAddressUseCase.Request(
                        addressLine1 = data.addressLine1,
                        addressLine2 = data.addressLine2,
                        addressLine3 = data.addressLine3,
                        countryIsoCode = data.countryIsoCode
                    )
                ).ref
            ).build()
        } catch (e: CreateAddressUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    @PUT
    @Path("/{ref}")
    @Consumes(value = [MediaType.APPLICATION_JSON])
    fun updateAddress(@PathParam("ref") ref: String, data: RequestData): Response {
        try {
            return Response.ok(
                updateAddressUseCase.execute(
                    UpdateAddressUseCase.Request(
                        ref = ref,
                        addressLine1 = data.addressLine1,
                        addressLine2 = data.addressLine2,
                        addressLine3 = data.addressLine3,
                        countryIsoCode = data.countryIsoCode
                    )
                ).ref
            ).build()
        } catch (e: CreateAddressUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    @DELETE
    @Path("/{ref}")
    fun deleteAddress(@PathParam("ref") ref: String): Response {
        try {
            deleteAddressUseCase.execute(DeleteAddressUseCase.Request(ref = ref))
            return Response.ok().build()
        } catch (e: CreateAddressUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    @GET
    @Path("/{ref}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    fun getAddress(@PathParam("ref") ref: String): Response {
        try {
            return Response.ok(getAddressUseCase.execute(GetAddressUseCase.Request(ref = ref)).address).build()
        } catch (e: CreateAddressUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    @GET
    @Produces(value = [MediaType.APPLICATION_JSON])
    fun searchAddresses(): Response {
        try {
            return Response.ok(searchAddressUseCase.execute(SearchAddressesUseCase.Request()).addresses).build()
        } catch (e: SearchAddressesUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    data class RequestData(
        val addressLine1: String?,
        val addressLine2: String?,
        val addressLine3: String?,
        val countryIsoCode: String?
    )
}