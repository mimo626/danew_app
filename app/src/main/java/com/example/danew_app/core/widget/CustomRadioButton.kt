package com.example.danew_app.core.widget

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.danew_app.core.theme.ColorsLight

@Composable
fun CustomRadioButton(text:String, selected:Boolean, onCLick: () -> Unit){
    RadioButton(
        selected = selected,
        onClick =  onCLick,
        colors = RadioButtonColors(
            selectedColor = ColorsLight.primaryColor,
            unselectedColor = ColorsLight.grayColor,
            disabledSelectedColor = ColorsLight.grayColor,
            disabledUnselectedColor = ColorsLight.grayColor,)

    )
    Text(text)
}