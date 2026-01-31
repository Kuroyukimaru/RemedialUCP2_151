package com.example.remedialucp2_151.view.route

import com.example.remedialucp2_151.R

object DetailBook : DestinasiNavigasi {
    override val route = "detail_book"
    override val titleRes = R.string.detail_book_title
    const val itemIdArg = "idBook"
    val routeWithArgs = "$route/{$itemIdArg}"
}