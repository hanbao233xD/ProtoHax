package dev.sora.relay.cheat.module.impl.movement

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.Ability
import com.nukkitx.protocol.bedrock.data.AbilityLayer
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData
import com.nukkitx.protocol.bedrock.data.PlayerPermission
import com.nukkitx.protocol.bedrock.data.command.CommandPermission
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType
import com.nukkitx.protocol.bedrock.packet.*
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.utils.TimerUtil
import dev.sora.relay.game.utils.movement.MovementUtils.isMoving
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

class ModuleFly : CheatModule("Fly","飞行") {

    private val modeValue = ListValue("FlyMode", arrayOf("Motion", "Vanilla", "Mineplex", "ZoomFly"), "Motion")
    private val motionYValue = FloatValue("MotionY", 0.32f, 0f, 2f)
    private val motionXZValue = FloatValue("MotionXZ", 0.38f, 0f, 2f)
    private val zoomSpeedValue = FloatValue("ZoomSpeed", 0.98f, 0f, 1f)

    private var launchY = 0.0
    private var canFly = false

    private val abilityPacket = UpdateAbilitiesPacket().apply {
        playerPermission = PlayerPermission.OPERATOR
        commandPermission = CommandPermission.OPERATOR
        abilityLayers.add(AbilityLayer().apply {
            layerType = AbilityLayer.Type.BASE
            abilitiesSet.addAll(Ability.values())
            abilityValues.addAll(
                arrayOf(
                    Ability.BUILD,
                    Ability.MINE,
                    Ability.DOORS_AND_SWITCHES,
                    Ability.OPEN_CONTAINERS,
                    Ability.ATTACK_PLAYERS,
                    Ability.ATTACK_MOBS,
                    Ability.OPERATOR_COMMANDS,
                    Ability.MAY_FLY,
                    Ability.FLY_SPEED,
                    Ability.WALK_SPEED
                )
            )
            walkSpeed = 0.1f
            flySpeed = 0.15f
        })
    }

    override fun onEnable() {
        canFly = false
        launchY = session.thePlayer.posY
        onDamage=0
        if(modeValue.get()=="ZoomFly") {
            var pos = Vector3f.from(
                mc.thePlayer.posX,
                mc.thePlayer.posY - 4,
                mc.thePlayer.posZ
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
            chat("注意!受到伤害后你可以飞行1秒!请先面向目标，并找机会让自己受到伤害(如弓箭,或敌人打击)飞行途中按下跳跃键取消飞行!")
            chat("等待伤害...")
        }
    }

    @Listen
    fun onTick(event: EventTick) {
        when {
            modeValue.get() == "Mineplex" -> {
                event.session.netSession.inboundPacket(abilityPacket.apply {
                    uniqueEntityId = event.session.thePlayer.entityId
                })
                if (!canFly) return
                val player = event.session.thePlayer
                val yaw = Math.toRadians(player.rotationYaw.toDouble())
                val value = 2.2f
                player.teleport(
                    player.posX - sin(yaw) * value,
                    launchY,
                    player.posZ + cos(yaw) * value,
                    event.session.netSession
                )
            }

            modeValue.get() == "Vanilla" && !canFly -> {
                canFly = true
                event.session.netSession.inboundPacket(abilityPacket.apply {
                    uniqueEntityId = event.session.thePlayer.entityId
                })
            }
        }
    }

    private var onDamage = 0
    private val damageTimer = TimerUtil()

    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        if (event.packet is UpdateAbilitiesPacket) {
            event.cancel()
            event.session.netSession.inboundPacket(abilityPacket.apply {
                uniqueEntityId = event.session.thePlayer.entityId
            })
        } else if (event.packet is StartGamePacket) {
            event.session.netSession.inboundPacket(abilityPacket.apply {
                uniqueEntityId = event.session.thePlayer.entityId
            })
        }
        if (onDamage==1 && damageTimer.delay(1000f)) {
            onDamage = 0
            state=false
        }
        if (event.packet is EntityEventPacket) {
            if (event.packet.type== EntityEventType.HURT && event.packet.runtimeEntityId==mc.thePlayer.entityId){
                onDamage=1
                damageTimer.reset()
                chat("ZoomFly!")
            }
        }
    }

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        when {
            modeValue.get() == "Mineplex" -> {
                if (event.packet is RequestAbilityPacket && event.packet.ability == Ability.FLYING) {
                    canFly = !canFly
                    if (canFly) {
                        launchY = floor(session.thePlayer.posY) - 0.38
                        event.session.sendPacketToClient(EntityEventPacket().apply {
                            runtimeEntityId = event.session.thePlayer.entityId
                            type = EntityEventType.HURT
                            data = 0
                        })
                        val player = event.session.thePlayer
                        repeat(5) {
                            event.session.sendPacket(MovePlayerPacket().apply {
                                runtimeEntityId = player.entityId
                                position = Vector3f.from(player.posX, launchY, player.posZ)
                                rotation = Vector3f.from(player.rotationPitch, player.rotationYaw, 0f)
                                mode = MovePlayerPacket.Mode.NORMAL
                            })
                        }
                    }
                    event.session.netSession.inboundPacket(abilityPacket.apply {
                        uniqueEntityId = session.thePlayer.entityId
                    })
                    event.cancel()
                } else if (event.packet is MovePlayerPacket && canFly) {
                    event.packet.isOnGround = true
                    event.packet.position = event.packet.position.let {
                        Vector3f.from(it.x, launchY.toFloat(), it.z)
                    }
                }
            }
            modeValue.get() == "ZoomFly" -> {
                if (!mc.thePlayer.inputData.contains(PlayerAuthInputData.JUMPING)){
                    if (event.packet is PlayerAuthInputPacket && onDamage==1) {
                        strafe(zoomSpeedValue.get(),0.05f)
                    }
                }else{
                    if(onDamage==1) {
                        onDamage = 0
                        state = false
                        chat("Stop!")
                    }
                }
            }

            modeValue.get() == "Motion" -> {
                if (event.packet is PlayerAuthInputPacket && isMoving(mc)) strafe(
                    motionXZValue.get(),
                    if (mc.thePlayer.inputData.contains(PlayerAuthInputData.JUMPING)) motionYValue.get() else if (mc.thePlayer.inputData.contains(
                            PlayerAuthInputData.SNEAKING
                        )
                    ) -motionYValue.get() else 0.01f
                )
            }

            else -> {
                if (event.packet is RequestAbilityPacket && event.packet.ability == Ability.FLYING) {
                    event.cancel()
                }
            }
        }
    }


    private fun strafe(speed: Float, motionY: Float) {
        val yaw = direction
        session.netSession.inboundPacket(SetEntityMotionPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            motion = Vector3f.from(-sin(yaw) * speed, motionY.toDouble(), cos(yaw) * speed)
        })
    }
}