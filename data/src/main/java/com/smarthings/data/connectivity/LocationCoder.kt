package com.smarthings.data.connectivity

interface LocationCoder {
    fun search(location: String): Location
}
