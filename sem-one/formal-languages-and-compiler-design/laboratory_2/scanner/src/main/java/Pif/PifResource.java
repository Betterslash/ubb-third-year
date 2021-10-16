package Pif;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@RequiredArgsConstructor
public abstract class PifResource<K, V extends Serializable> {
    protected final K key;
    protected final V value;

}
