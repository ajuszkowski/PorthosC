package mousquetaires.languages.cmin.types;

import mousquetaires.languages.cmin.CminKeyword;
import mousquetaires.languages.internal.types.Bitness;
import mousquetaires.languages.internal.types.BitvectorType;
import mousquetaires.languages.internal.types.Type;
import mousquetaires.patterns.Builder;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.Map;


public class CminPrimitiveTypeBuilder extends Builder<Type> {

    private final int modifiersNumber = CminKeyword.values().length;
    private BitSet modifiers = new BitSet(modifiersNumber);

    private final Map<CminKeyword, Integer> keywordIdentificatorsMap;

    public CminPrimitiveTypeBuilder() {
        keywordIdentificatorsMap = new EnumMap<>(CminKeyword.class);
        int id = 1;
        for (CminKeyword keyword : CminKeyword.values()) {
            keywordIdentificatorsMap.put(keyword, id++);
        }
    }

    public void addModifier(CminKeyword keyword) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        Integer modifierId = keywordIdentificatorsMap.get(keyword);
        if (modifierId == null) {
            throw new IllegalArgumentException(keyword.name());
        }
        modifiers.set(modifierId, true);
    }

    @Override
    public Type build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        boolean signed = true;
        Bitness bitness = Bitness.getMinBitness();
        for (Map.Entry<CminKeyword, Integer> entry : keywordIdentificatorsMap.entrySet()) {
            boolean hasValue = modifiers.get(entry.getValue());
            if (hasValue) {
                CminKeyword keyword = entry.getKey();
                if (keyword == CminKeyword.Unsigned) {
                    signed = false;
                }
                Bitness newBitness = CminKeyword.bitnessMap.get(keyword);
                if (newBitness == null) {
                    continue;
                }
                if (newBitness.value > bitness.value) {
                    bitness = newBitness;
                }
            }
        }
        setBuilt();
        return new BitvectorType(bitness, signed);
    }
}
