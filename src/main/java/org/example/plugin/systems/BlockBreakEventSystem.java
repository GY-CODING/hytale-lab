package org.example.plugin.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.ItemUtils;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class BlockBreakEventSystem
        extends EntityEventSystem<EntityStore, BreakBlockEvent> {

    public BlockBreakEventSystem() {
        super(BreakBlockEvent.class);
    }

    @Override
    public void handle(
            int i,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
            @NonNullDecl BreakBlockEvent event
    ) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) return;

        // Evitar drop vanilla si quieres
        event.setCancelled(true);

        // Crear el ItemStack del lingote
        ItemStack ironIngot = new ItemStack("core:iron_ingot", 1);

        // Dropear cerca del player (API disponible)
        ItemUtils.dropItem(ref, ironIngot, store);

        player.sendMessage(
                Message.raw("YOU MINED: " + event.getBlockType().getId())
        );
    }


    @NonNullDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
