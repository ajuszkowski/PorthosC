package mousquetaires.wmm;

import mousquetaires.models.MemoryModelName;


public class MemoryModelFactory {

    public static MemoryModel getMemoryModel(MemoryModelName memoryModelName) {
        MemoryModel temp = new MemoryModel();
        Relation co = new BasicRelation("co");
        Relation po = new BasicRelation("po");
        Relation fr = new BasicRelation("fr");
        Relation rf = new BasicRelation("rf");
        Relation com = new RelUnion(new RelUnion(co, fr), rf, "com");
        Relation ghbsc = new RelUnion(po, com, "ghb-sc");

        if (memoryModelName.is(MemoryModelName.SC)) {
            temp.addAxiom(new Acyclic(ghbsc));
            return temp;
        }

        Relation poloc = new BasicRelation("poloc");
        Relation rfe = new BasicRelation("rfe");
        Relation comtso = new RelUnion(new RelUnion(co, fr), rfe, "com-tso");
        Relation WR = new BasicRelation("WR");
        Relation mfence = new BasicRelation("mfence");
        Relation potso = new RelUnion(new RelMinus(po, WR), mfence, "po-tso");
        Relation ghbtso = new RelUnion(potso, comtso, "ghb-tso");

        if (memoryModelName.is(MemoryModelName.TSO)) {
            temp.addAxiom(new Acyclic(ghbtso));
            temp.addAxiom(new Acyclic(new RelUnion(poloc, com)));
            return temp;
        }

        return null;
    }
}
