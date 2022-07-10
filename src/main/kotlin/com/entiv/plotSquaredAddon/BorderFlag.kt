package com.entiv.plotSquaredAddon

import com.plotsquared.core.configuration.caption.TranslatableCaption
import com.plotsquared.core.plot.flag.InternalFlag
import com.plotsquared.core.plot.flag.types.NonNegativeIntegerFlag

class BorderFlag(value: Int) : NonNegativeIntegerFlag<BorderFlag>(
    value,
    TranslatableCaption.of("flags.flag_description_border"),
), InternalFlag {

    override fun flagOf(value: Int): BorderFlag {
        return BorderFlag(value)
    }

    companion object {
        val BORDER_SIZE_DEFAULT = BorderFlag(50)
   }
}
