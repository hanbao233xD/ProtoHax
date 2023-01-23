package dev.sora.relay.cheat.module.impl.movement

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.BoolValue
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.utils.movement.MovementUtils
import kotlin.math.cos
import kotlin.math.sin

class ModuleAirJump : CheatModule("AirJump","踏空") {

    private val onlyMoveValue = BoolValue("OnlyMove", true)
    private val yHighValue = FloatValue("YHigh", 0.35f, 0f, 2f)
    private val speedValue = FloatValue("Speed", 0.38f, 0f, 1f)

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if (onlyMoveValue.get()) {
            if (event.packet is PlayerAuthInputPacket && MovementUtils.isMoving(mc) && mc.thePlayer.inputData.contains(
                    PlayerAuthInputData.JUMPING
                )
            ) strafe(
                speedValue.get(),
                yHighValue.get()
            )
        } else if (event.packet is PlayerAuthInputPacket && mc.thePlayer.inputData.contains(PlayerAuthInputData.JUMPING)) strafe(
            speedValue.get(),
            yHighValue.get()
        )
    }

    private fun strafe(speed: Float, motionY: Float) {
        val yaw = direction
        session.netSession.inboundPacket(SetEntityMotionPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            motion = Vector3f.from(-sin(yaw) * speed, motionY.toDouble(), cos(yaw) * speed)
        })
    }

}