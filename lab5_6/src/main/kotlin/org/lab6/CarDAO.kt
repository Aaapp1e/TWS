package org.lab6

import java.sql.*


class CarDAO {
    private var jdbcConnection: Connection? = null

    @Throws(SQLException::class)
    protected fun connect() {
        if (jdbcConnection == null || jdbcConnection!!.isClosed) {
            jdbcConnection = ConnectionUtil.connection;
        }
    }

    @Throws(SQLException::class)
    protected fun disconnect() {
        if (jdbcConnection != null && !jdbcConnection!!.isClosed) {
            jdbcConnection!!.close()
        }
    }

    @Throws(SQLException::class)
    fun insertCar(car: CarLib?): Boolean {
        val sql = "INSERT INTO cars (name, country, type, year, win) VALUES (?, ?, ?, ?, ?)"
        connect()

        if (car == null) {
            throw SQLException("car cannot be null")
        }
        if (jdbcConnection == null) {
            throw SQLException("jdb connection is null")
        }

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setString(1, car.name)
        statement.setString(2, car.country)
        statement.setString(3, car.type)
        statement.setInt(4, car.year)
        statement.setString(5, car.win)

        val rowInserted = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowInserted
    }

    @Throws(SQLException::class)
    fun listAllCars(): List<CarLib> {
        val listCar: MutableList<CarLib> = ArrayList()

        val sql = "SELECT * FROM cars"

        connect()

        val statement = jdbcConnection!!.createStatement()
        val resultSet = statement.executeQuery(sql)

        while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val name = resultSet.getString("name")
            val country = resultSet.getString("country")
            val type = resultSet.getString("type")
            val year = resultSet.getInt("year")
            val win = resultSet.getString("win")

            val car = CarLib(id, name, country, type, win, year)
            listCar.add(car)
        }

        resultSet.close()
        statement.close()

        disconnect()

        return listCar
    }

    @Throws(SQLException::class)
    fun getCar(id: Int): CarLib? {
        var car: CarLib? = null
        val sql = "SELECT * FROM cars WHERE id = ?"

        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setInt(1, id)

        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val name = resultSet.getString("name")
            val country = resultSet.getString("country")
            val type = resultSet.getString("type")
            val year = resultSet.getInt("year")
            val win = resultSet.getString("win")

            car = CarLib(id, name, country, type, win, year)
        }

        resultSet.close()
        statement.close()

        disconnect()

        return car
    }


    @Throws(SQLException::class)
    fun updateCar(car: CarLib): Boolean {
        val sql = "UPDATE cars SET name = ?, country = ?, type = ?, year = ?, win = ? WHERE id = ?"
        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setString(1, car.name)
        statement.setString(2, car.country)
        statement.setString(3, car.type)
        statement.setInt(4, car.year)
        statement.setString(5, car.win)
        statement.setInt(6, car.id)

        val rowUpdated = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowUpdated
    }

    @Throws(SQLException::class)
    fun deleteCar(id: Int): Boolean {
        val sql = "DELETE FROM cars WHERE id = ?"
        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setInt(1, id)

        val rowDeleted = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowDeleted
    }
}

