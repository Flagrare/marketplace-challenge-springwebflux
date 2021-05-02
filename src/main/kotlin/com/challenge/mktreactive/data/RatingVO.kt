package com.challenge.mktreactive.data

import com.challenge.mktreactive.entity.Rating
import com.challenge.mktreactive.entity.Sale

class RatingVO(
    val rate: Int,
    val sale: Sale
) {
    companion object {
        fun fromEntity(rating: Rating): RatingVO =
            RatingVO(
                rate = rating.rate,
                sale = rating.sale
            )
    }
}

