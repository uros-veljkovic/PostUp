package com.urkeev14.myapplication.utils.extensions

import java.util.Locale

/**
 * Capitalizes first letter of a given [String]
 *
 * @return capitalized [String]
 */
fun String.capitalized(): String = replaceFirstChar { firstChar ->
    if (firstChar.isLowerCase())
        firstChar.titlecase(Locale.getDefault())
    else
        firstChar.toString()
}
