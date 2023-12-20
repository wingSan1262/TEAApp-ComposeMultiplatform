package features.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import base.compose.colorPrimary

@Composable
fun PartialClickableText(
    text: String,
    clickableText: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        // Normal text
        append(text)

        // Clickable text with a different style
        pushStringAnnotation(
            tag = "Clickable",
            annotation = clickableText
        )
        withStyle(
            style = SpanStyle(
                color = colorPrimary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(clickableText)
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "Clickable", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    if (annotation.item == clickableText) {
                        onClick()
                    }
                }
        },
        style = MaterialTheme.typography.body1.copy(
            textAlign = TextAlign.Center),
        modifier = modifier
    )
}