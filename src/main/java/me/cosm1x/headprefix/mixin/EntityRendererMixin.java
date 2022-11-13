package me.cosm1x.headprefix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import me.cosm1x.headprefix.headprefix.HeadPrefix;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Shadow
    private TextRenderer textRenderer;
    
    @Shadow
    protected EntityRenderDispatcher dispatcher;

    @Shadow
    protected abstract void renderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    @Shadow
    protected abstract boolean hasLabel(T entity);

    @Shadow
    public TextRenderer getTextRenderer() {
        return this.textRenderer;
    }
    
    @Overwrite
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (!this.hasLabel(entity)) {
            return;
        }
        renderLabelIfPresent(entity, ((Entity)entity).getName(), matrices, vertexConsumers, light);
        
        // Check if the entity is a player
        if (entity instanceof PlayerEntity) {
            // Get the player
            PlayerEntity player = (PlayerEntity) entity;
            // Get the player's team
            Team team = player.getScoreboard().getPlayerTeam(player.getEntityName());
            // Check if the player is in a team
            if (team != null && HeadPrefix.getHeadPrefix(team) != null) {
                this.renderPrefixIfPresent(entity, HeadPrefix.getHeadPrefix(team), matrices, vertexConsumers, light);
            }            
        }
    }
    
    protected void renderPrefixIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        double d = this.dispatcher.getSquaredDistanceToCamera((Entity)entity);
        if (d > 4096.0) {
            return;
        }
        boolean bl = !((Entity)entity).isSneaky();
        float f = ((Entity)entity).getHeight() + 0.5f;
        int i = -10;
        matrices.push();
        matrices.translate(0.0, f, 0.0);
        matrices.multiply((this.dispatcher.getRotation()));
        matrices.scale(-0.025f, -0.025f, 0.025f);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
        int j = (int)(g * 255.0f) << 24;
        TextRenderer textRenderer = this.getTextRenderer();
        float h = -textRenderer.getWidth(text) / 2;
        // TODO: Configurable options backgroundColor(hex, convert to decimal)
        textRenderer.draw(text, h, (float)i, 0x20FFFFFF, false, matrix4f, vertexConsumers, bl, 0, light);
        if (bl) {
            textRenderer.draw(text, h, (float)i, -1, false, matrix4f, vertexConsumers, false, 0, light);
        }
        matrices.pop();
    }
}