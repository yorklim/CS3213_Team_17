package sqlancer.common;

public interface TableQueryGenerator {
    void generate();

    boolean isFinished();

    Object getRandNextAction();
}
