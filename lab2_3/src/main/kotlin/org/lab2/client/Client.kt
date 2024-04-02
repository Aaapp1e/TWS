package org.lab2.client

import org.lab2.CarLib
import org.lab2.CarService
import java.util.*

class CarServiceClientCLI(
    private val carService: CarService
) {
    fun run() {
        val scanner = Scanner(System.`in`)

        while (true) {
            println("Выберите действие: c - Добавить машину, r - Получить машину, u - Обновить машину, d - Удалить машину, e - Выйти")
            val choice = scanner.nextLine()

            when (choice) {
                "c" -> {
                    val car = inputCar()
                    val success = carService.createCar(car)
                    if (success) println("Машина добавлена успешно") else println("Ошибка при добавлении машины")
                }
                "r" -> {
                    println("Введите ID машины:")
                    val id = scanner.nextLine().toInt()
                    val car = carService.getCar(id)
                    println("Машинаа: $car")
                }
                "u" -> {
                    println("Введите ID мишины для обновления:")
                    val id = scanner.nextLine().toInt()

                    val updatedCar = inputCar()
                    if (updatedCar != null) {
                        updatedCar.id = id
                    };

                    val success = carService.updateCar(updatedCar)
                    if (success) println("Машина обновлена успешно") else println("Ошибка при обновлении машины")
                }
                "d" -> {
                    println("Введите ID машины для удаления:")
                    val id = scanner.nextLine().toInt()
                    val success = carService.deleteCar(id)
                    if (success) println("Машина обновлена успешно") else println("Ошибка при обновлении машины")
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

    fun inputCar(): CarLib? {
        val scanner = Scanner(System.`in`)

        // Запрашиваем данные для обновления
        println("Введите фирму мишины:")
        val name = scanner.nextLine()
        println("Введите страну мишины:")
        val country = scanner.nextLine()
        println("Введите тип мишины:")
        val type = scanner.nextLine()
        println("Введите win мишины:")
        val win = scanner.nextLine()
        println("Введите год изготовления мишины:")
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

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val carService: CarService = CarService()

            val clientCLI = CarServiceClientCLI(carService)
            clientCLI.run()
        }
    }
}
