package org.lab1.client

import org.lab1.CarLib
import org.lab1.CarService
import java.net.MalformedURLException
import java.net.URL


object Client {
    @Throws(MalformedURLException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val url = URL("http://localhost:8083/lab1_j2ee_1_0_SNAPSHOT_war/ws/car?wsdl")
        val carService: CarService = CarService()

        val cars: List<CarLib> = carService.getAll()
        println("All cars")
        for (car in cars) {
            println(
                ("cars {" + "id=" + car.id
                        ).toString() + ", name=" + car.name.toString() +
                        ", country=" + car.country +
                        ", type=" + car.type +
                        ", win=" + car.win +
                        ", year=" + car.year + '}'
            )
        }
        println("Total cars: " + cars.size)

        println("\nCar < 2005")
        for (car in cars) {
            if (car.year < 2005) {
                System.out.println(
                    ("car {Id=" + car.id).toString() + ", name=" + car.name.toString() + ", country=" +
                            car.country + ", type=" + car.type + ", win=" + car.win +
                            ", year=" + car.year + "}"
                )
            }
        }

        println("\nCar")
        for (car in cars) {
            if (car.type == "Car") {
                System.out.println(
                    ("car {Id=" + car.id + ", name=" + car.name + ", country=" +
                            car.country + ", type=" + car.type + ", win=" + car.win +
                            ", year=" + car.year + "}"
                ))
            }
        }
    }
}
