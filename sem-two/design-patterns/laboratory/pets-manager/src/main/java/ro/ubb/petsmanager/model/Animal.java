package ro.ubb.petsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class Animal extends BaseEntity{
    protected String name;
    protected String race;
    protected Long age;
    protected boolean available;
    protected String owner;

    @ElementCollection
    private List<String> imageUrls;
}
