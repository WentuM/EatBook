package com.example.eatbook.ui.tables.booking.list.model

data class Hour(
    var title: String,
    var exist: Boolean,
    var countHour: Int
) {

    //переопределил метод для сравнения забронированных дат
    override fun equals(other: Any?): Boolean {
        var hour = other as Hour
        if (this.title == other.title && this.exist == other.exist) {
            return true
        }
        return false
    }
}