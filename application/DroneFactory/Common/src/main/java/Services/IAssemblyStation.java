package Services;

public interface IAssemblyStation {

    /**
     * Starts the construction on the Assembly Station
     */
    void construct();

    /**
     * Checks for a healthy produced product
     * @return false if unhealthy and true if healthy
     */
    boolean checkProductHealth();

    /**
     * Checks if the production has finished
     * @return true if the production finished and false if not
     */
    boolean isDone();

    /**
     * Checks if currently constructing
     * @return true if construction and false if not
     */
    boolean isConstructing();

    /**
     * Gets the current state of the Assembly Station
     * @return the current state of the Assembly station
     */
    String getState();
}
