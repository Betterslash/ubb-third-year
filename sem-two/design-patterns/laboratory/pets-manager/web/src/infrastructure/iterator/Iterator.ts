import {AnimalModel} from "../../core/model/AnimalModel";

export interface Iterator<T extends AnimalModel>{
    hasNext(): boolean;
    next(): object;
}

export interface Aggregator{
    getIterator(): Iterator<AnimalModel>;
}