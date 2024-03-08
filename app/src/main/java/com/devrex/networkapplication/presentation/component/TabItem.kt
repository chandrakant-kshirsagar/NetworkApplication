package com.devrex.networkapplication.presentation.component

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unSelectedIcon: ImageVector? = null,
)
