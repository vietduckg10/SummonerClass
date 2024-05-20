package com.ducvn.summonerclass.utils;

import com.ducvn.summonerclass.entity.summonedmob.*;

import java.util.UUID;

public class SummonerClassUtils {
    public static boolean isTheSameMaster(ISummonedEntity targetMob, UUID master){
        return targetMob.getMaster() == master;
    }
}
