package sqlancer.common;

public abstract class TableCreator extends FileExecutor {

    public abstract void create() throws Exception;
}
