import axios, {AxiosResponse} from "axios";
import {Environment} from "../environment/Environment";
import {PokemonModel} from "../model/PokemonModel";
import {StorageService} from "./StorageService";


export class PokemonsService{
    private static readonly POKEMONS_API = "pokemons";

    public static async getAllPokemons(): Promise<AxiosResponse<PokemonModel[]>> {
        const token = await StorageService.getToken();
        // @ts-ignore
        axios.defaults.headers.get['Authorization'] = 'Bearer : ' + token.value;
        return axios.get<PokemonModel[]>(`${Environment.apiUrl}${this.POKEMONS_API}`);
    }
}
