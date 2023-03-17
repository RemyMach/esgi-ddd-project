package infrastructure;

import com.example.use_case.common.IdGenerator;

public class IdGeneratorNotRandom implements IdGenerator {
    @Override
    public String generate() {
        return "23833299328";
    }
}
