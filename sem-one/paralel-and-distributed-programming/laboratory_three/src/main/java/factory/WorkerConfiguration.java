package factory;

import lombok.Data;

@Data
public final class WorkerConfiguration {
    private final ExecutionType executionType;
    private final int threadNumber;
}
