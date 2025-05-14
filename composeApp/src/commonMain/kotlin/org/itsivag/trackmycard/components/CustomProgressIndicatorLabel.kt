package org.itsivag.trackmycard.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor


@Composable
internal fun CustomProgressIndicatorLabel(label: String, value: String) {
    Text(text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontFamily = DmSansFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = onBackgroundColor
            )
        ) {
            append("$label : ")
        }

        withStyle(
            style = SpanStyle(
                fontFamily = DmSansFontFamily(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = onBackgroundColor
            )
        ) {
            append(value)
        }
    })
}
