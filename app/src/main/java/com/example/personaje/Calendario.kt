package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.database.Cursor
import android.provider.CalendarContract
import android.widget.Toast

class Calendario(private val context: Context) {

    fun crearEvento(titulo: String, descripcion: String, inicio: Long, fin: Long): Boolean {
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, inicio)
            put(CalendarContract.Events.DTEND, fin)
            put(CalendarContract.Events.TITLE, titulo)
            put(CalendarContract.Events.DESCRIPTION, descripcion)
            put(CalendarContract.Events.CALENDAR_ID,obtenerIdCalendario())
            put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Madrid")
        }

        val uri: Uri? = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)

        return if (uri != null) {
            Toast.makeText(context, "Evento creado exitosamente", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(context, "Error al crear el evento", Toast.LENGTH_SHORT).show()
            false
        }
    }

    @SuppressLint("Range")
    fun obtenerIdCalendario(): Long? {
        val projection = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.NAME
        )

        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val selection = "${CalendarContract.Calendars.VISIBLE} = 1"
        val cursor: Cursor? = context.contentResolver.query(uri, projection, selection, null, null)

        cursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID))
                return id
            }
        }
        return null
    }
}