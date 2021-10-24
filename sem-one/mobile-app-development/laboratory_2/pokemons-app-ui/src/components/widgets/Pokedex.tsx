import React, {useEffect, useState} from "react";
import {
    IonButton,
    IonButtons,
    IonContent,
    IonFab,
    IonFabButton,
    IonIcon,
    IonImg, IonInfiniteScroll, IonInfiniteScrollContent,
    IonItem,
    IonList,
    IonListHeader,
    IonLoading, IonReorder, IonReorderGroup, IonSearchbar, IonText, IonTitle
} from "@ionic/react";
import {useNetowrk, useUserState} from "../../hooks/AppHooks";
import {PokemonOnlineService} from "../../services/PokemonOnlineService";
import {PokemonModel, PokemonUserModel} from "../../model/PokemonModel";
import {add, remove} from "ionicons/icons";
import {LocalRepositoryService} from "../../services/repository/LocalRepositoryService";
import {useHistory} from "react-router";
import {Logger} from "../../helpers/logger/Logger";

export const Pokedex : React.FC = () => {

    const userState = useUserState();
    const [fetching, setFetching] = useState(false);
    const [pokemons, setPokemons] = useState([] as PokemonModel[]);
    const [pageNumber, setPageNumber] = useState(1);
    const [allData, setAllData]= useState([] as PokemonModel[]);
    const fetchable = useNetowrk().connected;
    const history = useHistory();
    const isUserAdmin = () : boolean => {
        return userState.authorities.filter(e => {return e.includes('ROLE_GYM_LEADER') || e.includes('GYM_LEADER')}).length > 0;
    }
    const navigateToModify = (id: number | string) => {
        history.push(`/pokemon/${id}`)
    }
    useEffect(() => {
        setFetching(true);
        if(userState.token !== "") {
            if (fetchable) {
                setTimeout(() => PokemonOnlineService.getAllPokemons()
                    .then(result => {
                        setPokemons(result.data);
                        setFetching(false);
                        setDisableInfiniteScroll(false);
                    }), 200);
            } else {
                setTimeout(() => {
                    LocalRepositoryService.getAllPokemons()
                        .then(result => {
                            setPokemons(result);
                            setDisableInfiniteScroll(true);
                            setPageNumber(1);
                            setFetching(false);
                        });
                }, 300);
            }
        }else{
            setFetching(false);
        }
    }, [fetchable, userState]);

    const [disableInfiniteScroll, setDisableInfiniteScroll] = useState(false);
    const filterByName = (e : any) => {
        if (e.detail.value === "") {
            setPokemons(allData);
            setDisableInfiniteScroll(false);
        } else {
            setPokemons(allData.filter(p => p.name
                .toLowerCase()
                .startsWith(e.detail.value.toLowerCase())));
        }
    }
    const searchNext = (event: CustomEvent<void>) => {
        if(fetchable){
            PokemonOnlineService.getNextPagine(pageNumber)
                .then(results => {
                    if(results.data.length < 6){
                        results.data.forEach(e => {
                            if(!pokemons.map(q => q.id).includes(e.id)){
                                pokemons.push(e);
                            }
                        });
                        setPokemons(Array.from(pokemons));
                        setPageNumber(pageNumber + 1);
                        setDisableInfiniteScroll(true);

                    }else{
                        results.data.forEach(e => {
                            if(!pokemons.map(q => q.id).includes(e.id)){
                                pokemons.push(e);
                            }
                        });
                        setPokemons(Array.from(pokemons));
                        setPageNumber(pageNumber + 1);
                    }
                    setAllData(Array.from(pokemons));
                });
        }else{
            setDisableInfiniteScroll(true);
        }
        setTimeout(() => (event.target as HTMLIonInfiniteScrollElement).complete().then(() => {}), 100);

    }
    const catchOne = (id : number) => {
        const pokemonUser = {pokemonId : id, pokemonName : ""} as PokemonUserModel;
        PokemonOnlineService.catchOnePokemon(pokemonUser)
            .then(() => Logger.info(Pokedex.name + ' -> ' + catchOne.name))
    }

    const removeOne = (id : number) => {
        PokemonOnlineService.deleteOneFromPokedex(id)
            .then(() => Logger.info(Pokedex.name + ' -> ' + removeOne.name));
    }

    return (
        <IonContent >
            <IonLoading isOpen={fetching}/>
            <IonListHeader>
                <IonTitle>
                    Available Pokemons
                </IonTitle>
            </IonListHeader>
            <IonSearchbar onIonChange={filterByName}/>
            <IonList>
            <IonReorderGroup disabled={false} onIonItemReorder={(e:any) => {e.detail.complete();}}>
                {pokemons.map(e =>
                    <IonItem key={e.id} >
                        <IonImg src={`../assets/pokemon_types/${e.types.typeOne.toLowerCase()}.png`} slot="start"/>
                        <IonText onClick={() => navigateToModify(e.id)}>{e.name}</IonText>
                        <IonButtons slot="end">
                            <IonButton onClick={() => removeOne(e.id)}>
                                <IonIcon icon={remove}/>
                            </IonButton>
                            <IonButton onClick={() => catchOne(e.id)}>
                                <IonIcon icon={add}/>
                            </IonButton>
                        </IonButtons>
                        <IonReorder key={e.id + '_reorder'} slot="end"/>
                    </IonItem>
                )}
            </IonReorderGroup>

            </IonList>
            <IonInfiniteScroll threshold="5px"
                               disabled={disableInfiniteScroll}
                               onIonInfinite={searchNext}>
                <IonInfiniteScrollContent
                    loadingText="Loading more pokemons ...">
                </IonInfiniteScrollContent>
            </IonInfiniteScroll>
            {isUserAdmin() &&
                <IonFab>
                    <IonFabButton>
                        <IonIcon onClick={() => navigateToModify("")} icon={add}/>
                    </IonFabButton>
                </IonFab>
            }
        </IonContent>);
}
