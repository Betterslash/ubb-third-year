export enum PokemonType{
    //0
    FIRE,
    //1
    WATER,
    //2
    GRASS,
    //3
    DARK,
    //4
    FAIRY,
    //5
    DRAGON,
    //6
    GHOST,
    //7
    FIGHTING,
    //8
    PSYCHIC,
    //9
    GROUND,
    //10
    ROCK,
    //11
    ICE,
    //12
    NORMAL,
    //13
    POISON,
    //14
    FLYING,
    //15
    BUG,
    //16
    ELECTRIC,
    //17
    STEEL
}

export interface PokemonTypes {
    typeOne : string;
    typeTwo? : string;
}

export interface PokemonModel {
    registeredAt: string;
    hasShiny: boolean;
    id: number;
    name : string;
    types : PokemonTypes;
    evolvesFrom: number;
    catchRate : number;
}
