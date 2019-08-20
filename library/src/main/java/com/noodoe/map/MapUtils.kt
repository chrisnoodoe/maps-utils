/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.noodoe.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import java.util.*

/** Convert a drawable resource to a Bitmap. */
fun drawableToBitmap(context: Context, @DrawableRes resId: Int): Bitmap {
    return requireNotNull(context.resources.getDrawable(resId, null)).toBitmap()
}

/**
 * Demonstrates converting a [Drawable] to a [BitmapDescriptor],
 * for use as a marker icon.
 */
fun Context.vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
    val vectorDrawable: Drawable =
        ResourcesCompat.getDrawable(resources, id, null)
            ?: return BitmapDescriptorFactory.defaultMarker()
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    DrawableCompat.setTint(vectorDrawable, color)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

/**
 * Returns the drawable resource id for an icon marker, or 0 if no resource with this name exists.
 */
@DrawableRes
fun getDrawableResourceForIcon(context: Context, iconType: String?): Int {
    if (iconType == null) {
        return 0
    }
    return context.resources.getIdentifier(
        iconType.toLowerCase(Locale.US),
        "drawable",
        context.packageName
    )
}

fun GoogleMap.getClassicMarker(context: Context, location: LatLng) {
    val circleOptions: CircleOptions = CircleOptions().apply {
        center(location)
        radius(15.0)
        fillColor(0x32ff6600)
        strokeWidth(0f)
    }
    this.addCircle(circleOptions)

    val markerOptions: MarkerOptions = MarkerOptions().apply {
        icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(context, R.drawable.shape_ring)))
        position(location)
        anchor(0.5f, 0.5f)
    }

    this.addMarker(markerOptions)
}