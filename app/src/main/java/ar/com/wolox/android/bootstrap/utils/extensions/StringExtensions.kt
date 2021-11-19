package ar.com.wolox.android.bootstrap.utils.extensions

const val LINE_BREAK = "\n"
const val BLANK_SPACE = " "

fun String.removeLineBreaks() = this.replace(LINE_BREAK, BLANK_SPACE)
