package com.example.personaje

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast

class Calendario(private val context: Context) {

    fun crearEvento(titulo: String, descripcion: String): Boolean {
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, System.currentTimeMillis() + 1000 * 60 * 60)
            put(CalendarContract.Events.DTEND, System.currentTimeMillis() + 1000 * 60 * 60 * 2)
            put(CalendarContract.Events.TITLE, titulo)
            put(CalendarContract.Events.DESCRIPTION, descripcion)
            put(CalendarContract.Events.CALENDAR_ID,1)
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
}