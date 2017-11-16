package avra.hrsystem.employeemanagement.model.builder;

import avra.hrsystem.employeemanagement.model.dto.Pair;

import java.util.ArrayList;
import java.util.List;

public class PairBuilder {
    private List<Pair> pairs=new ArrayList<>();
    private Pair pair=new Pair();

    public PairBuilder setKey(String key) {
        this.pair.setKey(key);
        return this;
    }

    public PairBuilder setValue(String value) {
        this.pair.setValue(value);
        pairs.add(pair);
        pair=new Pair();
        return this;
    }

    public List<Pair> createPairs() {
        return pairs;
    }
}