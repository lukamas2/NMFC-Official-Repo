package mod.goony.world.gen;

import java.util.Random;

import mod.goony.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPatagonianGrass extends WorldGenerator
{
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) 
	{
		// we randomly pick between a bush with a cookie and a bush without a cookie
		Block cookieBush = rand.nextBoolean() ? ModBlocks.pgrass : ModBlocks.pgrass;
		int y = 1 + getGroundFromAbove(worldIn, pos.getX(), pos.getZ());
		// debug:
		// System.out.println("Y-value of ground is " + y + " at (" + pos.getX() + ", " + pos.getZ() + ")");
		// the Y we passed earlier will be used here as the minimum spawn height allowed
		if(y >= pos.getY())
		{
			BlockPos bushPos = new BlockPos(pos.getX(), y, pos.getZ());
			// we know it's on top of grass or dirt, but what is here already?
			Block toReplace = worldIn.getBlockState(bushPos).getBlock();
			// only place bush if it is air or plant
			{
				// set the block to a bush
				// use 2 as the flag to prevent update -- you don't have to include that parameter
				worldIn.setBlockState(bushPos, cookieBush.getDefaultState(), 2);
				// debug:
				// System.out.println("placed a cookie bush!");
			}   // else System.out.println("Sadly, this block is occupied by " + toReplace.getUnlocalizedName());
		}
		return false;
	}

	// find a grass or dirt block to place the bush on
	public static int getGroundFromAbove(World world, int x, int z)
	{
		int y = 255;
		boolean foundGround = false;
		while(!foundGround && y-- >= 0)
		{
			Block blockAt = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			// "ground" for our bush is grass or dirt
			foundGround = blockAt == ModBlocks.patagoniandirt || blockAt == ModBlocks.patagoniangrass;
		}

		return y;
	}
}
