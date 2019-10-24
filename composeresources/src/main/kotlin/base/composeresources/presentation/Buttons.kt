package base.composeresources.presentation

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.*
import androidx.ui.material.surface.*

@Composable fun ModifiableButton(
    onClick: (() -> Unit)? = null,
    style: ButtonStyle = ContainedButtonStyle(),
    modifier: Modifier = Modifier.None,
    padding: EdgeInsets = style.paddings,
    children: @Composable() () -> Unit
) {
    Surface(style.shape, style.color, style.border, style.elevation) {
        Ripple(bounded = true, color = style.rippleColor,
            enabled = onClick != null) {
            Clickable(onClick = onClick) {
                Container(padding = padding, modifier = modifier) {
                    CurrentTextStyleProvider(
                        value = +themeTextStyle {
                            button.merge(style.textStyle)
                        },
                        children = children
                    )
                }
            }
        }
    }
}