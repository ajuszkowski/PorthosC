package mousquetaires.languages.cmin.types;

import mousquetaires.languages.cmin.tokens.CminKeyword;
import mousquetaires.languages.ytree.types.InternalType;
import mousquetaires.patterns.Builder;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.Map;


public class CminPrimitiveTypeBuilder extends Builder<InternalType> {

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
    public InternalType build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        boolean signed = true;
        int bitness = 0;
        for (Map.Entry<CminKeyword, Integer> entry : keywordIdentificatorsMap.entrySet()) {
            boolean hasValue = modifiers.get(entry.getValue());
            if (hasValue) {
                CminKeyword keyword = entry.getKey();
                if (keyword == CminKeyword.Unsigned) {
                    signed = false;
                    continue;
                }
                InternalType newType = CminKeyword.tryConvert(keyword);
                if (newType == null) {
                    continue;
                }
                if (newType.bitness > bitness) {
                    bitness = newType.bitness;
                }
            }
        }
        setBuilt();
        return new InternalType(bitness, signed);
    }
}
