package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.BoolValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.event.Listen
import dev.sora.relay.utils.RandomUtils

class ModuleAntiAim : CheatModule("AntiAim","大陀螺") {
    private val yawModeValue = ListValue("YawMove", arrayOf("Jitter", "Spin", "Back", "BackJitter"), "BackJitter")
    private val pitchModeValue = ListValue("PitchMode", arrayOf("Down", "Up", "Jitter", "AnotherJitter"), "Up")
    private val rotateValue = BoolValue("SilentRotate", true)

    private var yaw = 0f
    private var pitch = 0f

    override fun onEnable() {
        super.onEnable()
        if(rotateValue.get()) chat("注意!只有敌人视角才能看得见你的转头!")
    }
    @Listen
    fun onTick(event: EventTick) {
        when (yawModeValue.get().lowercase()) {
            "spin" -> {
                yaw += 20.0f
                if (yaw > 180.0f) {
                    yaw = -180.0f
                } else if (yaw < -180.0f) {
                    yaw = 180.0f
                }
            }
            "jitter" -> {
                yaw = mc.thePlayer.rotationYaw + if (mc.thePlayer.tickExists % 2 == 0L) 90F else -90F
            }
            "back" -> {
                yaw = mc.thePlayer.rotationYaw + 180f
            }
            "backjitter" -> {
                yaw = mc.thePlayer.rotationYaw + 180f + RandomUtils.nextDouble(-3.0, 3.0).toFloat()
            }
        }

        when (pitchModeValue.get().lowercase()) {
            "up" -> {
                pitch = -90.0f
            }
            "down" -> {
                pitch = 90.0f
            }
            "anotherjitter" -> {
                pitch = 60f + RandomUtils.nextDouble(-3.0, 3.0).toFloat()
            }
            "jitter" -> {
                pitch += 30.0f
                if (pitch > 90.0f) {
                    pitch = -90.0f
                } else if (pitch < -90.0f) {
                    pitch = 90.0f
                }
            }
        }

        rotations = Pair(yaw, pitch)
        if (!rotateValue.get()) {
            session.netSession.inboundPacket(MovePlayerPacket().apply {
                runtimeEntityId = mc.thePlayer.entityId
                position = mc.thePlayer.vec3Position
                rotation = Vector3f.from(rotations!!.second, rotations!!.first, rotations!!.first)
                mode = MovePlayerPacket.Mode.HEAD_ROTATION
                isOnGround = (mc.thePlayer.motionY==0.0)
                ridingRuntimeEntityId = 0
                entityType = 0
                tick = mc.thePlayer.tickExists
            })
        }
    }

    private var rotations: Pair<Float, Float>? = null
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        val rotation = rotations ?: return
        val packet = event.packet

        if (packet is PlayerAuthInputPacket) {
            packet.rotation = Vector3f.from(rotation.first, rotation.second, packet.rotation.z)
            this.rotations = null
        } else if (packet is MovePlayerPacket) {
            packet.rotation = Vector3f.from(rotation.first, rotation.second, packet.rotation.z)
            this.rotations = null
        }
    }
}