package com.anysoftkeyboard.janus.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.anysoftkeyboard.janus.app.R

/**
 * Search text input field with search and clear actions.
 *
 * Features:
 * - Search icon as leading icon
 * - Clear button when text is not empty
 * - Search keyboard action to trigger search
 *
 * @param text Current search text value
 * @param onTextChange Callback when text changes
 * @param onSearch Callback when search action is triggered
 * @param label Label text for the input field
 */
@Composable
fun SearchInputField(
    text: String,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    label: String = stringResource(R.string.search_input_label),
) {
  OutlinedTextField(
      value = text,
      onValueChange = onTextChange,
      label = { Text(label) },
      leadingIcon = {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.content_description_search),
        )
      },
      trailingIcon = {
        if (text.isNotEmpty()) {
          IconButton(onClick = { onTextChange("") }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.content_description_clear),
            )
          }
        }
      },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
      keyboardActions = KeyboardActions(onSearch = { onSearch() }),
      singleLine = true,
      modifier = Modifier.fillMaxWidth().testTag("search_box"),
  )
}
