package base.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import base.base_entity.Event
import base.base_entity.ResourceState
import io.github.aakira.napier.Napier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

@Composable
fun LoadUrlImage(
    url: String,
    onLoading : @Composable (BoxScope, Float)->Unit = { scope, size ->
        Column(verticalArrangement = Arrangement.Center){
            CircularProgressIndicator(size)
        }
    },
    onFailure : @Composable (Throwable)->Unit = {},
    modifier: Modifier = Modifier
        .fillMaxWidth().height(200f.dp)
        .padding(8.dp)
        .shadow(elevation = 8.dp, RoundedCornerShape(16.dp))
        .background(Color.White, RoundedCornerShape(16.dp))
        .clip(RoundedCornerShape(16.dp))
){
    KamelImage(
        resource = asyncPainterResource(url),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        onLoading = onLoading,
        onFailure = { exception: Throwable ->
            onFailure(exception)
            Napier.d("error load image ${exception.toString()}")
        },
    )
}

@Composable
fun <K> StateFlow<Event<ResourceState<K>>>.collectEventState(): State<ResourceState<K>> {
    return this.mapNotNull{
        it.contentIfNotHandled?.let {
            return@mapNotNull it
        } ?: run{
            return@mapNotNull null
        }
    }.collectAsState(ResourceState(isSuccess = false, isError = false, false))
}

@Composable
fun <K> StateFlow<Event<ResourceState<K>>>.collectResourceState(): ResourceState<K> {
    val data by this.mapNotNull{
        it.mContent?.let {
            return@mapNotNull it
        } ?: run{
            return@mapNotNull null
        }
    }.collectAsState(ResourceState(isSuccess = false, isError = false, false))
    return data
}

data class ValidatorItemControl(
    val isValid: Boolean = true,
    val errorMessage: String = "",
    val value: String = "",
    val onValueChange : (String)->Unit = {},
    val errorChecker : (List<ValidatorItemControl>, String)->String? = {a,b -> null}
)

data class ValidatorFormControl(
    val onSubmit: () -> Unit,
    val state : List<MutableState<ValidatorItemControl>>
)

@Composable
fun ComposeValidatorForm(
    vararg stateValidations: ValidatorItemControl,
    onValid: (List<ValidatorItemControl>) -> Unit
): ValidatorFormControl {

    var dataState : ValidatorFormControl? = null

    fun onUpdateValue(value: String, state: MutableState<ValidatorItemControl>) {
        state.value = state.value.copy(value = value)
    }

    val formStateList: ArrayList<MutableState<ValidatorItemControl>> =
        arrayListOf<MutableState<ValidatorItemControl>>().apply {
            stateValidations.forEach {
                val state = mutableStateOf(it)
                val valueChange = { value: String ->
                    onUpdateValue(value, state)
                }
                state.value = state.value.copy(onValueChange = valueChange)
                this.add(state)
            }
        }

    val onSubmit = {
        var isValid = true
        stateValidations.forEachIndexed { index, validator ->
            val current = formStateList[index].value
            val message = validator.errorChecker(
                dataState?.state?.map { it.value } ?: listOf(),
                current.value
            )
            isValid = message.isNullOrBlank() == true
            formStateList[index].value = current.copy(
                isValid, message ?: ""
            )
        }
        if (isValid) onValid(formStateList.map { it.value })
    }

    dataState = ValidatorFormControl(onSubmit, formStateList)
    return remember { dataState }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberTeaBottomSheet(): ModalBottomSheetState {
    return rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )
}

fun CoroutineScope.go(
    dispatch: CoroutineDispatcher = Dispatchers.Main,
    onDo: suspend ()->Unit
){
    launch(dispatch){
        onDo()
    }
}