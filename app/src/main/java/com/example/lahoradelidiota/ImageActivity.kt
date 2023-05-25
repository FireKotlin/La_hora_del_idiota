package com.example.lahoradelidiota

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lahoradelidiota.databinding.ActivityImageBinding


class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)


        val imageList = mutableListOf<image>()

        imageList.add(image(R.drawable.p1, "Iditota tomando un baño"))
        imageList.add(image(R.drawable.p2, "Idiota con exceso de flow"))
        imageList.add(image(R.drawable.p3, "Idiota con un poco de hambre"))
        imageList.add(image(R.drawable.p4, "Encuentra las 5 diferencias️‼️"))
        imageList.add(image(R.drawable.p5, "Idiota pidiendo piedra SEEEE!!"))
        imageList.add(image(R.drawable.p6, "Triste se cayó su torta"))
        imageList.add(image(R.drawable.p7, "Pasó el Omar enfrente, hagan sus teorías!! "))
        imageList.add(image(R.drawable.p8, "Idiota sin miedo al exito SEEE!!"))
        imageList.add(image(R.drawable.p9, "Idiotas en camino a visitar a  su jefe!!"))
        imageList.add(image(R.drawable.p10, "Desfile de idiotas, Ecatepec de Morelos, 2023."))
        imageList.add(image(R.drawable.p11, "Idiota ansado!!"))
        imageList.add(image(R.drawable.p12, "Perlita antes de los tacos!!"))
        imageList.add(image(R.drawable.p13, "Idiota sediento de fierro!!!"))
        imageList.add(image(R.drawable.p14, "Encuentra las 5 diferencias️‼️"))
        imageList.add(image(R.drawable.p15, "El pabro maquillandose"))
        imageList.add(image(R.drawable.p16, "A grabar un disco juntos" +
                "       Ya pa que te digo!!"))
        imageList.add(image(R.drawable.p17, "Feliz Navidad!!"))
        imageList.add(image(R.drawable.p18, "Idiota Belico!!"))
        imageList.add(image(R.drawable.p19, "Reunion de Idiotas, SEEEEEEEEEEE!!!"))
        imageList.add(image(R.drawable.p20, "Idiota en el tradajo!!"))
        imageList.add(image(R.drawable.p21, "Y que lo tiran!!"))
        imageList.add(image(R.drawable.p22, "FLOW VIOLENTO!!"))
        imageList.add(image(R.drawable.p23, "El creador de la hora del idiota posando!!"))
        imageList.add(image(R.drawable.p24, "?"))
        imageList.add(image(R.drawable.p25, "Junta de idiotas!!"))
        imageList.add(image(R.drawable.p26, "Idiotas en apareamiento SEEEEE!!!!"))
        imageList.add(image(R.drawable.p27, "Ansado de tragar!!"))
        imageList.add(image(R.drawable.p28, "Feliz"))
        imageList.add(image(R.drawable.p29, "Después de la tragedia!!"))
        imageList.add(image(R.drawable.p30, "\uD83D\uDE2E"))
        imageList.add(image(R.drawable.p31, "Ojos de perro con chorro!!"))
//  imageList.add(image(R.drawable.p32, ""))
        imageList.add(image(R.drawable.p33, "Analizando!!"))
        imageList.add(image(R.drawable.p34, "Idiota pidiendo fierro"))
        imageList.add(image(R.drawable.p35, "Buenas noches antes de tomarse el activo!!"))
        imageList.add(image(R.drawable.p36, "Extra, Extra!!!!!!!!!"))
        imageList.add(image(R.drawable.p37, "\uD83E\uDD28"))
        imageList.add(image(R.drawable.p38, "La lagartija parada!!"))
        imageList.add(image(R.drawable.p39, "\uD83E\uDD13"))
        imageList.add(image(R.drawable.p40, "La ultima cena!!"))
        imageList.add(image(R.drawable.p41, "WhatsApp ️‼️"))
        imageList.add(image(R.drawable.p42, "Al fin encontraron a su jefe!!!"))
        imageList.add(image(R.drawable.p43, "El creador de la hora miando despues de darle los limones mas secos a los clientes SEEEE‼️"))



        val adapter = photoAdapter()
        binding.recyclerView.adapter = adapter
        adapter.setDataList(imageList)

    }
}
