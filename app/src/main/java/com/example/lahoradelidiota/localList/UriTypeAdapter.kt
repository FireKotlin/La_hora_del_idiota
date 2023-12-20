package com.example.lahoradelidiota.localList

import android.net.Uri
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class UriTypeAdapter : TypeAdapter<Uri?>() {
    override fun write(out: JsonWriter, value: Uri?) {
        out.value(value?.toString())
    }

    override fun read(`in`: JsonReader): Uri? {
        return if (`in`.peek() != JsonToken.NULL) {
            val uriString = `in`.nextString()
            if (uriString != null) {
                Uri.parse(uriString)
            } else {
                null
            }
        } else {
            `in`.nextNull()
            null
        }
    }
}
