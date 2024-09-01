package com.example.weatherforcastapp.data.mappers

interface ApiMapper <Domain, Entity> {
    fun maptoDomain ( apiEntity: Entity ) : Domain
}