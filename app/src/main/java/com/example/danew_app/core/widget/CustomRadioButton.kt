package com.example.danew_app.core.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.danew_app.core.theme.DanewColors

@Composable
fun CustomRadioButton(text:String, selected:Boolean, onCLick: () -> Unit){
    RadioButton(
        selected = selected,
        onClick =  onCLick,
        colors = RadioButtonColors(
            selectedColor = DanewColors.blueColor,
            unselectedColor = MaterialTheme.colorScheme.onSurface,
            disabledSelectedColor = MaterialTheme.colorScheme.onSurface,
            disabledUnselectedColor = MaterialTheme.colorScheme.onSurface,)

    )
    Text(text)
}