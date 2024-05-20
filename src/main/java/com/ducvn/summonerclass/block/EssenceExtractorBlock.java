package com.ducvn.summonerclass.block;

import com.ducvn.summonerclass.container.EssenceExtractorContainer;
import com.ducvn.summonerclass.tileentity.EssenceExtractorTileEntity;
import com.ducvn.summonerclass.tileentity.SummonerClassTileEntitiesRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class EssenceExtractorBlock extends Block {
    public EssenceExtractorBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos,
                                PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isClientSide()) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if(tileEntity instanceof EssenceExtractorTileEntity) {
                INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

                NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random randomSource) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if(tileEntity instanceof EssenceExtractorTileEntity){
            if (((EssenceExtractorTileEntity) tileEntity).extractData.get(1) > 0){
                world.addParticle(ParticleTypes.ENCHANT,
                        (pos.getX() + 0.2D) + (randomSource.nextDouble() * 0.6D),
                        pos.getY() + 1.5D,
                        (pos.getZ() + 0.2D) + (randomSource.nextDouble() * 0.6D),
                        0D, 0D, 0D);
            }
        }
        super.animateTick(state, world, pos, randomSource);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState state1, boolean p_196243_5_) {
        if (!state.is(state1.getBlock())) {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof EssenceExtractorTileEntity) {
                InventoryHelper.dropContents(world, pos, (EssenceExtractorTileEntity)tileentity);
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, state1, p_196243_5_);
        }
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.summonerclass.essence_extractor");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new EssenceExtractorContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return SummonerClassTileEntitiesRegister.ESSENCE_EXTRACTOR_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
