package dev.sora.relay.cheat.module.impl.misc

import com.nukkitx.protocol.bedrock.data.inventory.TransactionType
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.Listen

class ModuleBetterAttack : CheatModule("BetterAttack") {
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        val packet = event.packet
    }
}