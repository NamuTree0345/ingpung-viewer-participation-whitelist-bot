package xyz.namutree0345.ingpungWhitelist.bot

import me.kbrewster.mojangapi.MojangAPI
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class CommandHandler : ListenerAdapter() {

    fun failedMessage(author: User, reason: String) {
        author.openPrivateChannel().queue { channel ->
            val builder = EmbedBuilder()
            builder.setTitle("뷰잉풍 시참")
            builder.setColor(Color.RED)
            builder.setDescription(reason)
            builder.setFooter("!마크 <닉네임>", Bot.jda?.selfUser?.avatarUrl)
            channel.sendMessage(builder.build()).queue()
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if(event.author.isBot) return
        if(!event.isFromType(ChannelType.TEXT)) return

        if(event.channel.id == "844175838969593909") {
            if(event.message.contentRaw.startsWith("!send")) {
                if(event.member?.hasPermission(Permission.ADMINISTRATOR) == true) {
                    event.channel.sendMessage(event.message.contentRaw.replace("!send ", "")).queue() {
                        it.channel.pinMessageById(it.id).queue()
                    }
                }
            }
            if(event.message.contentRaw.startsWith("!마크")) {
                val notParsed = event.message.contentRaw
                val parsed = notParsed.split(" ")
                if(parsed.size > 1) {
                    val profileValidatorResult = ProfileRegister.checkVaild(parsed[1], event.author.id)
                    if(profileValidatorResult == 0) {
                        event.author.openPrivateChannel().queue { channel ->
                            val builder = EmbedBuilder()
                            builder.setTitle("뷰잉풍 시참")
                            builder.setColor(Color.GREEN)
                            builder.setDescription("마인크래프트 계정 등록 완료!")
                            builder.addField(parsed[1], "`${MojangAPI.getProfile(parsed[1]).id}`", false)
                            builder.setFooter("!마크 <닉네임>", Bot.jda?.selfUser?.avatarUrl)
                            channel.sendMessage(builder.build()).queue()
                        }
                    } else if(profileValidatorResult == 1) {
                        failedMessage(event.author, "계정이 알맞지 않습니다. 계정 이름을 제대로 입력하셨는지 확인해 주세요.")
                    } else if(profileValidatorResult == 2) {
                        failedMessage(event.author, "이미 등록된 계정입니다.")
                    }
                } else {
                    failedMessage(event.author, "명령어가 잘못되었습니다. 명령어를 다시 확인해주세요.")
                }
            } else {
               failedMessage(event.author, "명령어가 잘못되었습니다. 명령어를 다시 확인해주세요.")
            }
            event.message.delete().queue()
        }
    }

}