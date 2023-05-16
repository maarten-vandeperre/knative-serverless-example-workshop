package com.redhat.demo.infra.dataproviders.postgres.repositories

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

interface JdbcTemplate {
    fun execute(query: String, params: List<Any?> = emptyList())

    @Throws(JdbcException::class)
    fun <T> query(query: String, params: List<Any?> = emptyList(), mapper: (rs: ResultSet) -> T): T?

    @Throws(JdbcException::class)
    fun <T> queryForList(query: String, params: List<Any?> = emptyList(), mapper: (rs: ResultSet) -> T): List<T>

    class JdbcException(message: String) : Exception(message)
}

class PostgresJdbcTemplate(
    private val connectionUrl: String,
    private val user: String,
    private val password: String
) : JdbcTemplate {

    @Throws(JdbcTemplate.JdbcException::class)
    override fun execute(query: String, params: List<Any?>) {
        try {
            DriverManager.getConnection(connectionUrl, user, password)
                .use { conn ->
                    conn
                        ?.prepareStatement(query)
                        ?.use { stmt ->
                            params.forEachIndexed { index, k -> stmt.setObject(index + 1, k) }
                            stmt.execute()
                        }
                }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
            throw JdbcTemplate.JdbcException(e.localizedMessage)
        } catch (e: Exception) {
            throw JdbcTemplate.JdbcException(e.localizedMessage)
        }
    }

    @Throws(JdbcTemplate.JdbcException::class)
    override fun <T> query(query: String, params: List<Any?>, mapper: (rs: ResultSet) -> T): T? {
        return try {
            DriverManager.getConnection(connectionUrl, user, password)
                .use { conn ->
                    conn
                        ?.prepareStatement(query)
                        ?.use { stmt ->
                            params.forEachIndexed { index, k -> stmt.setObject(index + 1, k) }
                            val resultSet = stmt.executeQuery()
                            if(resultSet.next()){
                                mapper(resultSet)
                            } else {
                                null
                            }

                        }
                }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
            throw JdbcTemplate.JdbcException(e.localizedMessage)
        } catch (e: Exception) {
            throw JdbcTemplate.JdbcException(e.localizedMessage)
        }
    }

    @Throws(JdbcTemplate.JdbcException::class)
    override fun <T> queryForList(query: String, params: List<Any?>, mapper: (rs: ResultSet) -> T): List<T> {
        return try {
            DriverManager.getConnection(connectionUrl, user, password)
                .use { conn ->
                    conn
                        ?.prepareStatement(query)
                        ?.use { stmt ->
                            val result = mutableListOf<T>()
                            params.forEachIndexed { index, k -> stmt.setObject(index + 1, k) }
                            val resultSet = stmt.executeQuery()
                            while (resultSet.next()) {
                                result.add(mapper(resultSet))
                            }
                            result
                        } ?: emptyList()
                }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
            throw JdbcTemplate.JdbcException(e.localizedMessage)
        } catch (e: Exception) {
            throw JdbcTemplate.JdbcException(e.localizedMessage)
        }
    }
}