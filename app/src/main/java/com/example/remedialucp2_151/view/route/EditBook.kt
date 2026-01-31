package com.example.remedialucp2_151.view.route

import com.example.remedialucp2_151.R

object EditBook : DestinasiNavigasi {
    override val route = "edit_book"
    override val titleRes = R.string.edit_book_title
    const val itemIdArg = "idBook"
    val routeWithArgs = "$route/{$itemIdArg}"
}