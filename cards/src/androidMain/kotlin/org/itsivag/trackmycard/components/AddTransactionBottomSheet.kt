@Composable
internal fun TrackMyCardTextInputField(
    label: String,
    value: String,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        singleLine = singleLine,
        value = value,
        onValueChange = { newValue ->
            if (singleLine && newValue.length <= 50) {
                onValueChange(newValue)
            } else if (!singleLine) {
                onValueChange(newValue)
            }
        },
        label = { Text(label, color = Color.White, fontFamily = DmSansFontFamily()) },
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = DmSansFontFamily(),
            fontWeight = FontWeight.Medium
        ),
        maxLines = if (singleLine) 1 else 3,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = focusedColor,
            unfocusedContainerColor = backgroundColor,
            cursorColor = primaryColor
        ),
        placeholder = { Text("Enter title") },
        supportingText = {
            if (singleLine) {
                Text(
                    text = "${value.length}/50",
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = DmSansFontFamily(),
                    fontSize = 12.sp
                )
            }
        },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
} 