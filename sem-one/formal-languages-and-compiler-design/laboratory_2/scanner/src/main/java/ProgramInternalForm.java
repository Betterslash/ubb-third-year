import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProgramInternalForm {
    private final List<PifPair> internalRepresentaion;

    public ProgramInternalForm() {
        this.internalRepresentaion = new ArrayList<>();
    }

    public void add(PifPair pair){
        internalRepresentaion.add(pair);
    }
}
