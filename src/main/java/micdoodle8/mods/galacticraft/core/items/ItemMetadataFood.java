package micdoodle8.mods.galacticraft.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemMetadataFood extends ItemFood
{
	public int[] hungerValues = {8,8,4,2};
	public float[] saturationModifiers = {0.3F, 0.6F, 0.6F, 0.3F};

	public static final String[] names = { "dehydratedApple", "dehydratedCarrot", "dehydratedMelon", "dehydratedPotato" };
    protected IIcon[] icons = new IIcon[ItemMetadataFood.names.length];
    public static final int MaxItemUseDuration = 32;


	public ItemMetadataFood(int[] hungerValues, float[] saturationModifiers)
	{
		super(0, 0f, false);
		this.hungerValues = hungerValues;
		this.saturationModifiers = saturationModifiers;

		setHasSubtypes(true);
	}
	
	public ItemMetadataFood(String assetName) {
		super(0, 0f, false);
		//this.hungerValues = {8,8,4,2};
		//this.saturationModifiers = {0.3F, 0.6F, 0.6F, 0.3F};
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setUnlocalizedName(assetName);
        this.setTextureName(GalacticraftCore.TEXTURE_PREFIX + assetName);
	}

	/**
	 * @return The hunger value of the ItemStack
	 */
	@Override
	public int func_150905_g(ItemStack itemStack)
	{
		return hungerValues[itemStack.getItemDamage()];
	}

	/**
	 * @return The saturation modifier of the ItemStack
	 */
	@Override
	public float func_150906_h(ItemStack itemStack)
	{
		return saturationModifiers[itemStack.getItemDamage()];
	}

//    public ItemMetadataFood(String assetName)
//    {
//        super(alwaysEdible, isWolfsFavoriteMeat);
//        this.setMaxDamage(0);
//        this.setHasSubtypes(true);
//        this.setUnlocalizedName(assetName);
//        this.setTextureName(GalacticraftCore.TEXTURE_PREFIX + assetName);
//    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GalacticraftCore.galacticraftItemsTab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return ClientProxyCore.galacticraftItem;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        int i = 0;

        for (final String name : ItemMetadataFood.names)
        {
            this.icons[i++] = iconRegister.registerIcon(this.getIconString() + "." + name);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return this.getUnlocalizedName() + ".cannedFood";
    }

    @Override
    public IIcon getIconFromDamage(int damage)
    {
        if (this.icons.length > damage)
        {
            return this.icons[damage];
        }

        return super.getIconFromDamage(damage);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < ItemMetadataFood.names.length; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
            par3List.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate(this.getUnlocalizedName() + "." + ItemMetadataFood.names[par1ItemStack.getItemDamage()] + ".name"));
    }

    public int getHealAmount(ItemStack par1ItemStack)
    {
    	return hungerValues[par1ItemStack.getItemDamage()];
    }

    public float getSaturationModifier(ItemStack par1ItemStack)
    {
    	return saturationModifiers[par1ItemStack.getItemDamage()];
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(this.getHealAmount(par1ItemStack), this.getSaturationModifier(par1ItemStack));
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        if (!par2World.isRemote)
        {
            par3EntityPlayer.entityDropItem(new ItemStack(GCItems.canister, 1, 0), 0.0F);
        }
        return par1ItemStack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
            return MaxItemUseDuration;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
            return EnumAction.eat;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

        return par1ItemStack;
    }
    
    @Override
    public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer player, Entity entity)
    {
    	return false;
    }
}
