package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutInfoDialog(
    onDismiss: () -> Unit,
    isBangla: Boolean
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("about_dialog_close_button")
            ) {
                Text(if (isBangla) "ঠিক আছে" else "Close", fontWeight = FontWeight.Bold)
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = if (isBangla) "CalorieSnap সম্পর্কে" else "About CalorieSnap",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        },
        title = {
            Text(
                text = "CalorieSnap",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = if (isBangla)
                        "CalorieSnap একটি দ্রুত ও সহজ ফুড ক্যালোরি পরিমাপক অ্যাপ। সাধারণ বাঙালি, দক্ষিণ এশীয় এবং জনপ্রিয় আন্তর্জাতিক খাবারের আনুমানিক ক্যালোরি তাৎক্ষণিকভাবে জানুন এবং বন্ধুদের সাথে শেয়ার করুন।"
                    else
                        "CalorieSnap is a fast, lightweight calorie estimator for everyday Bangladeshi, South Asian, and global foods. Estimate portions and share calorie summaries instantly.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                Text(
                    text = if (isBangla) "ডেভেলপার: ViveScript Solutions LLC"
                    else "Developed by: ViveScript Solutions LLC",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "© 2026 ViveScript Solutions LLC.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = if (isBangla) "সোর্স কোড: MIT লাইসেন্স"
                    else "Source code: MIT License",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = if (isBangla) "অ্যাপের নাম, লোগো, আইকন, স্ক্রিনশট, ট্রেডমার্ক এবং সংশ্লিষ্ট ব্র্যান্ডিং ViveScript Solutions LLC-এর স্বত্বাধীন সম্পত্তি এবং MIT লাইসেন্সের আওতাভুক্ত নয়।"
                    else "The app name, logo, icons, screenshots, trademarks, and associated branding are proprietary property of ViveScript Solutions LLC and are not licensed under the MIT License.",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )

                Text(
                    text = if (isBangla) "⚠️ চিকিৎসা দাবিত্যাগ: ক্যালোরির মান শুধুমাত্র দৈনন্দিন রেফারেন্সের জন্য আনুমানিক হিসাব। এটি কোনো চিকিৎসা বা ক্লিনিকাল ডায়েট পরামর্শ প্রদান করে না।"
                    else "⚠️ Medical Disclaimer: Calorie values are approximate estimates intended for everyday reference only. It does not provide medical or clinical diet advice.",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.85f)
                )
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}
