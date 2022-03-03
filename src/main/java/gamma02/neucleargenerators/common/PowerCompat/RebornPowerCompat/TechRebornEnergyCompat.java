package gamma02.neucleargenerators.common.PowerCompat.RebornPowerCompat;

import gamma02.neucleargenerators.common.PowerCompat.IPowerProvider;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import team.reborn.energy.api.EnergyStorage;

import java.net.InterfaceAddress;

public class TechRebornEnergyCompat {
    public Object getEnergyStorageFor(BlockEntity entity){
        if(FabricLoader.getInstance().isModLoaded("team_reborn_energy") && entity instanceof IPowerProvider power){
            return new EnergyStorage() {
                @Override
                public long insert(long maxAmount, TransactionContext transaction) {
                    return 0;
                }

                @Override
                public long extract(long maxAmount, TransactionContext transaction) {
                    return 0;
                }

                @Override
                public long getAmount() {
                    return 0;
                }

                @Override
                public long getCapacity() {
                    return 0;
                }
            };
        }
        return null;
    }
}
