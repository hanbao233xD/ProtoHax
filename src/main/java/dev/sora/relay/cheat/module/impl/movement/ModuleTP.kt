package dev.sora.relay.cheat.module.impl.movement

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.game.utils.math.MathHelper.cos
import dev.sora.relay.game.utils.math.MathHelper.sin

class ModuleTP : CheatModule("TPFly") {
    private val distanceValue = FloatValue("Distance", 20f, 0f, 50f)
    private val tpHighValue = FloatValue("YHigh", 20f, 0f, 50f)
    override fun onEnable() {
        super.onEnable()
        var pos = Vector3f.from(
            mc.thePlayer.posX + (distanceValue.get() * sin(mc.thePlayer.rotationYaw)),
            (mc.thePlayer.vec3Position.y + tpHighValue.get()).toDouble(),
            mc.thePlayer.posZ + (distanceValue.get() * cos(mc.thePlayer.rotationYaw))
        )
        session.netSession.inboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists
        })
        session.netSession.inboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists
        })
        session.netSession.inboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists
        })
    }
}