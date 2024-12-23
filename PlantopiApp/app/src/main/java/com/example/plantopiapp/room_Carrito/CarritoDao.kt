package com.example.plantopiapp.room_Jardin

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.plantopiapp.dataclases.Carrito
import com.example.plantopiapp.dataclases.GroupedCarrito

@Dao
interface CarritoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCarritoItem(item: Carrito)

    @Query("UPDATE Carrito SET cantidad = :cantidad, url = :photoUrl WHERE nombrePlanta = :nombrePlanta AND precioPlanta = :precioPlanta")
    suspend fun updateQuantity(nombrePlanta: String, precioPlanta: Int, cantidad: Int, photoUrl: String?): Int

    @Transaction
    suspend fun addOrUpdateCarrito(item: Carrito) {
        val rowsUpdated = updateQuantity(item.nombrePlanta, item.precioPlanta, item.cantidad, item.url)
        if (rowsUpdated == 0) {
            insertCarritoItem(item)
        }
    }

    @Query("SELECT * FROM Carrito WHERE nombrePlanta = :nombrePlanta AND precioPlanta = :precioPlanta LIMIT 1")
    suspend fun findProduct(nombrePlanta: String, precioPlanta: Int): Carrito?

    @Delete
    suspend fun deleteCarritoItem(item: Carrito)

    @Query("SELECT * FROM Carrito")
    suspend fun getGroupedProducts(): List<Carrito>

    @Query("SELECT SUM(cantidad) FROM Carrito")
    fun getTotalItemCount(): LiveData<Int>

    @Query("SELECT SUM(precioPlanta * cantidad) FROM Carrito")
    fun getTotalCartPrice(): LiveData<Int>
}
