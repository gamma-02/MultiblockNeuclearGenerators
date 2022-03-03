package gamma02.neucleargenerators.common.PowerCompat;

/**
 * <h1>PowerProvider</h1>
 * this class is the main class that enables the power of my mod to work, I plan to make an api later, for now I use the
 * two most(maybe) commonly used energy apis. If you want to see support of another, just submit a github issue.
 *
 * @author gamma_02
 *
 */
public interface IPowerProvider {
    /**
     * getCapacity
     * gets the capacity of a certain machine
     * @return the amount of power a given {@link net.minecraft.block.entity.BlockEntity} can output
     */
    long getCapacity();

    /**
     *
     * @return the transfer rate in units per tick
     */
    long getTransferRate();

    /**
     * Use if you need to specify something other than {@code getTransferRate()}
     * @return the maximum insert rate for a block entity
     */
    long getMaxInsert();

    /**
     * Use if you need ot specify something other than {@code getTransferRate()}
     * @return the maximum extraction rate for a block entity
     */
    long getMaxExtract();




}
