package com.devrex.networkapplication.domain.model

import com.devrex.networkapplication.data.dto.Genre
import com.devrex.networkapplication.data.dto.ProductionCompany

data class MovieDetailsModel(
    val backDropImageUrl: String? = "",
    var posterImageUrl: String? = "",
    var title: String? = "",
    var releaseDate: String? = "",
    val overView: String? = "",
    val rating: Float? = 0F,
    var genres: List<Genre?>? = null,
    val productionCompanyList: List<ProductionCompany?>? = null,
)
