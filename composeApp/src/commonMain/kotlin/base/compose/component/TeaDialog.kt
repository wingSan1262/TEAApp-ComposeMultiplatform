package base.compose.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun TeaDialog(
    title: String = "",
    message: String = "",
    isNotifyOnly: Boolean = false,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = {
                    onConfirm()
                    showDialog.value = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                if(isNotifyOnly) return@AlertDialog
                Button(onClick = {
                    onCancel()
                    showDialog.value = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}
