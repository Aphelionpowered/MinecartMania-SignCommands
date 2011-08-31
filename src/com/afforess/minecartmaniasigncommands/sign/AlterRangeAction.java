package com.afforess.minecartmaniasigncommands.sign;

import com.afforess.minecartmaniacore.config.MinecartManiaConfiguration;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.minecart.MinecartManiaStorageCart;
import com.afforess.minecartmaniacore.signs.Sign;
import com.afforess.minecartmaniacore.signs.SignAction;
import com.afforess.minecartmaniacore.utils.MathUtils;
import com.afforess.minecartmaniacore.utils.StringUtils;

public class AlterRangeAction implements SignAction{
	protected int range = -1;
	protected boolean itemRange = false;
	protected boolean rangeY = false;
	public AlterRangeAction(Sign sign) {
		
		for (String line : sign.getLines()) {
			if (line.toLowerCase().contains("range")) {
				String[] split = line.split(":");
				if (split.length != 2) continue;
				try {
					this.range = Integer.parseInt(StringUtils.getNumber(split[1]));
					this.range = MathUtils.range(this.range, MinecartManiaConfiguration.getMinecartMaximumRange(), 0);
				}
				catch (Exception e) {
					this.range = -1;
				}
				this.itemRange = line.toLowerCase().contains("item range");
				this.rangeY = line.toLowerCase().contains("rangey");
				sign.addBrackets();
				break;
			}
		}
	}
	
	public boolean execute(MinecartManiaMinecart minecart) {
		if (itemRange) {
			if (minecart.isStorageMinecart()) {
				((MinecartManiaStorageCart)minecart).setItemRange(this.range);
				return true;
			}
		}
		else if (rangeY) {
			minecart.setRangeY(this.range);
		}
		else {
			minecart.setRange(this.range);
			return true;
		}
		return false;
	}
	
	public boolean async() {
		return true;
	}
	
	public boolean valid(Sign sign) {
		return this.range != -1;
	}

	public String getName() {
		return "alterrangesign";
	}

	public String getFriendlyName() {
		return "Alter Range Sign";
	}

}
