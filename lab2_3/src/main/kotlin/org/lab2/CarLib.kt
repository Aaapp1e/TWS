package org.lab2

class CarLib {
    var id: Int = 0
    var name: String? = null
    var country: String? = null
    var type: String? = null
    var win: String? = null
    var year: Int = 0

    constructor()

    constructor(id: Int, name: String, country: String, type: String, win: String, year: Int) {
        this.id = id
        this.name = name
        this.country = country
        this.type = type
        this.win = win
        this.year = year
    }

    override fun toString(): String {
        return ("CarService {" + "Id=" + id
                + "name=" + name +
                ", country=" + country +
                ", type=" + type +
                ", win=" + win +
                ", year=" + year +
                '}')
    }
}
