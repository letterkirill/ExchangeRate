package com.example.exchange_rate.data.network

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(strict = false, name = "Valute")
class ValutaData(
    @field:Attribute(name = "ID")
    var id: String = "",
    @field:Element(name = "Value")
    var valueRate: String = ""
)