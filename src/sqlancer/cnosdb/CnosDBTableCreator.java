package sqlancer.cnosdb;

import java.io.BufferedReader;
import java.io.FileReader;

import sqlancer.Randomly;
import sqlancer.cnosdb.gen.CnosDBTableGenerator;
import sqlancer.cnosdb.query.CnosDBOtherQuery;
import sqlancer.common.TableCreator;

public class CnosDBTableCreator extends TableCreator {

    private final CnosDBGlobalState globalState;

    public CnosDBTableCreator(CnosDBGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        int numTables = Randomly.fromOptions(4, 5, 6);
        while (globalState.getSchema().getDatabaseTables().size() < numTables) {
            String tableName = String.format("m%d", globalState.getSchema().getDatabaseTables().size());
            CnosDBOtherQuery createTable = CnosDBTableGenerator.generate(tableName);
            globalState.executeStatement(createTable);
        }
    }

    public void runQueryFromFileCnos(String file, CnosDBGlobalState globalState) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String cur = br.readLine();
            while (cur != null) {
                globalState.executeStatement(new CnosDBOtherQuery(cur, null));
                cur = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
        }
    }
}
