package com.nyx.common.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.adeo.kviewmodel.compose.observeAsState

/**
 * Remember the function obtaining singleton [event] of type [E] without parameters.
 * @sample
 * val onProductActionsClick = viewModel.rememberEvent(ProductCardEvent.OnProductActionsClicked)
 */
@Composable
inline fun <E> BaseViewModel<*, *, E>.rememberEvent(event: E): () -> Unit =
    remember(this) {
        { this.obtainEvent(event) }
    }

/**
 * Remember the function obtaining dynamically created [event] of type [E] with single parameter of type [T].
 * @sample
 * val onBoundProductClick = viewModel.rememberEvent<DetailingInfoType, _> { ProductCardEvent.OnProductDetailingClicked(it) }
 * val onDebugActionClick = viewModel.rememberEvent(ProfileEvent::DebugActionClicked)
 */
@Composable
inline fun <T, E> BaseViewModel<*, *, E>.rememberEvent(crossinline event: @androidx.compose.runtime.DisallowComposableCalls (T) -> E): (T) -> Unit =
    remember(this) {
        { this.obtainEvent(event(it)) }
    }

@Composable
inline fun <T, T2, E> BaseViewModel<*, *, E>.rememberEvent(crossinline event: @androidx.compose.runtime.DisallowComposableCalls (T, T2) -> E): (T, T2) -> Unit =
    remember(this) {
        { p, p1 -> this.obtainEvent(event(p, p1)) }
    }

@Composable
inline fun <T, T2, T3, E> BaseViewModel<*, *, E>.rememberEvent(crossinline event: @androidx.compose.runtime.DisallowComposableCalls (T, T2, T3) -> E): (T, T2, T3) -> Unit =
    remember(this) {
        { p, p1, p2 -> this.obtainEvent(event(p, p1, p2)) }
    }

@Composable
inline fun <A, E> BaseViewModel<*, A, E>.observeAction(
    resetEvent: E,
    crossinline block: suspend (A) -> Unit,
) {
    val viewAction = viewActions().observeAsState()

    LaunchedEffect(viewAction.value) {
        val action = viewAction.value ?: return@LaunchedEffect
        obtainEvent(resetEvent)
        block(action)
    }
}