package maxpowa.aikonia.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AikoniaItems {

    public static CreativeTabs tabAikonia = (new CreativeTabs("aikonia") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Items.baked_potato;
        }

        @Override
        public int getSearchbarWidth() {
            return 100;
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }).setBackgroundImageName("aikonia.png").setNoTitle();
    
	private static ArmorMaterial materialCouncilRobes;

	private static ItemRobeBase hoodCouncil;
	private static ItemRobeBase robeCouncil;
	private static ItemRobeBase bootsCouncil;
    
	public static void init() {
		materialCouncilRobes = EnumHelper.addArmorMaterial("council", 5, new int[]{1, 3, 2, 1}, 25);
		
		GameRegistry.registerItem(hoodCouncil = new ItemRobeBase("hood_council", materialCouncilRobes, "council", 0), "hood_council");
		hoodCouncil.setCreativeTab(AikoniaItems.tabAikonia);
		GameRegistry.registerItem(robeCouncil = new ItemRobeBase("robe_council", materialCouncilRobes, "council", 1), "robe_council");
		robeCouncil.setCreativeTab(AikoniaItems.tabAikonia);
		GameRegistry.registerItem(bootsCouncil = new ItemRobeBase("boots_council", materialCouncilRobes, "council", 3), "boots_council");
		bootsCouncil.setCreativeTab(AikoniaItems.tabAikonia);
	}

}
