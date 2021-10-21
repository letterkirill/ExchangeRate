package com.example.exchange_rate.data.network

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "ValCurs")
class ValutaRates(
    @field:ElementList(inline = true)
    var valutasList: List<ValutaData> = ArrayList()){

    fun toValue (id: String): Float{

        val valuta = valutasList.find{ it.id == id}
        valuta?.valueRate?.replace(",", ".")?.toFloat()?.let {
            return it
        }
        return 0f
    }
}