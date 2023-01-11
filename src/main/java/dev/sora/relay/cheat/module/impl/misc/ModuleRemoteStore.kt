package dev.sora.relay.cheat.module.impl.misc

import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.entity.EntityPlayerSP

class ModuleRemoteStore : CheatModule("RemoteStore") {
    override fun onEnable() {
        val entityList = session.theWorld.entityMap.values.filter {
            it.entityType == 15
        }

        if (entityList.isNotEmpty()){
            (entityList.minByOrNull { it.distanceSq(session.thePlayer) } ?: return).also {
                session.thePlayer.attackEntity(it, session, EntityPlayerSP.SwingMode.SERVERSIDE)
            }
        }

        toggle()
    }
}