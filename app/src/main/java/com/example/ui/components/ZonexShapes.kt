package com.example.ui.components

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

fun chamferedShape(cutSize: Dp = 10.dp): GenericShape = GenericShape { size: Size, _ ->
    val cut = cutSize.value * 2.5f
    val w = size.width
    val h = size.height

    moveTo(cut, 0f)
    lineTo(w - cut, 0f)
    lineTo(w, cut)
    lineTo(w, h - cut)
    lineTo(w - cut, h)
    lineTo(cut, h)
    lineTo(0f, h - cut)
    lineTo(0f, cut)
    close()
}

val HexagonShape = GenericShape { size: Size, _ ->
    val w = size.width
    val h = size.height
    val side = min(w, h)
    val r = side / 2f
    val cx = w / 2f
    val cy = h / 2f

    moveTo(cx, cy - r)
    lineTo(cx + r * 0.866f, cy - r * 0.5f)
    lineTo(cx + r * 0.866f, cy + r * 0.5f)
    lineTo(cx, cy + r)
    lineTo(cx - r * 0.866f, cy + r * 0.5f)
    lineTo(cx - r * 0.866f, cy - r * 0.5f)
    close()
}

val HexagonFlatTopShape = GenericShape { size: Size, _ ->
    val w = size.width
    val h = size.height
    val r = min(w, h) / 2f
    val cx = w / 2f
    val cy = h / 2f

    val cos30 = 0.866f
    moveTo(cx - r * 0.5f, cy - r * cos30)
    lineTo(cx + r * 0.5f, cy - r * cos30)
    lineTo(cx + r, cy)
    lineTo(cx + r * 0.5f, cy + r * cos30)
    lineTo(cx - r * 0.5f, cy + r * cos30)
    lineTo(cx - r, cy)
    close()
}
