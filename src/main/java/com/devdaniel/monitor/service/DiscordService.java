package com.devdaniel.monitor.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiscordService {

    @Value("${discord.bot.token}")
    private String botToken;

    @Getter
    private JDA jda;

    private final Map<String, Instant> lastSentMap = new HashMap<>();
    private static final long COOLDOWN_SECONDS = 60;

    @PostConstruct
    public void init() throws Exception {
        jda = JDABuilder.create(botToken,
                        GatewayIntent.GUILD_MESSAGES)
                .disableCache(
                        CacheFlag.VOICE_STATE,
                        CacheFlag.EMOJI,
                        CacheFlag.STICKER,
                        CacheFlag.SCHEDULED_EVENTS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ONLINE_STATUS
                )
                .setMemberCachePolicy(MemberCachePolicy.NONE)
                .setChunkingFilter(ChunkingFilter.NONE)
                .setRequestTimeoutRetry(false)
                .build()
                .awaitReady();

        System.out.println("✅ Discord Bot conectado com sucesso!");
    }

    @PreDestroy
    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
        }
    }

    public void sendMessageToChannel(String channelId, String message) {
        Instant now = Instant.now();
        Instant lastSent = lastSentMap.getOrDefault(channelId, Instant.MIN);

        if (Duration.between(lastSent, now).getSeconds() < COOLDOWN_SECONDS) {
            System.out.println("⏳ Cooldown ativo para canal " + channelId + ", ignorando envio.");
            return;
        }

        try {
            TextChannel channel = jda.getTextChannelById(channelId);
            if (channel != null) {
                channel.sendMessage(message).queue(
                        success -> {
                            lastSentMap.put(channelId, now);
                            System.out.println("✅ Alerta enviado ao canal: " + channelId);
                        },
                        error -> System.err.println("❌ Erro ao enviar mensagem: " + error.getMessage())
                );
            } else {
                System.err.println("⚠️ Canal Discord não encontrado: " + channelId);
            }
        } catch (ErrorResponseException e) {
            System.err.println("❌ Discord API error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado ao enviar mensagem: " + e.getMessage());
        }
    }
}