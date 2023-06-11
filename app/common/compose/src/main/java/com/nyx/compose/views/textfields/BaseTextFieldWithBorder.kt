package com.nyx.compose.views.textfields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseTextFieldWithBorder(
    modifier: Modifier = Modifier,
    message: String,
    placeholder: String,
    focusRequester: FocusRequester,
    textStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontFamily = FontFamily.SansSerif,
        fontSize = 14.sp
    ),
    cursorColor: Color = Color.Black,
    fieldColor: Color = Color.White,
    minLines: Int = 4,
    maxLines: Int = 8,
    isEnabled: Boolean = true,
    isSingleLine: Boolean = false,
    contentPaddings: PaddingValues = PaddingValues(
        start = 16.dp,
        end = 34.dp,
        top = 16.dp,
        bottom = 16.dp
    ),
    onTextChanged: (String) -> Unit,
    onClearInputClick: () -> Unit,
) {
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = message,
                selection = TextRange(message.length)
            )
        )
    }
    val input = textFieldValueState.copy(text = message)

    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = input,
            onValueChange = {
                textFieldValueState = it
                onTextChanged(it.text)
            },
            modifier = modifier
                .fillMaxSize()
                .background(fieldColor)
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused }
                .border(
                    border = if (isFocused) BorderStroke(width = 2.dp, color = Color.Black) else
                        BorderStroke(width = 1.dp, color = Color.LightGray),
                    shape = RoundedCornerShape(4.dp)
                ),
            minLines = minLines,
            maxLines = maxLines,
            textStyle = textStyle,
            cursorBrush = SolidColor(cursorColor),
            singleLine = isSingleLine
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = "",
                placeholder = {
                    if (input.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.LightGray,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )
                    }
                },
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = isSingleLine,
                enabled = isEnabled,
                interactionSource = interactionSource,
                contentPadding = contentPaddings,
            )
        }
        if (message.isNotBlank() && isFocused) {
            Image(
                modifier = if (isSingleLine) {
                    Modifier.align(Alignment.CenterEnd)
                } else {
                    Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.TopEnd)
                }
                    .padding(end = 8.dp)
                    .clickable(onClick = onClearInputClick),
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    }
}