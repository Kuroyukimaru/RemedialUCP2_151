package com.example.remedialucp2_151.view.route

import com.example.remedialucp2_151.R

object DetailCategory : DestinasiNavigasi {
    override val route = "detail_category"
    override val titleRes = R.string.detail_category_title
    const val itemIdArg = "idCategory"
    val routeWithArgs = "$route/{$itemIdArg}"
}