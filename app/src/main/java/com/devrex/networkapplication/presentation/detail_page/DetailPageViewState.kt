package com.devrex.networkapplication.presentation.detail_page

import com.devrex.networkapplication.domain.model.MovieDetailsModel


data class DetailPageViewState(
    val movieDetailsModel: MovieDetailsModel? = null,
    val showProgress: Boolean = true,
) {
    val generaList = {
            var list = ""
            if (movieDetailsModel?.genres.isNullOrEmpty().not()) {

                val count = movieDetailsModel?.genres?.size
                movieDetailsModel?.genres?.forEachIndexed { index, genre ->
                     if (genre?.name.isNullOrEmpty().not()) {
                         list += "${genre?.name}"
                         list +=  if (count != index) ", " else ""
                    } else {
                         list += ""
                    }
                }
            }
              list
        }


}
