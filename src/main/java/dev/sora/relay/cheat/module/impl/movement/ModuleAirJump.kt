package dev.sora.relay.cheat.module.impl.movement

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData
import com.nukkitx.protocol.bedrock.packet.*
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.utils.movement.MovementUtils
import kotlin.math.cos
import kotlin.math.sin

class ModuleAirJump : CheatModule("AirJump") {
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if (event.packet is PlayerAuthInputPacket && MovementUtils.isMoving(mc) && mc.thePlayer.inputData.contains(PlayerAuthInputData.JUMPING)) strafe(
            0.38f,
            0.35f
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