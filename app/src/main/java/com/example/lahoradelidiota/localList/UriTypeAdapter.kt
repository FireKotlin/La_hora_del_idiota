package com.example.lahoradelidiota.localList

import android.net.Uri
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class UriTypeAdapter : TypeAdapter<Uri?>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, uri: Uri?) {
        if (uri == null) {
            out.nullValue()
        } else {
            out.value(uri.toString())
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Uri? {
        return when (`in`.peek()) {
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            JsonToken.BEGIN_OBJECT -> {
                `in`.beginObject()
                `in`.endObject()
                null
            }
            else -> Uri.parse(`in`.nextString())
        }
    }
}
