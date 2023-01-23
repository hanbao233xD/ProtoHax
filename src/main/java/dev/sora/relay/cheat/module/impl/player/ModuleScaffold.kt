package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.math.vector.Vector3i
import com.nukkitx.protocol.bedrock.data.PlayerActionType
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType
import com.nukkitx.protocol.bedrock.packet.*
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.IntValue
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.event.Listen
import java.util.*

class ModuleScaffold : CheatModule("Scaffold","自动搭路") {

    private val floatValue = IntValue("Expand", 4, 1, 6)
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        val packet = event.packet
        if (packet is AvailableCommandsPacket) return
        if (packet is MobEquipmentPacket) {
            session.thePlayer.currentItem = packet.item
        }
    }

    @Listen
    fun onMove(event: EventTick) {
        values="Expand: "+floatValue.get()
    }

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        val packet = event.packet
        if (packet is PlayerAuthInputPacket) {
            if (session.thePlayer.currentItem == null) return
            /*session.sendPacket(PlayerActionPacket().apply {
                action = PlayerActionType.ITEM_USE_ON_START
                runtimeEntityId = session.thePlayer.entityId
                blockPosition = Vector3i.from(
                    kotlin.math.floor(session.thePlayer.posX).toInt(),
                    kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                    kotlin.math.floor(session.thePlayer.posZ).toInt()
                )
                resultPosition = Vector3i.from(
                    kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw)).toInt(),
                    kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                    kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw)).toInt()
                )
                face = getFace(session.thePlayer.rotationYaw)
            })
            session.sendPacket(AnimatePacket().apply {
                rowingTime = 0.0f
                action = AnimatePacket.Action.SWING_ARM
                runtimeEntityId = session.thePlayer.entityId
            })
            session.sendPacket(InventoryTransactionPacket().apply {
                legacyRequestId = 0
                transactionType = TransactionType.ITEM_USE
                actionType = 0
                runtimeEntityId = 0
                blockPosition = Vector3i.from(
                    kotlin.math.floor(session.thePlayer.posX).toInt(),
                    kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                    kotlin.math.floor(session.thePlayer.posZ).toInt()
                )
                blockFace = getFace(session.thePlayer.rotationYaw)
                hotbarSlot = session.thePlayer.heldItemSlot
                session.thePlayer.currentItem!!.isUsingNetId = false
                session.thePlayer.currentItem!!.netId = 0
                itemInHand = session.thePlayer.currentItem
                playerPosition = session.thePlayer.vec3Position
                clickPosition = Vector3f.from(0.5f, 0.5f, 0.5f)
                isUsingNetIds = false
                blockRuntimeId = 115
            })
            session.sendPacket(InventoryTransactionPacket().apply {
                legacyRequestId = 0
                transactionType = TransactionType.ITEM_USE
                actionType = 1
                runtimeEntityId = 0
                blockPosition = Vector3i.from(
                    0, 0, 0
                )
                blockFace = 255
                hotbarSlot = session.thePlayer.heldItemSlot
                itemInHand = session.thePlayer.currentItem
                playerPosition = session.thePlayer.vec3Position
                clickPosition = Vector3f.from(0f, 0f, 0f)
                isUsingNetIds = false
                blockRuntimeId = 0
            })
            session.sendPacket(PlayerActionPacket().apply {
                action = PlayerActionType.ITEM_USE_ON_STOP
                runtimeEntityId = session.thePlayer.entityId
                blockPosition = Vector3i.from(
                    kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw)).toInt(),
                    kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                    kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw)).toInt()
                )
                resultPosition = Vector3i.from(
                    0, 0, 0
                )
            })

            session.sendPacket(PlayerActionPacket().apply {
                action = PlayerActionType.ITEM_USE_ON_STOP
                runtimeEntityId = session.thePlayer.entityId
                blockPosition = Vector3i.from(
                    kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw)).toInt(),
                    kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                    kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw)).toInt()
                )
                resultPosition = Vector3i.from(
                    0, 0, 0
                )
            })*/
            var i=0
            repeat(floatValue.get()) {
                i++
                session.sendPacket(PlayerActionPacket().apply {
                    action = PlayerActionType.ITEM_USE_ON_START
                    runtimeEntityId = session.thePlayer.entityId
                    blockPosition = Vector3i.from(
                        kotlin.math.floor(session.thePlayer.posX).toInt(),
                        kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                        kotlin.math.floor(session.thePlayer.posZ).toInt()
                    )
                    resultPosition = Vector3i.from(
                        kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw,i)).toInt(),
                        kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                        kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw,i)).toInt()
                    )
                    face = getFace(session.thePlayer.rotationYaw)
                })
                session.sendPacket(AnimatePacket().apply {
                    rowingTime = 0.0f
                    action = AnimatePacket.Action.SWING_ARM
                    runtimeEntityId = session.thePlayer.entityId
                })
                session.sendPacket(InventoryTransactionPacket().apply {
                    legacyRequestId = 0
                    transactionType = TransactionType.ITEM_USE
                    actionType = 0
                    runtimeEntityId = 0
                    blockPosition = Vector3i.from(
                        kotlin.math.floor(session.thePlayer.posX).toInt(),
                        kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                        kotlin.math.floor(session.thePlayer.posZ).toInt()
                    )
                    blockFace = getFace(session.thePlayer.rotationYaw)
                    hotbarSlot = session.thePlayer.heldItemSlot
                    session.thePlayer.currentItem!!.isUsingNetId = false
                    session.thePlayer.currentItem!!.netId = 0
                    itemInHand = session.thePlayer.currentItem
                    playerPosition = session.thePlayer.vec3Position
                    clickPosition = Vector3f.from(0.5f, 0.5f, 0.5f)
                    isUsingNetIds = false
                    blockRuntimeId = 115
                })
                session.sendPacket(InventoryTransactionPacket().apply {
                    legacyRequestId = 0
                    transactionType = TransactionType.ITEM_USE
                    actionType = 1
                    runtimeEntityId = 0
                    blockPosition = Vector3i.from(
                        0, 0, 0
                    )
                    blockFace = 255
                    hotbarSlot = session.thePlayer.heldItemSlot
                    itemInHand = session.thePlayer.currentItem
                    playerPosition = session.thePlayer.vec3Position
                    clickPosition = Vector3f.from(0f, 0f, 0f)
                    isUsingNetIds = false
                    blockRuntimeId = 0
                })
                session.sendPacket(PlayerActionPacket().apply {
                    action = PlayerActionType.ITEM_USE_ON_STOP
                    runtimeEntityId = session.thePlayer.entityId
                    blockPosition = Vector3i.from(
                        kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw,i)).toInt(),
                        kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                        kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw,i)).toInt()
                    )
                    resultPosition = Vector3i.from(
                        0, 0, 0
                    )
                })

                session.sendPacket(PlayerActionPacket().apply {
                    action = PlayerActionType.ITEM_USE_ON_STOP
                    runtimeEntityId = session.thePlayer.entityId
                    blockPosition = Vector3i.from(
                        kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw,i)).toInt(),
                        kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                        kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw,i)).toInt()
                    )
                    resultPosition = Vector3i.from(
                        0, 0, 0
                    )
                })
                session.netSession.inboundPacket(UpdateBlockPacket().apply {
                    flags.add(UpdateBlockPacket.Flag.NETWORK)
                    runtimeId = session.thePlayer.currentItem!!.blockRuntimeId
                    dataLayer=0
                    blockPosition = Vector3i.from(
                        kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw,i)).toInt(),
                        kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                        kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw,i)).toInt()
                    )
                })
                session.netSession.inboundPacket(UpdateBlockPacket().apply {
                    flags.add(UpdateBlockPacket.Flag.NETWORK)
                    runtimeId = session.thePlayer.currentItem!!.blockRuntimeId
                    dataLayer=0
                    blockPosition = Vector3i.from(
                        kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw,i)).toInt(),
                        kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                        kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw,i)).toInt()
                    )
                })
            }
            // 拓展搭路 1


/*
            session.netSession.inboundPacket(UpdateBlockPacket().apply {
                flags.add(UpdateBlockPacket.Flag.NETWORK)
                runtimeId = session.thePlayer.currentItem!!.blockRuntimeId
                dataLayer=0
                blockPosition = Vector3i.from(
                    kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw,2)).toInt(),
                    kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                    kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw,2)).toInt()
                )
            })

            session.netSession.inboundPacket(UpdateBlockPacket().apply {
                flags.add(UpdateBlockPacket.Flag.NETWORK)
                runtimeId = session.thePlayer.currentItem!!.blockRuntimeId
                dataLayer=0
                blockPosition = Vector3i.from(
                    kotlin.math.floor(session.thePlayer.posX + getOffsetX(session.thePlayer.rotationYaw,3)).toInt(),
                    kotlin.math.floor(session.thePlayer.posY - 2.62).toInt(),
                    kotlin.math.floor(session.thePlayer.posZ + getOffsetZ(session.thePlayer.rotationYaw,3)).toInt()
                )
            })
*/
            return
        }
        if (packet is InventoryTransactionPacket) {
            session.thePlayer.currentItem = packet.itemInHand
        }
    }

    private fun getOffsetX(yaw: Float): Int {
        return if (yaw > 45 && yaw < 135) -1 else if (yaw > -135 && yaw < -45) 1 else 0
    }

    private fun getOffsetZ(yaw: Float): Int {
        return if (yaw < 45 && yaw > -45) -1 else if (yaw > 135 || yaw < -135) 1 else 0
    }

    private fun getOffsetX(yaw: Float,index:Int): Int {
        return if (yaw > 45 && yaw < 135) -index else if (yaw > -135 && yaw < -45) index else 0
    }

    private fun getOffsetZ(yaw: Float,index:Int): Int {
        return if (yaw < 45 && yaw > -45) index else if (yaw > 135 || yaw < -135) -index else 0
    }

    private fun getFace(yaw: Float): Int {
        return if (yaw < 45 && yaw > -45) 3 else if (yaw > 45 && yaw < 135) 4 else if (yaw > 135 || yaw < -135) 2 else if (yaw > -135 && yaw < -45) 5 else 1
    }
}