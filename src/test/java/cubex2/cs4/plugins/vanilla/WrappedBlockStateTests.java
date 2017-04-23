package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.WrappedBlockState;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class WrappedBlockStateTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();
    }

    @Test
    public void test_deserialization()
    {
        WrappedBlockState state = gson.fromJson("{ \"block\": \"minecraft:log\", \"properties\" : { \"variant\" : \"spruce\"} }", WrappedBlockState.class);
        IBlockState blockState = state.createState();

        assertNotNull(state);
        assertNotNull(blockState);
        assertSame(Blocks.LOG, blockState.getBlock());
        assertSame(BlockPlanks.EnumType.SPRUCE, blockState.getValue(BlockOldLog.VARIANT));
    }
}