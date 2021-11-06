import axios, {AxiosResponse} from "axios";
import {Environment} from "../environment/Environment";
import {PokemonModel, PokemonUserModel} from "../model/PokemonModel";
import {UserTokenService} from "./UserTokenService";


export class PokemonOnlineService {
    private static readonly POKEMONS_API = "pokemons";
    public static async deleteOneFromPokedex(id: number): Promise<AxiosResponse<PokemonModel>> {
        const token = await UserTokenService.getToken();
        const headerValue = 'Bearer : ' + token.value;
        //console.log(headerValue);
        return axios.delete(`${Environment.apiUrl}${this.POKEMONS_API}/${id}`, {data: null, headers: {'Authorization': headerValue}});
    }
    public static async getAllPokemons(hasFilters?: boolean): Promise<AxiosResponse<PokemonModel[]>> {
        const token = await UserTokenService.getToken();
        // @ts-ignore
        axios.defaults.headers.get['Authorization'] = 'Bearer : ' + token.value;
        if(!hasFilters){
            return axios.get<PokemonModel[]>(`${Environment.apiUrl}${this.POKEMONS_API}/paginated/0?size=10`);
        }else{
            return axios.get<PokemonModel[]>(`${Environment.apiUrl}${this.POKEMONS_API}`);
        }
    }
    public static async getOneById(id: number): Promise<AxiosResponse<PokemonModel>> {
        const token = await UserTokenService.getToken();
        // @ts-ignore
        axios.defaults.headers.get['Authorization'] = 'Bearer : ' + token.value;
        return axios.get<PokemonModel>(`${Environment.apiUrl}${this.POKEMONS_API}/${id}`);
    }
    public static async  getNextPagine(pagine : number){
        const token = await UserTokenService.getToken();
        // @ts-ignore
        axios.defaults.headers.get['Authorization'] = 'Bearer : ' + token.value;
        return axios.get<PokemonModel[]>(`${Environment.apiUrl}${this.POKEMONS_API}/paginated/${pagine}?size=6`);
    }
    public static async catchOnePokemon(pokemonUserModel: PokemonUserModel) {
        const token = await UserTokenService.getToken();
        const headerValue = 'Bearer : ' + token.value;
        //console.log(headerValue);
        return axios.post(`${Environment.apiUrl}${this.POKEMONS_API}/catch`, pokemonUserModel, {headers: {'Authorization': headerValue}})
    }
    public static async updateOnePokemon(id : number, pokemon: PokemonModel) : Promise<AxiosResponse<PokemonModel>>{
        const token = await UserTokenService.getToken();
        const headerValue = 'Bearer : ' + token.value;
        //console.log(headerValue);
        return axios.put(`${Environment.apiUrl}${this.POKEMONS_API}/${id}`, pokemon ,{headers: {'Authorization' : headerValue} });
    }
    public static async getMyPokemons(): Promise<AxiosResponse<PokemonModel[]>> {
        const token = await UserTokenService.getToken();
        // @ts-ignore
        axios.defaults.headers.get['Authorization'] = 'Bearer : ' + token.value;
        return axios.get<PokemonModel[]>(`${Environment.apiUrl}${this.POKEMONS_API}/caught`);
    }
    public static async releaseOnePokemon(id  : number) : Promise<AxiosResponse<PokemonModel>>{
        const token = await UserTokenService.getToken();
        const headerValue = 'Bearer : ' + token.value;
        //console.log(headerValue);
        return axios.delete(`${Environment.apiUrl}${this.POKEMONS_API}/release/${id}`, {data: null, headers: {'Authorization': headerValue}});
    }

}
