package mrandroid.league.util.state


sealed class ApiState<T>(val data: T? = null, val message: UiText? = null) {
    class Success<T>(data: T?) : ApiState<T>(data = data)
    class Error<T>(message: UiText) : ApiState<T>(message = message)
    class Loading<T>(data: T? = null) : ApiState<T>(data)
}
