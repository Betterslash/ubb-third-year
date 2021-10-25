import {PokemonModel} from "./PokemonModel";

export interface NotificationModel{
    id : string;
    action : string;
    body : PokemonModel;
}
