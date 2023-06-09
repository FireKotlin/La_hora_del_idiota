package com.example.lahoradelidiota

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lahoradelidiota.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerIdiot.layoutManager =   LinearLayoutManager(this)



        val idiotList = mutableListOf<Idiota>()
        idiotList.add(Idiota(R.drawable.pabro,"1","El Pabro", "Incalculable", "La tienda de doña huevas", "Cagarla", "Este idiota se caracteriza por ser extremadamente idiota y por no tener nariz, su cuerpo no esta diseñado para soportar tanta idiotez y por eso se suicida.\n" +
                "El nivel de idiotez de este especimen es respaldado por sus mas de doce intentos de suicidio, suele ahorcarse con la longaniza y cortarse con corcholatas, el primer intento de la lagartija parada fue despues de cagarse en la express.\n" +
                "Todas las noches pasa a visitar a doña huevas para comprar barritas para cortarse con ellas, antes de cada intento grita SIN MIEDO AL ÉXITO!!, SEEE un idiota ejemplar, YA NO LE SIGO ESCRIBIENDO POR QUE SI NO SE SUICIDA!!!!" ))
        idiotList.add(Idiota(R.drawable.pina, "2","Piña", "11/10", "En el pinche bosque!!", "Comprar limones culeros y fumar foco", "Experto fumador de focos, no despacha, pero como se divierte limpiando saleros, odia limpiar su tronco y sirve los tacos con carne de hace tres semanas, es experto en comprar los limones mas culeros del mundo ''A veces compra tomates en vez de limones'', tiene un hermano perdido que vende pan.\n" +
                "Está casado con un pinche enano mion y lo sacan a putazos del pepes, su traje de abejorro panzón es su vestimenta más elegante, le dicen piña y no le gusta la piña al wey.\n" +
                "Es el fundador de la hora del idiota YA PA QUE TE DIGO!!"))
        idiotList.add(Idiota(R.drawable.chapopote,"3","Chapopote", "Nivel de idiotez: 100/10" , "La casa del hielero", "Cagar", "Este ciudadano Guatemalteco es reconocido por sus multiples aportes a la hora del idiota y por su aparicion en el largometraje ''Mi abuela es un peligro''\n" +
                "Todas las noches pasa a pagarle a Omar, lo raro es que no lleva dinero.\n" +
                "Bautizo al pajero loco y le hizo un hijo a la caldera,\n" +
                "se pone guapa para el gasero,\n" +
                "por el momento se encuentra encerrado."))
        idiotList.add(Idiota(R.drawable.botas,"4","El Botas", "10/10", "La base de taxis", "No bañarse", "Lo unico que se sabe del botas es que no se baña, no trabaja, aunque haya comida gratis llega tarde, su mejor amigo es el pabro, es hijo del pollero."))
        idiotList.add(Idiota(R.drawable.sonrix,"5","Sonrix", "9.5/10", "El roma", "Sonreirle a la vida", "Este idiota solo sonrie aunque este triste.\n" +
                "9.5  por que está feliz."))
        idiotList.add(Idiota(R.drawable.robocot,"6","Robocot", "9/10", "Desconocido", "Fumar piedra", "El robocot le vende porquerias a los idiotas y los idiotas las compran, cada dia mas acabado por la piedra ya se parece al calambres.\n" +
                "El goti lo ama y le dice robocot, fundador de la hora de la piedra SEEEE!!!"))
        idiotList.add(Idiota(R.drawable.thomas,"7","Olmecota", "1000000/10", "El cuarto del piña", "Miarse", "Este idiota es el padre del olmeca por eso su cabeza es mas grande que su cuerpo, su altura seria normal pero su cabeza lo apachurro, su cabeza le impide ir a miar.\n" +
                "Esposo del piña y mejor amigo del suicidios."))
        idiotList.add(Idiota(R.drawable.pedro,"8","Pedro", "7/10", "Los miserables", "Violar niños", "Este idiota dejo sin caminar al pancholin a cambio de un cinco SEEEE!! "))
        idiotList.add(Idiota(R.drawable.chanpo,"9","Pancholin", "Incalculable", "La tienda del compa", "Tragar", "Este pinche chango idiota es el idiota mas hambreado y el segundo mas negro de todos, algunos de sus pasatiempos favoritos son: pelar chiles, cromar rifles, hablar con el robocot, subirse con pedro, ir con don estafas, ir con el compa a jugar con las bolas y jalar palancas en su casino, comerse la comida antes de entrar a su casa por que si no se la quitan, violar al piña, desenrollarle la longaniza al pabro en sus intentos de suicido, etc. \n" +
                "Mataría a su carnal el cabezón por un cinco.\n" +
                "Le soba el fundillo al chapopote cuando regresa de pagarle a Omar.\n" +
                "Apodó al pabro como el ojos de panocha de aguela.\n" +
                "Sus mejores amigos son los hijos de la brenda y su novia es la calderita.\n" +
                "Sus ojos de perro chillon reflejan maltrato.\n" +
                "Los ojos le chillan de lo idiota que está.\n" +
                "La verdadera pregunta es ¿COMO CHINGADOS SE SUBIÓ AHÍ?\n"))
        idiotList.add(Idiota(R.drawable.chucho,"10","Chucho", "105/10", "El punto", "Monearse", "Tiene un perro muy idiota se llama pancholin, le gusta monearse con su hijo.\n" +
                "Pechugas!!"))
        idiotList.add(Idiota(R.drawable.perrochucho,"11","Perro Chucho", "1/10", "Donde sea menos con el chucho", "Ser marica", "Es un perro extremadamente idiota, su nivel de inteligencia es similar al de la concha solo que este gana por poco, este perro idiota seria el idiota con el mayor nivel de idiotez por ser el perro del chucho pero este pinche perro tiene un perro.\n"))
        idiotList.add(Idiota(R.drawable.cono,"12","Coño","10 puntos por kilo", "Donde haya comida", "Apartar", "En la imagen podemos observar a un idiota buscando fierro." +
                "Solo traga, aparta a las tres de la mañana y se besa con la locutora.\n" +
                "Pumba belico."))
        idiotList.add(Idiota(R.drawable.toques,"13","Toques","10/10", "El puesto", "Descomponer cosas", "No se sabe nada de este idiota solo que su mejor amigo es el piña, por eso el nivel de idiotez.\n"))
        idiotList.add(Idiota(R.drawable.misterioso,"14","Idiota Misterioso!!","Aumenta cada dia", "La tienda de doña huevas", "Robarse el suadero", "Este idiota se retuerce de lo idiota que esta, esta clase de idiotas se caracteriza por esconder el suadero en sus chamarras.\n" +
                "Para darse una idea de su nivel de idiotez SUS PROPIOS PERROS SE LO QUIEREN COMER SEEEEEEEEEEEEEEE!!!"))
        idiotList.add(Idiota(R.drawable.pajeroloco,"15","Pajero Loco","8/10", "Su panaderia 'No sale de ahí'", "Hacer pan culero", "El hermano perdido del piña, siguen la misma formula: Comprar los ingredientes mas culeros y perder el cuello, el no esta detras de los barrotes pero nunca sale del local SEEE!!\n"))
        idiotList.add(Idiota(R.drawable.misterioso,"16","Idiota Misterioso!!","9/10", "Oaxaca", "Vender caro y estafar niños", "Este idiota solo sabe decir compaaaaaa, no vende nada y da bien caro.\n" +
                "Dice ''Compaaa ya llego al casino de las Vegas Nevada'' pero solo tiene tres maquinitas, dos no sirven y la otra roba.\n" +
                "Estafa a lo niños por eso le roban las cocas."))
        idiotList.add(Idiota(R.drawable.machero,"17","El Machero", "10/10", "Desconocido", "Vender machitos podridos", "Vende carne podrida, si le pides machitos a las 6 de la tarde llega a las 12 de la noche, amante del suicidios."))
        idiotList.add(Idiota(R.drawable.pijsd,"18","El Pijas", "1000/10", "El roma", "Tirar el pastorero", "Solo tira el pastorero."))
        idiotList.add(Idiota(R.drawable.ajaa,"19","El Ajaaa", "Extremooo", "Ecatepec, Viene diario por un litro de cloro", "Decir AJAAAAAAAAA", "AJAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
        idiotList.add(Idiota(R.drawable.buenasnoches,"20","El Buenas Noches", "100/10", "El cielo", "Tomarse el activo", "Solo decia buenas noches regalame un tacooooo, es el idolo de los idiotas.\n" +
                "El buenas noches seria el menos idiota de todos los idiotas, pero la causa de su muerte fue tomarse el activo."))
        idiotList.add(Idiota(R.drawable.misterioso,"21","Idiota Misterioso!!", "1000/10", "Da vueltas vendiendo piezas", "Vender piezas", "Este pequeño idiota solo vende piezas, adivina quien es SEEEEE!!."))
        idiotList.add(Idiota(R.drawable.cacahuates,"22","El Cacahuates", "100/10", "El puesto", "Caerse donde sea", "Este marica vende puras porquerias y siempre se cae al llegar al puesto, mejor amigo de la concha."))
        idiotList.add(Idiota(R.drawable.chillon2,"23","El Chillon", "1000/10", "Calderolandia", "Chillar", "Solo se sabe que a su papá le gusta el rifle y su papá es el goti, chilla por todo, el futuro de los caldos en mexico."))
        idiotList.add(Idiota(R.drawable.misterioso,"24","Idiota Misterioso!!", "100000000000/10", "El cuarto del Pancholin", "Echarse unos caldotes con su novio", "Su novio es el pancholin, le regalo sus calzones sucios, vende caldo.\n" +
                "ADIVINEN QUIEN ES!!"))
        idiotList.add(Idiota(R.drawable.misterioso,"25","Idiota Misterioso!!", "10/10", "Las cariñosas", "Vender gas", "Este idiota desconocido es un pinche pelon que vende gas, despues de trabajar pasa borracho por el puesto y se va donde las cariñosas, todas las noches espera al chapopote con la tanguita que tanto le gusta\n" +
                "A DONDE TAN PEINADO CHAVO?!!"))
        idiotList.add(Idiota(R.drawable.nono,"26","Ñoño", "10/10", "Desconocido", "Desconocido", "Lo unico que se sabe de el es que es un idiotaaaa SEEE!!"))
        idiotList.add(Idiota(R.drawable.concha,"27","La Concha", "∞", "El Casino del Compa", "Dar cabezazos", "Esté idiota a parte de ser el más cabezón también es el más idiota de todos, su nivel de idiotez no miente.\n" +
                "En sus aportaciones más grandes a la hora del idiota está ''El Pablo es puto y su novio es el Pablo'', su idiotez se refleja en su cara cabra hambreada, Piña y Pablo lo respetan por qué les pone sus cabezazos, en sus tiempos libres es decir todo el día, viola al piña.\n" +
                "Presenció cómo un mototaxi voló al de las nieves.\n" +
                "Hace cisternas a cabezazos, cuando se cae hace baches en las calles, un día un taxi lo atropelló y partió el carro a la mitad.\n" +
                "Come cada vez que existe la oportunidad y le gusta visitar a su papá en el bote.\n" +
                "Le toma y luego le escupe a la cerveza del piña antes de entregarla.\n" +
                "Los sonidos que emite este idiota son muy característicos (a veces ladra) y reflejan lo idiota que es.\n" +
                "Grita de emoción cada que llega el robocop.\n" +
                "Sin duda el idiota más idiota de todos SEEEEEEEEEEEEEEE!!!!.\n"))


        val adapter = IdiotaAdapter()
        binding.recyclerIdiot.adapter = adapter
        adapter.submitList(idiotList)

        adapter.setOnItemClickListener {
            openDetailActivity(it)
        }



        binding.fab.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }



    }
    private fun openDetailActivity(earthquake: Idiota) {
        val intent = Intent(this, IDetailActivity::class.java)
        intent.putExtra(IDetailActivity.IDIOT_KEY, earthquake)
        startActivity(intent)
    }

}