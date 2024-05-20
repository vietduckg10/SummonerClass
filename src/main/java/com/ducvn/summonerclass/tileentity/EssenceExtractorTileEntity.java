package com.ducvn.summonerclass.tileentity;

import com.ducvn.summonerclass.data.recipe.EssenceExtractorRecipe;
import com.ducvn.summonerclass.data.recipe.SummonerClassRecipeTypesRegister;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class EssenceExtractorTileEntity extends TileEntity implements ITickableTileEntity, IInventory {
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    public IIntArray extractData = new IntArray(2);
    public EssenceExtractorTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public EssenceExtractorTileEntity() {
        this(SummonerClassTileEntitiesRegister.ESSENCE_EXTRACTOR_TILE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        extractData.set(0, nbt.getInt("ExtractTime"));
        extractData.set(1, nbt.getInt("ExtractProgress"));
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("ExtractTime", extractData.get(0));
        compound.putInt("ExtractProgress", extractData.get(1));
        ItemStackHelper.saveAllItems(compound, this.items);
        return super.save(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
        };
    }

    public ItemStack getItemForDisplay(){
        if (this.getRecipe() != null){
            return this.getRecipe().getResultItem();
        }
        else {
            return Items.AIR.getDefaultInstance();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    private void finishExtracting(int numInput, int numAdditionInput, ItemStack output) {
        itemHandler.extractItem(0,numInput,false);
        itemHandler.extractItem(1,numAdditionInput,false);
        itemHandler.insertItem(2, output, false);
    }

    @Override
    public void tick() {
        if(level.isClientSide){
            return;
        }
        boolean isChanged = false;
        if (this.getRecipe() != null){
            int numInput = this.getRecipe().getInput().getItems()[0].getCount();
            int numAdditionInput = 0;
            if (!(this.getRecipe().getAdditionInput().getItems()[0].getItem() instanceof AirItem)){
                numAdditionInput = this.getRecipe().getAdditionInput().getItems()[0].getCount();
            }
            if (this.itemHandler.getStackInSlot(0).getCount() >= numInput
            && this.itemHandler.getStackInSlot(1).getCount() >= numAdditionInput){
                this.extractData.set(0, this.getRecipe().getExtractTime());
                if (this.extractData.get(1) < this.extractData.get(0)){
                    extractData.set(1, extractData.get(1) + 1);
                }
                else {
                    ItemStack output = this.getRecipe().getResultItem();
                    if (this.itemHandler.getStackInSlot(2).getCount() + output.getCount() <= 64) {
                        if ((this.itemHandler.getStackInSlot(2).getItem() == output.getItem()
                                || this.itemHandler.getStackInSlot(2).getItem() == Items.AIR)){
                            finishExtracting(numInput, numAdditionInput, output);
                            this.extractData.set(1,0);
                        }
                    }
                }
                isChanged = true;
            }
            else {
                this.extractData.set(0,0);
                this.extractData.set(1,0);
            }
        }
        else {
            this.extractData.set(0,0);
            this.extractData.set(1,0);
        }
        if (isChanged){
            setChanged();
        }
    }

    @Nullable
    private EssenceExtractorRecipe getRecipe() {
        Set<IRecipe<?>> recipes = findRecipesByType(SummonerClassRecipeTypesRegister.ESSENCE_EXTRACTOR_RECIPE, this.level);
        for (IRecipe<?> iRecipe : recipes) {
            EssenceExtractorRecipe recipe = (EssenceExtractorRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.itemHandler), this.level)) {
                return recipe;
            }
        }

        return null;
    }

    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn, World world) {
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return ItemStackHelper.removeItem(this.items, p_70298_1_, p_70298_2_);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return ItemStackHelper.takeItem(this.items, p_70304_1_);
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return false;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
