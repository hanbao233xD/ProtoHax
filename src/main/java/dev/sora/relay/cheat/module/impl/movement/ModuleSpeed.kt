package dev.sora.relay.cheat.module.impl.movement

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.impl.EventTick
import dev.sora.relay.game.utils.movement.MovementUtils.isMoving
import kotlin.math.cos
import kotlin.math.sin

class ModuleSpeed : CheatModule("Speed")  {
    private val modeValue = ListValue("Mode", arrayOf("Hop","LowHop" ,"Legit"), "Legit")
    private val speedValue = FloatValue("HopSpeed" ,0.29f,0f,2f)
    @Listen
    fun onTick(event: EventTick) {
        when(modeValue.get().lowercase()){
            "legit"->{
                if(mc.thePlayer.motionY==0.0 && isMoving(mc)){
                    mc.thePlayer.jump(session)
                }
            }
            "hop"->{
                strafe(speedValue.get())
            }
            "LowHop"->{
                if(isMoving(mc) && mc.thePlayer.onGround) strafe(0.38f,if(mc.thePlayer.inputData.contains(
                        PlayerAuthInputData.JUMPING)) 0.38f else if(mc.thePlayer.inputData.contains(
                        PlayerAuthInputData.SNEAKING)) -0.38f else 0.02f)
            }
        }
    }
    private val direction: Double
        get() {
            var rotationYaw = mc.thePlayer.rotationYaw
            if (mc.thePlayer.moveForward < 0f) rotationYaw += 180f
            var forward = 1f
            if (mc.thePlayer.moveForward < 0f) forward = -0.5f else if (mc.thePlayer.moveForward > 0f) forward = 0.5f
            if (mc.thePlayer.moveStrafing > 0f) rotationYaw -= 90f * forward
            if (mc.thePlayer.moveStrafing < 0f) rotationYaw += 90f * forward
            return Math.toRadians(rotationYaw.toDouble())
        }
    private fun strafe(speed: Float,motionY:Float) {
        val yaw = direction
        session.netSession.inboundPacket(SetEntityMotionPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            motion = Vector3f.from(-sin(yaw) * speed, motionY.toDouble(), cos(yaw) * speed)
        })
    }
    private fun strafe(speed: Float) {
        if (!isMoving(mc)) {
            if(mc.thePlayer.motionY>0) {
                session.netSession.inboundPacket(SetEntityMotionPacket().apply {
                    runtimeEntityId = mc.thePlayer.entityId
                    motion = Vector3f.from(0.0, -0.02, 0.0)
                })
            }
        }else {
            val yaw = direction
            var motionY = if (mc.thePlayer.motionY==0.0) 0.32 else 0.0
            if (mc.thePlayer.motionY <= 0.1 && mc.thePlayer.motionY > 0) motionY = -0.08
            if (motionY != 0.0) {
                session.netSession.inboundPacket(SetEntityMotionPacket().apply {
                    runtimeEntityId = mc.thePlayer.entityId
                    motion = Vector3f.from(-sin(yaw) * speed, motionY, cos(yaw) * speed)
                })
            }
        }
    }
}