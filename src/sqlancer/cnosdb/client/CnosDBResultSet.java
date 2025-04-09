package sqlancer.cnosdb.client;

import java.io.Reader;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import sqlancer.IgnoreMeException;

public class CnosDBResultSet {
    private final Iterator<CSVRecord> records;
    private CSVRecord next;

    public CnosDBResultSet(Reader in) throws Exception {
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build()
                .parse(in);
        this.records = records.iterator();
    }

    public void close() {
    }

    public boolean next() {
        if (records.hasNext()) {
            next = records.next();
            return true;
        }
        return false;
    }

    public int getInt(int i) {
        return Integer.parseInt(next.get(i - 1));
    }

    public String getString(int i) {
        return next.get(i - 1);
    }

    public long getLong(int i) {
        if (next.get(i - 1).isEmpty()) {
            throw new IgnoreMeException();
        }
        return Long.parseLong(next.get(i - 1));
    }

}
