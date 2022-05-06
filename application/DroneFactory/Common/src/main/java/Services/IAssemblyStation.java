package Services;

public interface IAssemblyStation {
    void construct();
    void checkProduct();
    boolean isDone();
    boolean isConstructing();
    String getState();
}
