package sqlancer.common;

public interface TableQueryGenerator {
    public void generate();

    public boolean isFinished();

    public Object getRandNextAction();
}
