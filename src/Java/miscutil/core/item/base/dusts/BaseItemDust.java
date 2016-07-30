package miscutil.core.item.base.dusts;

import static miscutil.core.creative.AddToCreativeTab.tabMisc;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

import java.util.List;

import miscutil.core.lib.CORE;
import miscutil.core.lib.MaterialInfo;
import miscutil.core.util.Utils;
import miscutil.core.util.item.UtilsItems;
import miscutil.core.util.recipe.UtilsRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.registry.GameRegistry;

public class BaseItemDust extends Item{

	protected int colour;
	protected String materialName;
	protected String pileType;
	protected boolean useBlastFurnace;
	String name = "";
	private int mTier;
	private MaterialInfo dustInfo;

	public BaseItemDust(String unlocalizedName, String materialName, MaterialInfo matInfo, int colour, String pileSize, boolean blastFurnaceRequired, int tier) {
		setUnlocalizedName(unlocalizedName);
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxStackSize(64);	
		if (pileSize == "dust" || pileSize == "Dust"){
			this.setTextureName(CORE.MODID + ":" + "dust");}
		else{
			this.setTextureName(CORE.MODID + ":" + "dust"+pileSize);}
		this.setMaxStackSize(64);
		this.setCreativeTab(tabMisc);
		this.colour = colour;
		this.mTier = tier;
		this.materialName = materialName;
		this.useBlastFurnace = blastFurnaceRequired;
		this.dustInfo = matInfo;
		GameRegistry.registerItem(this, unlocalizedName);

		String temp = "";
		Utils.LOG_WARNING("Unlocalized name for OreDict nameGen: "+getUnlocalizedName());
		if (getUnlocalizedName().contains("item.")){
			temp = getUnlocalizedName().replace("item.", "");
			Utils.LOG_WARNING("Generating OreDict Name: "+temp);
		}
		else {
			temp = getUnlocalizedName();
		}
		if (temp.contains("DustTiny")){
			temp = temp.replace("itemD", "d");
			Utils.LOG_WARNING("Generating OreDict Name: "+temp);
		}
		else if (temp.contains("DustSmall")){
			temp = temp.replace("itemD", "d");
			Utils.LOG_WARNING("Generating OreDict Name: "+temp);
		}
		else {
			temp = temp.replace("itemD", "d");
			Utils.LOG_WARNING("Generating OreDict Name: "+temp);
		}		
		if (temp != null && temp != ""){
			GT_OreDictUnificator.registerOre(temp, UtilsItems.getSimpleStack(this));
		}
		addMixerRecipe();
		addFurnaceRecipe();
		addMacerationRecipe();
	}

	@Override
	public String getItemStackDisplayName(ItemStack iStack) {

		if (getUnlocalizedName().contains("DustTiny")){
			name = "Tiny Pile of "+materialName + " Dust";
		}
		else if (getUnlocalizedName().contains("DustSmall")){
			name = "Small Pile of "+materialName + " Dust";
		}
		else {
			name = materialName + " Dust";
		}
		return name;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer aPlayer, List list, boolean bool) {
		if (pileType != null && materialName != null && pileType != "" && materialName != "" && !pileType.equals("") && !materialName.equals("")){
			if (this.pileType == "dust"){
				list.add(EnumChatFormatting.GRAY+"A pile of " + materialName + " dust.");		
			}
			else{
				list.add(EnumChatFormatting.GRAY+"A "+this.pileType.toLowerCase()+" pile of " + materialName + " dust.");		
			}
		}
		super.addInformation(stack, aPlayer, list, bool);
	}

	public final String getMaterialName() {
		return materialName;
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int HEX_OxFFFFFF) {
		if (colour == 0){
			return Utils.generateSingularRandomHexValue();
		}
		return colour;

	}

	private void addMixerRecipe(){
		ItemStack tempStack = UtilsItems.getSimpleStack(this);
		ItemStack tempOutput = null;
		ItemStack[] inputStacks = dustInfo.getInputs();
		ItemStack[] outputStacks = dustInfo.getOutputs();
		String temp = "";
		Utils.LOG_WARNING("Unlocalized name for OreDict nameGen: "+getUnlocalizedName());
		if (getUnlocalizedName().contains("item.")){
			temp = getUnlocalizedName().replace("item.", "");
			Utils.LOG_WARNING("Generating OreDict Name: "+temp);
		}
		else {
			temp = getUnlocalizedName();
		}
		if (temp.contains("DustTiny")){
			temp = temp.replace("itemDustTiny", "dust");
			Utils.LOG_WARNING("Generating OreDict Name: "+temp);
		}
		else if (temp.contains("DustSmall")){
			temp = temp.replace("itemDustSmall", "dust");
			Utils.LOG_WARNING("Generating OreDict Name: "+temp);
		}
		else {
			temp = temp.replace("itemD", "d");
			Utils.LOG_WARNING("Generating OreDict Name: "+temp);
		}		
		if (temp != null && temp != ""){
			
			if (getUnlocalizedName().contains("DustTiny") || getUnlocalizedName().contains("DustSmall")){
				tempOutput = UtilsItems.getItemStackOfAmountFromOreDict(temp, 1);
			}
			else {				
				if (outputStacks[0] != null){
					Utils.LOG_INFO("Getting output dusts for mixer recipe. Checking ENUM, got: "+outputStacks[0].toString());
					tempOutput = outputStacks[0];
				}
				else {
					Utils.LOG_INFO("Getting output dusts for mixer recipe. Enum check failed, failback item is: "+temp);
					tempOutput = UtilsItems.getItemStackOfAmountFromOreDict(temp, 1);
				}
			}
			
		}

		if (tempOutput != null){
			if (getUnlocalizedName().contains("DustTiny")){
				Utils.LOG_WARNING("Generating a 9 Tiny dust to 1 Dust recipe for "+materialName);
				UtilsRecipe.addShapelessGregtechRecipe(tempOutput,
						tempStack, tempStack, tempStack,
						tempStack, tempStack, tempStack,
						tempStack, tempStack, tempStack);
			}
			else if (getUnlocalizedName().contains("DustSmall")){
				Utils.LOG_WARNING("Generating a 4 Small dust to 1 Dust recipe for "+materialName);
				UtilsRecipe.addShapelessGregtechRecipe(tempOutput,
						tempStack, tempStack, null,
						tempStack, tempStack, null,
						null, null, null);
			}
			else {
				Utils.LOG_WARNING("Generating a shapeless Dust recipe for "+materialName);		

				GT_Values.RA.addMixerRecipe(
						inputStacks[0], inputStacks[1],
						inputStacks[2], inputStacks[3],
						null, null,
						tempOutput,
						8*mTier*20, 8*mTier*2);
			}
		}

	}

	private void addMacerationRecipe(){
		Utils.LOG_INFO("Adding recipe for "+materialName+" Dusts");

		String tempIngot = getUnlocalizedName().replace("item.itemDust", "ingot");
		String tempDust = getUnlocalizedName().replace("item.itemDust", "dust");
		ItemStack tempInputStack;
		ItemStack tempOutputStack;

		if (getUnlocalizedName().contains("DustSmall") || getUnlocalizedName().contains("DustTiny")){
			return;
		}

		Utils.LOG_WARNING("Unlocalized name for OreDict nameGen: "+getUnlocalizedName());
		if (getUnlocalizedName().contains("item.")){
			tempIngot = getUnlocalizedName().replace("item.", "");
			Utils.LOG_WARNING("Generating OreDict Name: "+tempIngot);
		}
		else {
			tempIngot = getUnlocalizedName();
		}

		tempIngot = tempIngot.replace("itemDust", "ingot");
		Utils.LOG_WARNING("Generating OreDict Name: "+tempIngot);
		ItemStack[] outputStacks = dustInfo.getOutputs();
		if (tempIngot != null && tempIngot != "" && outputStacks[1] != null){
			tempInputStack = UtilsItems.getItemStackOfAmountFromOreDict(tempIngot, 1);
			tempOutputStack = UtilsItems.getItemStackOfAmountFromOreDict(tempDust, 1);
			if (null != tempOutputStack && null != tempInputStack){
				GT_ModHandler.addPulverisationRecipe(tempInputStack, outputStacks[0], outputStacks[1], mTier*10/Utils.randInt(10, 20));
			}
		}	
		if (tempIngot != null && tempIngot != ""){
			tempInputStack = UtilsItems.getItemStackOfAmountFromOreDict(tempIngot, 1);
			tempOutputStack = UtilsItems.getItemStackOfAmountFromOreDict(tempDust, 1);
			if (null != tempOutputStack && null != tempInputStack){
				GT_ModHandler.addPulverisationRecipe(tempInputStack, tempOutputStack);
			}
		}
	}

	private void addFurnaceRecipe(){		

		String temp = "";
		if (getUnlocalizedName().contains("item.")){
			temp = getUnlocalizedName().replace("item.", "");
		}
		else {
			temp = getUnlocalizedName();
		}
		if (temp.contains("DustTiny") || temp.contains("DustSmall")){
			return;
		}
		temp = temp.replace("itemDust", "ingot");		
		if (temp != null && temp != ""){

			if (this.useBlastFurnace){
				Utils.LOG_INFO("Adding recipe for Hot "+materialName+" Ingots in a Blast furnace.");
				String tempIngot = temp.replace("ingot", "ingotHot");
				ItemStack tempOutputStack = UtilsItems.getItemStackOfAmountFromOreDict(tempIngot, 1);
				Utils.LOG_INFO("This will produce "+tempOutputStack.getDisplayName() + " Debug: "+tempIngot);
				if (null != tempOutputStack){
					addBlastFurnaceRecipe(UtilsItems.getSimpleStack(this), null, tempOutputStack, null, 350*mTier);		
				}				
				return;
			}
			Utils.LOG_INFO("Adding recipe for "+materialName+" Ingots in a furnace.");
			ItemStack tempOutputStack = UtilsItems.getItemStackOfAmountFromOreDict(temp, 1);
			Utils.LOG_INFO("This will produce an ingot of "+tempOutputStack.getDisplayName() + " Debug: "+temp);
			if (null != tempOutputStack){
				if (mTier < 5){
					if (CORE.MAIN_GREGTECH_5U_EXPERIMENTAL_FORK){
						addSmeltingAndAlloySmeltingRecipe(UtilsItems.getSimpleStack(this), tempOutputStack, false);						
					}
					else {
						GT_ModHandler.addSmeltingAndAlloySmeltingRecipe(UtilsItems.getSimpleStack(this), tempOutputStack);						
					}		
				}	
				else if (mTier >= 5){
					Utils.LOG_INFO("Adding recipe for "+materialName+" Ingots in a Blast furnace.");
					Utils.LOG_INFO("This will produce "+tempOutputStack.getDisplayName());
					if (null != tempOutputStack){
						addBlastFurnaceRecipe(UtilsItems.getSimpleStack(this), null, tempOutputStack, null, 350*mTier);		
					}				
					return;				
				}
			}

		}	
	}

	private void addBlastFurnaceRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, int tempRequired){
		//Special Cases
		if (input1.getUnlocalizedName().toLowerCase().contains("tantalloy61")){
			input2 = UtilsItems.getItemStackOfAmountFromOreDict("dustTantalloy60", 2);
		}
		GT_Values.RA.addBlastRecipe(
				input1,
				input2,
				GT_Values.NF, GT_Values.NF,
				output1,
				output2,
				250*mTier*20,
				mTier*64, 
				tempRequired);
		
		
		
	}

	private boolean addSmeltingAndAlloySmeltingRecipe(ItemStack aInput, ItemStack aOutput, boolean hidden) {
		if (aInput == null || aOutput == null) return false;
		boolean temp = false;
		if (aInput.stackSize == 1 && GT_ModHandler.addSmeltingRecipe(aInput, aOutput)) temp = true;
		if (GT_Values.RA.addAlloySmelterRecipe(aInput, OrePrefixes.ingot.contains(aOutput) ? ItemList.Shape_Mold_Ingot.get(0) : OrePrefixes.block.contains(aOutput) ? ItemList.Shape_Mold_Block.get(0) : OrePrefixes.nugget.contains(aOutput) ? ItemList.Shape_Mold_Nugget.get(0) : null, aOutput, 130, 3))
			temp = true;
		if (GT_ModHandler.addInductionSmelterRecipe(aInput, null, aOutput, null, aOutput.stackSize * 1600, 0)) temp = true;
		return temp;
	}

}