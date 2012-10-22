package micdoodle8.mods.galacticraft.moon;

import java.util.Random;

import micdoodle8.mods.galacticraft.API.WorldGenMinableMeta;
import net.minecraft.src.BiomeDecorator;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import cpw.mods.fml.common.FMLLog;

/**
 * Copyright 2012, micdoodle8
 * 
 *  All rights reserved.
 *
 */
public class GCMoonBiomeDecorator 
{
	protected World currentWorld;

	protected Random randomGenerator;

	protected int chunk_X;

	protected int chunk_Z;

	protected BiomeGenBase biome;

	protected WorldGenerator dirtGen;

	protected WorldGenerator cheeseGen;
	
	protected WorldGenerator aluminumGen;

	protected WorldGenerator ironGen;

	public GCMoonBiomeDecorator(BiomeGenBase par1BiomeGenBase) 
	{
		this.aluminumGen = new WorldGenMinableMeta(GCMoonBlocks.blockMoonOres.blockID, 7, 0, true);
		this.ironGen = new WorldGenMinableMeta(GCMoonBlocks.blockMoonOres.blockID, 8, 1, true);
		this.cheeseGen = new WorldGenMinableMeta(GCMoonBlocks.blockMoonOres.blockID, 10, 2, true);
		this.dirtGen = new WorldGenMinableMeta(GCMoonBlocks.moonDirt.blockID, 32, 0, false);
		this.biome = par1BiomeGenBase;
	}

	public void decorate(World par1World, Random par2Random, int par3, int par4) 
	{
		if (this.currentWorld != null) 
		{
			throw new RuntimeException("Already decorating!!");
		} 
		else 
		{
			this.currentWorld = par1World;
			this.randomGenerator = par2Random;
			this.chunk_X = par3;
			this.chunk_Z = par4;
			this.generateOres();
			this.currentWorld = null;
			this.randomGenerator = null;
		}
	}

	protected void genStandardOre1(int par1, WorldGenerator par2WorldGenerator, int par3, int par4) 
	{
		for (int var5 = 0; var5 < par1; ++var5) 
		{
			int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
			int var7 = this.randomGenerator.nextInt(par4 - par3) + par3;
			int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
			par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
		}
	}

	protected void generateOres() 
	{
		this.genStandardOre1(20, this.dirtGen, 0, 200);
        this.genStandardOre1(3, this.aluminumGen, 0, 14);
        this.genStandardOre1(8, this.ironGen, 0, 34);
        this.genStandardOre1(12, this.cheeseGen, 0, 128);
	}
}
