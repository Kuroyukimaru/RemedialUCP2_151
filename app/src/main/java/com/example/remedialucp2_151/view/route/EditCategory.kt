package com.example.remedialucp2_151.view.route

import com.example.remedialucp2_151.R

object EditCategory : DestinasiNavigasi {
    override val route = "edit_category"
    override val titleRes = R.string.edit_category_title
    const val itemIdArg = "idCategory"
    val routeWithArgs = "$route/{$itemIdArg}"
}