package com.anysoftkeyboard.janus.app.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R

/** Visual connector between Source and Target cards. */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PivotConnector(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    sharedElementKey: String = "shared_icon"
) {
  Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      val modifier =
          if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
              Modifier.sharedElement(
                  sharedContentState = rememberSharedContentState(key = sharedElementKey),
                  animatedVisibilityScope = animatedVisibilityScope)
            }
          } else {
            Modifier
          }
      Image(
          painter = painterResource(R.mipmap.ic_launcher_foreground),
          contentDescription = null,
          colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
          modifier = Modifier.size(48.dp).clip(CircleShape).then(modifier))

      Icon(
          imageVector = Icons.Default.KeyboardDoubleArrowDown,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier =
              Modifier.size(24.dp)
                  .animateInScope(animatedVisibilityScope, enter = fadeIn(), exit = fadeOut()))

      HorizontalDivider(
          color = MaterialTheme.colorScheme.primary,
          modifier =
              Modifier.weight(1f)
                  .animateInScope(
                      animatedVisibilityScope,
                      enter = expandHorizontally(expandFrom = Alignment.Start),
                      exit = shrinkHorizontally(shrinkTowards = Alignment.Start)))
    }
  }
}

private fun Modifier.animateInScope(
    scope: AnimatedVisibilityScope?,
    enter: EnterTransition,
    exit: ExitTransition
): Modifier {
  return if (scope != null) {
    with(scope) { this@animateInScope.animateEnterExit(enter = enter, exit = exit) }
  } else {
    this
  }
}
