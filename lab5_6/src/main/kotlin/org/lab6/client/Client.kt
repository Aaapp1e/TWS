package org.lab6.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.coroutines.runBlocking
import org.lab6.CarLib
import java.util.*


class CarServiceClientCLI(
    private val client: HttpClient
) {
    fun run() {
        val scanner = Scanner(System.`in`)

        while (true) {
            println("Выберите действие: c - Добавить машину, r - Получить машину, u - Обновить машину, d - Удалить машину, e - Выйти")
            val choice = scanner.nextLine()

            when (choice) {
                "c" -> {
                    val car = inputCar()
                    if (car == null) {
                        println("Проверьте корректность введенных данных")
                        continue
                    }
                    runBlocking {
                        try {
                            createCar(car)
                            println("Машина создана")
                        } catch (e: Exception) {
                            println("Произошла ошибка: ${e.message}")
                        }
                    }
                }
                "r" -> {
                    println("Введите ID машины:")
                    val id = scanner.nextLine().toInt()
                    runBlocking {
                        try {
                            val car = getCar(id)
                            println("машина с id $id: $car")
                        } catch (e: Exception) {
                            println("Произошла ошибка: ${e.message}")
                        }
                    }
                }
                "u" -> {
                    println("Введите ID машины для обновления:")
                    val id = scanner.nextLine().toInt()

                    val updatedCar = inputCar()
                    if (updatedCar != null) {
                        updatedCar.id = id
                    } else continue;

                    runBlocking {
                        try {
                            val success = updateCar(id, updatedCar)
                            if (success) println("Машина обновлена успешно") else println("Ошибка при обновлении машины")
                        } catch (e: Exception) {
                            println("Произошла ошибка: ${e.message}")
                        }
                    }
                }
                "d" -> {
                    println("Введите ID машины для удаления:")
                    val id = scanner.nextLine().toInt()
                    runBlocking {
                        try {
                            val success = deleteCar(id)
                            if (success) println("Машина удалена успешно") else println("Ошибка при удалении машины")
                        } catch (e: Exception) {
                            println("Произошла ошибка: ${e.message}")
                        }
                    }
                }
                "e" -> {
                    println("Выход из программы...")
                    return
                }
                else -> {
                    println("Некорректный ввод. Попробуйте снова.")
                }
            }

        }
    }

    private fun inputCar(): CarLib? {
        val scanner = Scanner(System.`in`)

        // Запрашиваем данные для обновления
        println("Введите название машины:")
        val name = scanner.nextLine()
        println("Введите автора машины:")
        val country = scanner.nextLine()
        println("Введите жанр машины:")
        val type = scanner.nextLine()
        println("Введите isbn машины:")
        val win = scanner.nextLine()
        println("Введите год издания машины:")
        val yearInput = scanner.nextLine()
        val year: Int;

        try {
            year = yearInput?.toInt() ?: throw NumberFormatException("Год не введен")
        } catch (e: NumberFormatException) {
            println("Ошибка: неверный формат года. Пожалуйста, введите число.")
            return null
        }

        val car = CarLib(0, name, country, type, win, year )
        return car
    }

    suspend fun getAllCars(): List<CarLib> {
        val cars: List<CarLib> = client.get("http://localhost:8080/cars").body()
        return cars
    }

    private suspend fun getCar(id: Int): CarLib {
        val car: CarLib = client.get("http://localhost:8080/cars/$id").body()
        return car


    }

    private suspend fun createCar(car: CarLib): Boolean {
        val response = client.post("http://localhost:8080/cars") {
            contentType(ContentType.Application.Json)
            setBody(car)
        }
        return response.status.value == 200
    }

    private suspend fun updateCar(id: Int, car: CarLib): Boolean {
        val response = client.put("http://localhost:8080/cars/$id") {
            contentType(ContentType.Application.Json)
            setBody(car)
        }
        return response.status.value == 200

    }

    private suspend fun deleteCar(id: Int): Boolean {
        val response = client.delete("http://localhost:8080/cars/$id")
        return response.status.value == 200
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }

            val clientCLI = CarServiceClientCLI(client)
            clientCLI.run()
        }
    }

}

