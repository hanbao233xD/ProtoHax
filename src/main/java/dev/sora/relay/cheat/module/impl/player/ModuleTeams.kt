package dev.sora.relay.cheat.module.impl.player

import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.module.impl.combat.ModuleAntiBot.isBot
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.GameSession
import dev.sora.relay.game.entity.EntityPlayer
import dev.sora.relay.game.entity.EntityPlayerSP

class ModuleTeams : CheatModule("Teams") {

    private val modeValue = ListValue("Mode", arrayOf("Name","Arrow"), "Name")

    fun EntityPlayer.isTeams(session: GameSession): Boolean {
        if (this is EntityPlayerSP || !state) return false
        if (modeValue.get() == "Name") {
            val playerList = session.theWorld.playerList
        }
        return false
    }
}