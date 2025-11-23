package com.anysoftkeyboard.janus.app.ui.components

import android.graphics.PathMeasure
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.PathParser
import com.anysoftkeyboard.janus.app.R

// The path data from docs/loader_path.svg
private const val LOADER_PATH_DATA =
    "M 218.00,126.00 C 218.00,126.00 231.33,130.00 231.33,130.00 231.33,130.00 243.33,138.67 243.33,138.67 243.33,138.67 251.33,148.00 251.33,148.00 251.33,148.00 254.67,161.33 254.67,161.33 254.67,161.33 253.33,178.67 253.33,178.67 253.33,178.67 252.00,192.00 252.00,192.00 252.00,192.00 247.33,206.00 247.33,206.00 247.33,206.00 243.33,217.33 243.33,217.33 243.33,217.33 239.33,226.67 239.33,226.67 239.33,226.67 234.67,235.33 234.67,235.33 234.67,235.33 225.33,246.67 225.33,246.67 225.33,246.67 204.00,274.00 204.00,274.00 204.00,274.00 197.33,280.00 197.33,280.00 197.33,280.00 187.33,286.00 187.33,286.00 187.33,286.00 174.67,290.67 174.67,290.67 174.67,290.67 160.67,291.33 160.67,291.33 160.67,291.33 148.00,288.67 148.00,288.67 148.00,288.67 137.33,280.00 137.33,280.00 137.33,280.00 128.67,269.33 128.67,269.33 128.67,269.33 124.00,255.33 124.00,255.33 124.00,255.33 124.00,240.67 124.00,240.67 124.00,240.67 129.33,230.67 129.33,230.67 129.33,230.67 140.00,223.33 140.00,223.33 140.00,223.33 151.33,216.67 151.33,216.67 151.33,216.67 164.00,211.33 164.00,211.33 164.00,211.33 176.67,206.00 176.67,206.00 176.67,206.00 190.00,203.33 190.00,203.33 190.00,203.33 203.33,200.67 203.33,200.67 203.33,200.67 216.00,199.33 216.00,199.33 216.00,199.33 231.33,200.00 231.33,200.00 231.33,200.00 265.33,207.33 265.33,207.33 265.33,207.33 276.67,212.00 276.67,212.00 276.67,212.00 286.67,218.00 286.67,218.00 286.67,218.00 296.00,226.67 296.00,226.67 296.00,226.67 302.67,235.33 302.67,235.33 302.67,235.33 306.67,246.67 306.67,246.67 306.67,246.67 305.33,262.00 305.33,262.00 305.33,262.00 300.00,272.00 300.00,272.00 300.00,272.00 291.33,284.67 291.33,284.67 291.33,284.67 280.67,288.00 280.67,288.00 280.67,288.00 266.00,290.00 266.00,290.00 266.00,290.00 252.67,290.00 252.67,290.00 252.67,290.00 238.67,284.00 238.67,284.00 238.67,284.00 230.00,274.67 230.00,274.67 230.00,274.67 223.33,267.33 223.33,267.33 223.33,267.33 211.33,255.33 211.33,255.33 211.33,255.33 204.67,246.67 204.67,246.67 204.67,246.67 198.00,235.33 198.00,235.33 198.00,235.33 190.00,220.00 190.00,220.00 190.00,220.00 178.00,186.00 178.00,186.00 178.00,186.00 177.33,172.67 177.33,172.67 177.33,172.67 178.00,158.67 178.00,158.67 178.00,158.67 180.67,146.00 180.67,146.00 180.67,146.00 188.67,138.00 188.67,138.00 188.67,138.00 202.00,130.00 202.00,130.00"

// The Gold Color requested
private val GoldColor = Color(0xFFFFD700)

private val NormalizedPathPoints: List<Offset> by lazy {
  val path = PathParser.createPathFromPathData(LOADER_PATH_DATA)
  val pathMeasure = PathMeasure(path, false)
  val length = pathMeasure.length
  val pointsCount = 1000
  val points = ArrayList<Offset>(pointsCount)
  val pos = floatArrayOf(0f, 0f)
  val tan = floatArrayOf(0f, 0f)
  for (i in 0 until pointsCount) {
    val distance = (i.toFloat() / pointsCount) * length
    pathMeasure.getPosTan(distance, pos, tan)
    // Normalize by viewport size 432x432
    points.add(Offset(pos[0] / 432f, pos[1] / 432f))
  }
  points
}

@Composable
fun JanusLoader(modifier: Modifier = Modifier, durationMillis: Int = 1000) {
  // 1. Define the Animation
  val infiniteTransition = rememberInfiniteTransition(label = "JanusLoaderAnimation")
  val progress by
      infiniteTransition.animateFloat(
          initialValue = 0f,
          targetValue = 1f,
          animationSpec =
              infiniteRepeatable(
                  animation = tween(durationMillis, easing = LinearEasing),
                  repeatMode = RepeatMode.Restart),
          label = "Progress")

  // 2. Density for converting dp to px
  val density = LocalDensity.current
  val dotRadiusPx = with(density) { 4.dp.toPx() }

  Box(modifier = modifier) {
    // Background Image
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Fit)

    // 3. Draw Content
    Canvas(modifier = Modifier.fillMaxSize()) {
      val points = NormalizedPathPoints
      val index = (progress * (points.size - 1)).toInt().coerceIn(0, points.size - 1)
      val normalizedPoint = points[index]

      val dotPosition =
          Offset(x = normalizedPoint.x * size.width, y = normalizedPoint.y * size.height)

      // Draw the Traveling Dot
      drawCircle(color = GoldColor, radius = dotRadiusPx, center = dotPosition)
    }
  }
}
