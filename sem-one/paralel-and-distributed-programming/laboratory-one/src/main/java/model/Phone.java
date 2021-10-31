package model;

import lombok.ToString;


@ToString
public class Phone extends Product{
    public Phone() {
        super(Phone.class.getName(), 100);
    }
}
