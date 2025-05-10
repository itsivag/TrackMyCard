package com.itsivag.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import trackmycard.helper.generated.resources.Res
import trackmycard.helper.generated.resources.dm_sans_variable
import trackmycard.helper.generated.resources.onest_variable


@Composable
fun DmSansFontFamily() = FontFamily(Font(Res.font.dm_sans_variable))

@Composable
fun OnestFontFamily() = FontFamily(Font(Res.font.onest_variable))
