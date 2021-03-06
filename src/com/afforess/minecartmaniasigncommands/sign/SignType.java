package com.afforess.minecartmaniasigncommands.sign;

import java.lang.reflect.Constructor;

import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.signs.Sign;
import com.afforess.minecartmaniacore.signs.SignAction;

public enum SignType {
    SetStationSign(SetStationAction.class),
    StopAtDestinationSign(StopAtDestinationAction.class),
    LockCartSign(LockCartAction.class),
    UnlockCartSign(UnlockCartAction.class),
    AutoSeedSign(GenericAction.class, "AutoSeed"),
    AutoTillSign(GenericAction.class, "AutoTill"),
    AutoHarvestSign(GenericAction.class, "AutoHarvest"),
    AutoTimberSign(GenericAction.class, "AutoTimber"),
    AutoForestSign(GenericAction.class, "AutoForest"),
    AutoSugarSign(GenericAction.class, "AutoSugar"),
    AutoPlantSign(GenericAction.class, "AutoPlant"),
    AutoCactusSign(GenericAction.class, "AutoCactus"),
    AutoReCactusSign(GenericAction.class, "AutoReCactus"),
    AutoSeedOffSign(GenericAction.class, "Seed Off", "AutoSeed", null),
    AutoTillOffSign(GenericAction.class, "Till Off", "AutoTill", null),
    AutoHarvestOffSign(GenericAction.class, "Harvest Off", "AutoHarvest", null),
    AutoTimberOffSign(GenericAction.class, "Timber Off", "AutoTimber", null),
    AutoForestOffSign(GenericAction.class, "Forest Off", "AutoForest", null),
    AutoSugarOffSign(GenericAction.class, "Sugar Off", "AutoSugar", null),
    AutoPlantOffSign(GenericAction.class, "Plant Off", "AutoPlant", null),
    AutoCactusOffSign(GenericAction.class, "Cactus Off", "AutoCactus", null),
    AutoReCactusOffSign(GenericAction.class, "ReCactus Off", "AutoReCactus", null),
    AlterRangeSign(AlterRangeAction.class),
    SetMaximumSpeedSign(SetMaxSpeedAction.class),
    EjectionSign(EjectionAction.class),
    AnnouncementSign(AnnouncementAction.class),
    HoldingForSign(HoldingForAction.class),
    ElevatorSign(ElevatorAction.class),
    PassPlayerSign(PassPlayerAction.class),
    EjectAtSign(EjectAtAction.class),
    EjectionConditionAction(EjectionConditionAction.class),
    
    ;
    SignType(final Class<? extends SignAction> action) {
        this.action = action;
        setting = null;
        key = null;
        value = null;
    }
    
    SignType(final Class<? extends SignAction> action, final String setting) {
        this.action = action;
        this.setting = setting;
        key = null;
        value = null;
    }
    
    SignType(final Class<? extends SignAction> action, final String setting, final String key, final Object value) {
        this.action = action;
        this.setting = setting;
        this.key = key;
        this.value = value;
    }
    
    private final Class<? extends SignAction> action;
    private final String setting;
    private final String key;
    private final Object value;
    
    public Class<? extends SignAction> getSignClass() {
        return action;
    }
    
    public SignAction getSignAction(final Sign sign) {
        try {
            
            Constructor<? extends SignAction> constructor;
            SignAction action;
            if (setting == null) {
                constructor = this.action.getConstructor(Sign.class);
                action = constructor.newInstance(sign);
            } else if (key == null) {
                constructor = this.action.getConstructor(String.class);
                action = constructor.newInstance(setting);
            } else {
                constructor = this.action.getConstructor(String.class, String.class, Object.class);
                action = constructor.newInstance(setting, key, value);
            }
            return action;
        } catch (final Exception e) {
            MinecartManiaLogger.getInstance().severe("Failed to read sign!");
            MinecartManiaLogger.getInstance().severe("Sign was :" + action);
            e.printStackTrace();
        }
        return null;
    }
}
