package net.luvas.tutorialmod.item.custom;

import net.luvas.tutorialmod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.MUD,
                    Blocks.END_STONE, Blocks.SPONGE,
                    Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS,
                    Blocks.OAK_LOG, Blocks.NETHERITE_BLOCK
            );

    private static final Map<Block, Block> REVERSE_CHISEL_MAP =
            CHISEL_MAP.entrySet().stream()
                    .collect(java.util.stream.Collectors.toMap(
                            Map.Entry::getValue,
                            Map.Entry::getKey
                    ));



    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if (context.getPlayer() == null) return ActionResult.PASS;

        boolean isSneaking = context.getPlayer().isSneaking();

        Map<Block, Block> activeMap = isSneaking ? REVERSE_CHISEL_MAP : CHISEL_MAP;

        if (activeMap.containsKey(clickedBlock)) {
            if (!world.isClient()) {
                world.setBlockState(
                        context.getBlockPos(),
                        activeMap.get(clickedBlock).getDefaultState()
                );

                context.getStack().damage(1,
                        (ServerWorld) world,
                        (ServerPlayerEntity) context.getPlayer(),
                        item -> context.getPlayer()
                                .sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND)
                );

                world.playSound(null,
                        context.getBlockPos(),
                        SoundEvents.BLOCK_GRINDSTONE_USE,
                        SoundCategory.BLOCKS
                );
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
