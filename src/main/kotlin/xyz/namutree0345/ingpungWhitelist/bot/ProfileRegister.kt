package xyz.namutree0345.ingpungWhitelist.bot

import me.kbrewster.mojangapi.MojangAPI
import java.io.File

class ProfileRegister {

    companion object {
        fun checkVaild(name: String, discordId: String) : Int {
            try {
                if(!File("players${File.separator}$discordId.txt").exists()) {
                    val profile = MojangAPI.getProfile(name)
                    File("whitelist.txt").appendText("${profile.id}\n")
                    File("players${File.separator}$discordId.txt").writeText(profile.id)
                    return 0
                }
                return 2
            } catch(e: me.kbrewster.exceptions.InvalidPlayerException) {
                return 1
            }
        }
    }

}