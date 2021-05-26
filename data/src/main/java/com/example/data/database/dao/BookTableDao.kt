package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.entity.BookTableEntity
import com.example.data.database.entity.FullBookTable
import com.example.data.database.entity.FullReview

@Dao
interface BookTableDao {

    @Delete
    suspend fun delete(bookTableEntity: BookTableEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookTableEntity: BookTableEntity)

    @Query("SELECT * FROM book_table WHERE id = :idBookTable")
    suspend fun getBookTableById(idBookTable: String): BookTableEntity


    @Query(
        "SELECT book_table.id, book_table.id_table, book_table.day, book_table.time," +
                "book_table.count_hour, `table`.title, `table`.image, `table`.id_rest, `table`.name_rest " +
                "FROM book_table, `table` " +
                "WHERE (book_table.day = :day AND book_table.id_table = :idTable)"
    )
    suspend fun getAllTablesByDayAndTable(day: String, idTable: String): List<FullBookTable>

    @Query(
        "SELECT book_table.id, book_table.id_table, book_table.day, book_table.time," +
                "book_table.count_hour, `table`.title, `table`.image, `table`.id_rest, `table`.name_rest " +
                "FROM book_table, `table` " +
                "WHERE (book_table.id_user = :idUser AND `table`.id = book_table.id_table)"
    )
    suspend fun getAllFullBookTable(idUser: String): List<FullBookTable>
}