package ro.ubb.petsmanager.model.builder;

import ro.ubb.petsmanager.model.BaseEntity;

import java.util.UUID;

public abstract class Builder<T extends BaseEntity> {
    protected UUID id;
    protected abstract Builder<T> id(UUID id);
    public abstract T build();
}

