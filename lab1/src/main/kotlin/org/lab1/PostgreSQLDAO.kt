package org.lab1

import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class PostgreSQLDAO {
    val all: List<CarLib>
        get() {
            val cars: MutableList<CarLib> = ArrayList<CarLib>()
            try {
                ConnectionUtil.connection.use { connection ->
                    val stmt: Statement? = connection?.createStatement()
                    val rs = stmt?.executeQuery("select * from cars") ?: return emptyList()
                    while (rs.next()) {
                        val id = rs.getInt("id")
                        val name = rs.getString("name")
                        val country = rs.getString("country")
                        val type = rs.getString("type")
                        val win = rs.getString("win")
                        val year = rs.getInt("year")

                        val car: CarLib = CarLib(id, name, country, type, win, year)
                        cars.add(car)
                    }
                }
            } catch (ex: SQLException) {
                Logger.getLogger(PostgreSQLDAO::class.java.name).log(
                    Level.SEVERE,
                    null, ex
                )
            }
            return cars
        }
}
