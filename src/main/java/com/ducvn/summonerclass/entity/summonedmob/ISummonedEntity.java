package com.ducvn.summonerclass.entity.summonedmob;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;

import java.util.UUID;

public interface ISummonedEntity {
    UUID getMaster();
    void setWitherHit();
    void setPoisonHit();
    void setFireHit();
    void setSlownessHit();
    void setWeaknessHit();
    void setThorn();
    void setExplode();
    void setBuffMaster();
    void setMagnetize();
    void setSupreme();
    boolean addEffect(EffectInstance p_195064_1_);
    ModifiableAttributeInstance getAttribute(Attribute p_110148_1_);
}
