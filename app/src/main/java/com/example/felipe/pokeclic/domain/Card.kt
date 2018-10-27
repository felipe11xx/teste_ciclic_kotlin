package com.example.felipe.pokeclic.domain

class Card(
    val id: String,
    val name: String,
    val nationalPokedexNumber: Int,
    val imageUrl: String,
    val imageUrlHiRes: String,
    val types: List<String>,
    val supertype: String,
    val subtype: String,
    val evolvesFrom: String,
    val Ability: Ability,
    val hp: String,
    val retreatCoast: List<String>,
    val number: String,
    val convertedRetreatCoaast: Int,
    val artist: String,
    val rarity: String,
    val series: String,
    val set: String,
    val setCod: String,
    val text :List<String>,
    val attacks: List<Attacks>,
    val weaknesses: List<Weaknesses>

) {
}