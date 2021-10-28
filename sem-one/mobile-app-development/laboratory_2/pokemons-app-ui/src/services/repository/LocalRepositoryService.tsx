import {PokemonModel} from "../../model/PokemonModel";
import {Storage} from "@capacitor/storage";
import {Logger} from "../../helpers/logger/Logger";
import {Environment} from "../../environment/Environment";
import {UserTokenService} from "../UserTokenService";
import axios from "axios";
import jwtDecode from "jwt-decode";

export class LocalRepositoryService {

    private static internalStorage : PokemonModel[] = [];
    private static POKEMONS_API = `${Environment.apiUrl}pokemons/synchronize`


    public static  getAllPokemons(){
        Logger.info(LocalRepositoryService.name + ' -> ' + this.getAllPokemons.name);
        return Storage.get({key:'repository'}).then(results => {
            if(results.value){
                return JSON.parse(results.value) as PokemonModel[]
            }else{
                return [] as PokemonModel[];
            }
        })

    }

    public static async insertOnePokemon(pokemon : PokemonModel){
        if(LocalRepositoryService.internalStorage.filter(e => e.id === pokemon.id).length > 0){
            LocalRepositoryService.internalStorage.forEach((e, index) => {
                if(e.id === pokemon.id){
                    pokemon.saved = false;
                    LocalRepositoryService.internalStorage[index] = pokemon;
                }
            });
            await Storage.set({key: 'repository', value: JSON.stringify(LocalRepositoryService.internalStorage)});
        }else {
            pokemon.id = -(this.internalStorage.length + 1);
            pokemon.saved = false;
            this.internalStorage.push(pokemon);
            await Storage.set({key: 'repository', value: JSON.stringify(LocalRepositoryService.internalStorage)});
            Logger.info(LocalRepositoryService.name + ' -> ' + this.insertOnePokemon.name);
        }
    }

    public static setRepositoryInternal(pokemons : PokemonModel[]) {
        (async () => {
            await Storage.set({key: 'repository', value: JSON.stringify(pokemons)})
        })();
    }

    public static async getOneById(id : number){
        const choice = LocalRepositoryService.internalStorage.filter(e => e.id === id);
        if(choice != null && choice.length > 0){
            Logger.info(LocalRepositoryService.name + ' -> ' + this.getOneById.name);
            return choice[0];
        }else {
            Logger.danger(`${LocalRepositoryService.name} -> ${this.getOneById.name} -> Cannot modify a pokemon that doesn't exist`);
            throw new Error("Cannot modify a pokemon that doesn't exist");
        }
    }

    public static async deleteOne(id : number){
        LocalRepositoryService.internalStorage = await LocalRepositoryService.getAllPokemons();
        LocalRepositoryService.internalStorage.forEach(e => {if(e.id === id){
            e.saved = false;
            e.deletionMark = true;
        }
        });
        const response = LocalRepositoryService.internalStorage;
        await Storage.set({key : 'repository', value : JSON.stringify(LocalRepositoryService.internalStorage)});
        Logger.info(LocalRepositoryService.name + ' -> ' + this.deleteOne.name);
        return response;
    }
    public static async synchronize(){
        const result = await Storage.get({key : 'repository'});
        if(result.value){
            LocalRepositoryService.internalStorage = JSON.parse(result.value);
        }
        if (LocalRepositoryService.internalStorage.length > 0) {
            return UserTokenService.getToken().then((token) => {
                // @ts-ignore
                const authorities = jwtDecode(token.value).authorities as string[];
                if (authorities.includes('ROLE_GYM_LEADER')) {
                    const headerValue = 'Bearer : ' + token.value;
                    const items = Array.from(LocalRepositoryService.internalStorage.filter(e => e.saved === false));
                    LocalRepositoryService.internalStorage.forEach(e => e.saved = true);
                    return axios.post<PokemonModel[]>(this.POKEMONS_API, items, {headers: {'Authorization': headerValue}});
                }
            });
        }
    }
}
