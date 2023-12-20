package base.base_classes

import base.base_entity.Event
import base.base_entity.ResourceState
import base.base_entity.TeaRemoteResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

open class BaseUseCase<PARAM, RESULT> : CoroutineScope {

    /** UseCase Original Response LiveData**/
    private val _currentData = MutableStateFlow<Event<ResourceState<RESULT>>>(
        Event(ResourceState(false, false, false,))
    )
    val currentData : StateFlow<Event<ResourceState<RESULT>>> = _currentData

    /** Job Context Scope **/
    protected var job : Job? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    /** Execute UseCase api or process **/
    fun execute(runApi : suspend () -> RESULT){
        launch(coroutineContext){
            withContext(Dispatchers.Main){
                _currentData.value = Event(ResourceState(
                    false, false, true))
            }
            val res  : ResourceState<RESULT> = try {
                val data = runApi()
                when{
                    data != null && data is TeaRemoteResponse<*> ->
                        ResourceState(
                            data.status_code in 200..299,
                            !(data.status_code in 200..299),
                            false,
                            data,
                            code = data.status_code,
                            if(data.status_code in 200..299) null else Throwable(data.message)
                        )
                    data != null -> ResourceState(true, false, false, data)
                    else ->
                        ResourceState(
                            false, true,false, data, 500,
                            Throwable("Data null")
                        )
                }
            } catch (e : Throwable){
                ResourceState(false, true, false,null, 500, e)
            }
            withContext(Dispatchers.Main){
                _currentData.value = (Event(res))
            }
        }
    }

    /** Open function for user class to varies the execute call **/
    open fun setup(parameter: PARAM){ checkJob() }
    /** Common Job Control**/
    fun cancel() {
        job?.cancel()
    }
    fun isCancelled(): Boolean {
        return job?.isCancelled ?: true
    }
    fun checkJob(){
        job?.let{
            if(it.isCancelled)
                job = Job()
        }
    }
}