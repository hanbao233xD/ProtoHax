package dev.sora.relay.cheat.module

import dev.sora.relay.cheat.BasicThing
import dev.sora.relay.cheat.value.Value
import dev.sora.relay.game.event.Listener
import lombok.Getter

abstract class CheatModule(val name: String,val chinese: String = name,
                           val defaultOn: Boolean = false,
                           val canToggle: Boolean = true) : BasicThing(), Listener {
    @Getter
    var values : String = ""
    var state = defaultOn
        set(state) {
            if (field == state) return

            if (!canToggle) {
                onEnable()
                return
            }
            field = state

            if (state) {
                onEnable()
            } else {
                onDisable()
            }
        }

    val direction: Double
        get() {
            var rotationYaw = mc.thePlayer.rotationYaw
            if (mc.thePlayer.moveForward < 0f) rotationYaw += 180f
            var forward = 1f
            if (mc.thePlayer.moveForward < 0f) forward = -0.5f else if (mc.thePlayer.moveForward > 0f) forward = 0.5f
            if (mc.thePlayer.moveStrafing > 0f) rotationYaw -= 90f * forward
            if (mc.thePlayer.moveStrafing < 0f) rotationYaw += 90f * forward
            return Math.toRadians(rotationYaw.toDouble())
        }
    open fun onEnable() {}

    open fun onDisable() {}

    open fun toggle() {
        this.state = !this.state
    }

    fun getValues() = javaClass.declaredFields.map { field ->
        field.isAccessible = true
        field.get(this)
    }.filterIsInstance<Value<*>>()

    fun getValue(valueName: String) = this.getValues().find { it.name.equals(valueName, ignoreCase = true) }

    override fun listen() = state
}