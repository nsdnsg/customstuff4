package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.util.IntRange;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Optional;

public abstract class ContentBlockBaseWithSubtypes<T extends Block> extends ContentBlockBase<T>
{
    public int[] subtypes = new int[0];
    public MetadataAttribute<String> creativeTab = MetadataAttribute.constant("anonexistingtabtoreturnnull");
    public MetadataAttribute<Float> hardness = MetadataAttribute.constant(1f);
    public MetadataAttribute<Float> resistance = MetadataAttribute.constant(0f);
    public MetadataAttribute<SoundType> soundType = MetadataAttribute.constant(SoundType.STONE);
    public MetadataAttribute<Integer> maxStack = MetadataAttribute.constant(64);
    public MetadataAttribute<Integer> opacity = MetadataAttribute.constant(255);
    public MetadataAttribute<Integer> light = MetadataAttribute.constant(0);
    public MetadataAttribute<Integer> flammability = MetadataAttribute.constant(0);
    public MetadataAttribute<Integer> fireSpreadSpeed = MetadataAttribute.constant(0);
    public MetadataAttribute<Boolean> isFireSource = MetadataAttribute.constant(false);
    public MetadataAttribute<Boolean> isWood = MetadataAttribute.constant(false);
    public MetadataAttribute<Boolean> canSustainLeaves = MetadataAttribute.constant(false);
    public MetadataAttribute<Boolean> isBeaconBase = MetadataAttribute.constant(false);
    public MetadataAttribute<Float> enchantPowerBonus = MetadataAttribute.constant(0f);
    public MetadataAttribute<IntRange> expDrop = MetadataAttribute.constant(IntRange.create(0, 0));
    public MetadataAttribute<String[]> information = MetadataAttribute.constant(new String[0]);

    MetadataAttribute<ResourceLocation> itemModel = MetadataAttribute.constant(new ResourceLocation("minecraft:stick"));

    private transient boolean hasSubtypes;

    @Override
    protected final T createBlock()
    {
        hasSubtypes = subtypes.length > 0;

        if (subtypes.length == 0)
            subtypes = new int[] {0};

        if (hasSubtypes)
            return createBlockWithSubtypes();
        else
            return createBlockWithoutSubtypes();
    }

    protected abstract T createBlockWithSubtypes();

    protected abstract T createBlockWithoutSubtypes();

    @Override
    protected final Optional<Item> createItem()
    {
        Optional<Item> item = createItem(hasSubtypes);

        item.ifPresent(this::initItem);

        return item;
    }

    private void initItem(Item item)
    {
        item.setHasSubtypes(hasSubtypes);
        Arrays.stream(subtypes).forEach(meta -> itemModel.get(meta).ifPresent(model -> CustomStuff4.proxy.registerItemModel(item, meta, model)));
    }

    protected abstract Optional<Item> createItem(boolean hasSubtypes);
}