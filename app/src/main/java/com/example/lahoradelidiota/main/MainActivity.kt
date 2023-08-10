package com.example.lahoradelidiota.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lahoradelidiota.detail.IDetailActivity
import com.example.lahoradelidiota.others.Idiota
import com.example.lahoradelidiota.others.IdiotaAdapter
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.R.color.nav
import com.example.lahoradelidiota.R.color.nav2
import com.example.lahoradelidiota.R.color.white
import com.example.lahoradelidiota.database.DbIdiotRecycler
import com.example.lahoradelidiota.database.LoginActivity
import com.example.lahoradelidiota.database.PantallaIdiota
import com.example.lahoradelidiota.databinding.ActivityMainBinding
import com.example.lahoradelidiota.photoactivity.ImageActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1000)
        setTheme(R.style.Theme_LaHoraDelIdiota)

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerIdiot.layoutManager = LinearLayoutManager(this)


        val idiotList = mutableListOf<Idiota>()
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fpabro.jpeg?alt=media&token=05059b0a-e8a0-41b0-8b05-27cb0ec68e6b",
                "1",
                "El Pabro",
                "Incalculable",
                "La tienda de doña huevas",
                "Cagarla",
                "Este idiota se caracteriza por ser extremadamente idiota y por no tener nariz, su cuerpo no esta diseñado para soportar tanta idiotez y por eso se suicida.\n" +
                        "El nivel de idiotez de este especimen es respaldado por sus mas de 37 intentos de suicidio, suele ahorcarse con la longaniza y cortarse con corcholatas, el primer intento de la lagartija parada fue despues de cagarse en la express.\n" +
                        "Todas las noches pasa a visitar a doña huevas para comprar barritas para cortarse con ellas, antes de cada intento grita SIN MIEDO AL ÉXITO!!, SEEE un idiota ejemplar, YA NO LE SIGO ESCRIBIENDO POR QUE SI NO SE SUICIDA!!!!"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fpina.jpg?alt=media&token=f4633c45-262d-46a0-85b9-9243e2d567f1",
                "2",
                "Piña",
                "11/10",
                "En el pinche bosque!!",
                "Comprar limones culeros y fumar foco",
                "Experto fumador de focos, no despacha, pero como se divierte limpiando saleros, odia limpiar su tronco y sirve los tacos con carne de hace tres semanas, es experto en comprar los limones mas culeros del mundo ''A veces compra tomates en vez de limones'', tiene un hermano perdido que vende pan.\n" +
                        "Está casado con un pinche enano mion y lo sacan a putazos del pepes, su traje de abejorro panzón es su vestimenta más elegante, le dicen piña y no le gusta la piña al wey.\n" +
                        "Es el fundador de la hora del idiota YA PA QUE TE DIGO!!"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fchapopote.jpg?alt=media&token=81c4fe45-2c28-4be7-a15f-864501be60e9",
                "3",
                "Chapopote",
                "Nivel de idiotez: 100/10",
                "La casa del hielero",
                "Cagar",
                "Este ciudadano Guatemalteco es reconocido por sus multiples aportes a la hora del idiota y por su aparicion en el largometraje ''Mi abuela es un peligro''\n" +
                        "Todas las noches pasa a pagarle a Omar, lo raro es que no lleva dinero.\n" +
                        "Bautizo al pajero loco y le hizo un hijo a la caldera,\n" +
                        "se pone guapa para el gasero,\n" +
                        "por el momento se encuentra encerrado."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fbotas.jpeg?alt=media&token=e656c4d0-d2e0-4256-9e67-d392338373c4",
                "4",
                "El Botas",
                "10/10",
                "La base de taxis",
                "No bañarse",
                "Lo unico que se sabe del botas es que no se baña, no trabaja, aunque haya comida gratis llega tarde, su mejor amigo es el pabro, es hijo del pollero."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fsonrix.jpg?alt=media&token=25db81bb-7bdc-44e4-be41-155b84bf6e91",
                "5",
                "Sonrix",
                "9.5/10",
                "El roma",
                "Sonreirle a la vida",
                "Este idiota solo sonrie aunque este triste.\n" +
                        "9.5  por que está feliz."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Frobocot.jpg?alt=media&token=76150a47-937b-4c72-b6d8-71ffd2a5e133",
                "6",
                "Robocot",
                "9/10",
                "Desconocido",
                "Fumar piedra",
                "El robocot le vende porquerias a los idiotas y los idiotas las compran, cada dia mas acabado por la piedra ya se parece al calambres.\n" +
                        "El goti lo ama y le dice robocot, fundador de la hora de la piedra SEEEE!!!"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Folmecota.jpeg?alt=media&token=fcdaa0eb-b65d-4556-a2b6-197815f5c256",
                "7",
                "Olmecota",
                "1000000/10",
                "El cuarto del piña",
                "Miarse",
                "Este idiota es el padre del olmeca por eso su cabeza es mas grande que su cuerpo, su altura seria normal pero su cabeza lo apachurro, su cabeza le impide ir a miar.\n" +
                        "Esposo del piña y mejor amigo del suicidios."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fpedro.jpeg?alt=media&token=a7ac53e0-6c42-40b3-8c39-f9356694f3c0",
                "8",
                "Pedro",
                "7/10",
                "Los miserables",
                "Violar niños",
                "Este idiota dejo sin caminar al pancholin a cambio de un cinco SEEEE!! "
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fchanpo.jpg?alt=media&token=bcc8086e-7361-40d8-a782-68e2e8749634",
                "9",
                "Pancholin",
                "Incalculable",
                "La tienda del compa",
                "Tragar",
                "Este pinche chango idiota es el idiota mas hambreado y el segundo mas negro de todos, algunos de sus pasatiempos favoritos son: pelar chiles, cromar rifles, hablar con el robocot, subirse con pedro, ir con don estafas, ir con el compa a jugar con las bolas y jalar palancas en su casino, comerse la comida antes de entrar a su casa por que si no se la quitan, violar al piña, desenrollarle la longaniza al pabro en sus intentos de suicido, etc. \n" +
                        "Mataría a su carnal el cabezón por un cinco.\n" +
                        "Le soba el fundillo al chapopote cuando regresa de pagarle a Omar.\n" +
                        "Apodó al pabro como el ojos de panocha de aguela.\n" +
                        "Sus mejores amigos son los hijos de la brenda y su novia es la calderita.\n" +
                        "Sus ojos de perro chillon reflejan maltrato.\n" +
                        "Los ojos le chillan de lo idiota que está.\n" +
                        "La verdadera pregunta es ¿COMO CHINGADOS SE SUBIÓ AHÍ?\n"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fchucho.jpg?alt=media&token=041eac05-2d64-4d80-ad73-19fa85e9e21a",
                "10",
                "Chucho",
                "105/10",
                "El punto",
                "Monearse",
                "Tiene un perro muy idiota se llama pancholin, le gusta monearse con su hijo.\n" +
                        "Pechugas!!"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fperrochucho.jpg?alt=media&token=9797b796-d2a9-4231-a4f1-7b5ec7b11567",
                "11",
                "Perro Chucho",
                "1/10",
                "Donde sea menos con el chucho",
                "Ser marica",
                "Es un perro extremadamente idiota, su nivel de inteligencia es similar al de la concha solo que este gana por poco, este perro idiota seria el idiota con el mayor nivel de idiotez por ser el perro del chucho pero este pinche perro tiene un perro.\n"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fcono.jpg?alt=media&token=5248353e-7b22-4255-a979-97544ca1cd87",
                "12",
                "Coño",
                "10 puntos por kilo",
                "Donde haya comida",
                "Apartar",
                "En la imagen podemos observar a un idiota buscando fierro." +
                        "Solo traga, aparta a las tres de la mañana y se besa con la locutora.\n" +
                        "Pumba belico."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Ftoques.jpg?alt=media&token=8a660a9d-109e-409e-b0be-0ac420234efe",
                "13",
                "Toques",
                "10/10",
                "El puesto",
                "Descomponer cosas",
                "No se sabe nada de este idiota solo que su mejor amigo es el piña, por eso el nivel de idiotez.\n"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fmisterioso.jpg?alt=media&token=c48df941-8631-4b47-b8f9-787e1c461f7a",
                "14",
                "Idiota Misterioso!!",
                "Aumenta cada dia",
                "La tienda de doña huevas",
                "Robarse el suadero",
                "Este idiota se retuerce de lo idiota que esta, esta clase de idiotas se caracteriza por esconder el suadero en sus chamarras.\n" +
                        "Para darse una idea de su nivel de idiotez SUS PROPIOS PERROS SE LO QUIEREN COMER SEEEEEEEEEEEEEEE!!!"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fpajeroloco.jpg?alt=media&token=ef51718a-6eee-44d3-a7ed-99b5b48d5fef",
                "15",
                "Pajero Loco",
                "8/10",
                "Su panaderia 'No sale de ahí'",
                "Hacer pan culero",
                "El hermano perdido del piña, siguen la misma formula: Comprar los ingredientes mas culeros y perder el cuello, el no esta detras de los barrotes pero nunca sale del local SEEE!!\n"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fcompa.jpeg?alt=media&token=c909cf8d-3487-4dc8-b1ee-8c110567563b",
                "16",
                "Compaa compaa!!",
                "9/10",
                "Oaxaca",
                "Vender caro y estafar niños",
                "Este idiota solo sabe decir compaaaaaa, no vende nada y da bien caro.\n" +
                        "Dice ''Compaaa ya llego al casino de las Vegas Nevada'' pero solo tiene tres maquinitas, dos no sirven y la otra roba.\n" +
                        "Estafa a lo niños por eso le roban las cocas." +
                        "No le quiere vender pepsi al suicida."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fmachero.jpg?alt=media&token=86a55198-2fbe-456d-8719-80364f16a9b7",
                "17",
                "El Machero",
                "10/10",
                "Desconocido",
                "Vender machitos podridos",
                "Vende carne podrida, si le pides machitos a las 6 de la tarde llega a las 12 de la noche, amante del suicidios."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fpijsd.jpg?alt=media&token=09e8362b-0760-46d4-857e-26d4ab92a334",
                "18",
                "El Pijas",
                "1000/10",
                "El roma",
                "Tirar el pastorero",
                "Solo tira el pastorero."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fajaa.jpg?alt=media&token=8adc41de-4366-482f-94fa-ac05a3f79fd0",
                "19",
                "El Ajaaa",
                "Extremooo",
                "Ecatepec, Viene diario por un litro de cloro",
                "Decir AJAAAAAAAAA",
                "AJAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fbuenasnoches.jpg?alt=media&token=0b8f94f6-e278-4262-a0c8-22afd6ebd74d",
                "20",
                "El Buenas Noches",
                "100/10",
                "El cielo",
                "Tomarse el activo",
                "Solo decia buenas noches regalame un tacooooo, es el idolo de los idiotas.\n" +
                        "El buenas noches seria el menos idiota de todos los idiotas, pero la causa de su muerte fue tomarse el activo."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fpiezas.jpg?alt=media&token=7a246650-c85b-4554-9da1-eaf1bea7cebb",
                "21",
                "El Piezas",
                "1000/10",
                "Da vueltas vendiendo piezas",
                "Vender piezas",
                "Este pequeño idiota solo vende piezas, parece alumno del piña por que vende puras porquerias SEEEEE!!."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fcacahuates.jpg?alt=media&token=8cfc84de-524e-4d79-9423-6908be6c7ba6",
                "22",
                "El Cacahuates",
                "100/10",
                "El puesto",
                "Caerse donde sea",
                "Este marica vende puras porquerias y siempre se cae al llegar al puesto, mejor amigo de la concha."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fchillon2.jpg?alt=media&token=db403833-b2bd-437b-b2b5-c2f0000e0e05",
                "23",
                "El Chillon",
                "1000/10",
                "Calderolandia",
                "Chillar",
                "Solo se sabe que a su papá le gusta el rifle y su papá es el goti, chilla por todo, el futuro de los caldos en mexico."
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fmisterioso.jpg?alt=media&token=c48df941-8631-4b47-b8f9-787e1c461f7a",
                "24",
                "Idiota Misterioso!!",
                "100000000000/10",
                "El cuarto del Pancholin",
                "Echarse unos caldotes con su novio",
                "Su novio es el pancholin, le regalo sus calzones sucios, vende caldo.\n" +
                        "ADIVINEN QUIEN ES!!"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fmisterioso.jpg?alt=media&token=c48df941-8631-4b47-b8f9-787e1c461f7a",
                "25",
                "Idiota Misterioso!!",
                "10/10",
                "Las cariñosas",
                "Vender gas",
                "Este idiota desconocido es un pinche pelon que vende gas, despues de trabajar pasa borracho por el puesto y se va donde las cariñosas, todas las noches espera al chapopote con la tanguita que tanto le gusta\n" +
                        "A DONDE TAN PEINADO CHAVO?!!"
            )
        )
        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fnono.jpeg?alt=media&token=ae58fa9e-9f46-40bc-8246-d884c0929de9",
                "26",
                "Ñoño",
                "10/10",
                "Desconocido",
                "Desconocido",
                "Lo unico que se sabe de el es que es un idiotaaaa SEEE!!"
            )
        )

        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fvaca.jpg?alt=media&token=c9f4ae73-6fba-42e9-92f6-afba79685b93",
                "27",
                "El vaca",
                "55457/10",
                "El bote",
                "Robar bicicletas",
                "Esté idiota es el mejor amigo del vaca, todas las fotos en la hora del idiota lo confirman, compañero de monas del chucho," +
                        "lider la banda cangrena" +
                        "Le encanta robarse las bicicletas."
            )
        )

        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fmisterioso.jpg?alt=media&token=c48df941-8631-4b47-b8f9-787e1c461f7a",
                "28",
                "Doña huevas",
                "100/10",
                "Sur 14",
                "Vender caro",
                ""
            )
        )

        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Fbisconde.jpeg?alt=media&token=cb4c3c2b-1af3-4901-9a87-ccc2dfc57881",
                "29",
                "El vizconde",
                "100/10",
                "Sur 14",
                "Vender caro",
                ""
            )
        )


        idiotList.add(
            Idiota(
                "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Detail%2Folmeca.jpeg?alt=media&token=6fcf6ca8-1084-446b-b556-87e16d7b5bd5",
                "30",
                "La Concha",
                "∞",
                "El Casino del Compa",
                "Dar cabezazos",
                "Esté idiota a parte de ser el más cabezón también es el más idiota de todos, su nivel de idiotez no miente.\n" +
                        "En sus aportaciones más grandes a la hora del idiota está ''El Pablo es puto y su novio es el Pablo'', su idiotez se refleja en su cara cabra hambreada, Piña y Pablo lo respetan por qué les pone sus cabezazos, en sus tiempos libres es decir todo el día, viola al piña.\n" +
                        "Presenció cómo un mototaxi voló al de las nieves.\n" +
                        "Hace cisternas a cabezazos, cuando se cae hace baches en las calles, un día un taxi lo atropelló y partió el carro a la mitad.\n" +
                        "Come cada vez que existe la oportunidad y le gusta visitar a su papá en el bote.\n" +
                        "Le toma y luego le escupe a la cerveza del piña antes de entregarla.\n" +
                        "Los sonidos que emite este idiota son muy característicos (a veces ladra) y reflejan maltrato y lo idiota que es.\n" +
                        "Grita de emoción cada que llega el robocop.\n" +
                        "Sin duda el idiota más idiota de todos SEEEEEEEEEEEEEEE!!!!." +
                        "Es la hora CONFIRMADOO!!!!\n"

            )
        )
        uploadIdiotListToFirestore(idiotList)

        val adapter = IdiotaAdapter()
        binding.recyclerIdiot.adapter = adapter
        adapter.submitList(idiotList)
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickListener {
            openDetailActivity(it)
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }

        setSupportActionBar(binding.maintoolbar)
        val mainToolbar = binding.maintoolbar
        mainToolbar.setNavigationIcon(R.drawable.baseline_menu_24)
        mainToolbar.setNavigationOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navigationView
        val backgroundColor = ContextCompat.getColor(this, nav2)
        navigationView.setBackgroundColor(backgroundColor)


        navigationView.setNavigationItemSelectedListener { menuItem ->

            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.menu_option1 -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_option2 -> {
                    val intent = Intent(this, PantallaIdiota::class.java)
                    startActivity(intent)
                }

                R.id.menu_option3 -> {
                    val intent = Intent(this, DbIdiotRecycler::class.java)
                    startActivity(intent)
                }

            }
            true
        }


    }

    fun openDetailActivity(earthquake: Idiota) {
        val intent = Intent(this, IDetailActivity::class.java)
        intent.putExtra(IDetailActivity.IDIOT_KEY, earthquake)
        startActivity(intent)

    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun uploadIdiotListToFirestore(idiotList: List<Idiota>) {
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("idiotas")

        GlobalScope.launch(Dispatchers.IO) {
            for (idiota in idiotList) {
                val id = idiota.numeroDeIdiota// Asegúrate de que "id" sea único para cada idiota
                val data = hashMapOf(
                    "imagenUrl" to idiota.imageUrl,
                    "numeroDeIdiota" to idiota.numeroDeIdiota,
                    "nombre" to idiota.nombre,
                    "nivel" to idiota.nivel,
                    "site" to idiota.site,
                    "habilidad" to idiota.habilidadEspecial,
                    "descripcion" to idiota.descripcion

                )

                collectionReference.document(id).set(data)
            }
        }
    }


}





