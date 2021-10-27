import React, {useContext, useEffect, useState} from "react";
import {
    IonButton,
    IonButtons, IonCardContent, IonChip,
    IonContent,
    IonIcon,
    IonImg, IonInfiniteScroll, IonInfiniteScrollContent,
    IonItem,
    IonList,
    IonListHeader,
    IonLoading, IonReorder, IonReorderGroup, IonSearchbar, IonText, IonTitle, IonToolbar, useIonModal
} from "@ionic/react";
import {useNetowrk} from "../../hooks/AppHooks";
import {PokemonOnlineService} from "../../services/PokemonOnlineService";
import {PokemonModel, PokemonUserModel} from "../../model/PokemonModel";
import {add, checkmark, closeCircle, closeSharp, filter, remove} from "ionicons/icons";
import {LocalRepositoryService} from "../../services/repository/LocalRepositoryService";
import {useHistory} from "react-router";
import {Logger} from "../../helpers/logger/Logger";
import {CompatClient, IMessage, Stomp} from "@stomp/stompjs";
import {NotificationModel} from "../../model/NotificationModel";
import SockJS from "sockjs-client";
import {Environment} from "../../environment/Environment";
import {NotificationService} from "../../services/NotificationService";
import {AppContext} from "../../context/AppContext";
import {FilterModel, FiltersModalBody} from "../modal/body/FiltersModalBody";

export interface PokedexProps {
    token : string;
}

export const Pokedex : React.FC<PokedexProps> = ({token}) => {

    const [fetching, setFetching] = useState(false);
    const [applyFilters, setApplyFilters] = useState(false);
    const [pokemons, setPokemons] = useState([] as PokemonModel[]);
    const [pageNumber, setPageNumber] = useState(1);
    const [allData, setAllData]= useState([] as PokemonModel[]);
    const fetchable = useNetowrk().connected;
    const history = useHistory();
    const [subscription,] = useState(Stomp.over(() => {
        return new SockJS(Environment.webSocketUrl+'?access_token='+token);
    }));
    const navigateToModify = (id: number | string) => {
        history.push(`/pokemon/${id}`)
    }
    const onSubscribe = (stompClient : CompatClient) => {
        stompClient.subscribe("/notify", (e : IMessage) =>{
            const message : NotificationModel = JSON.parse(e.body);
            NotificationService.addNotification(message)
                .then(() => {
                    Logger.info(`Got notifications -> ${JSON.stringify(message.action)}`);
                    console.log(notificationsState);
                    notificationsState.stateChange(notificationsState.notifications.concat(message));
                });
        });
    };
    const onError = (err: any) => {
        Logger.danger(err)
    };
    const onClose = () => {
        Logger.warning(`Subscrption closed on topic ${subscription.brokerURL}`);
    };
    const notificationsState = useContext(AppContext);
    useEffect(() => {
        setFetching(true);
            if (fetchable) {
                if(token !== ""){
                    subscription.debug = ()=> {};

                    subscription.connect({ Authorization : 'Bearer : ' + token},
                        () => onSubscribe(subscription),
                        (err : any) => onError(err),
                        () => onClose());
                }
                setTimeout(() => PokemonOnlineService.getAllPokemons(applyFilters)
                    .then(result => {
                        setPokemons(applyFiltering(result.data));
                        setAllData(result.data);
                        setFetching(false);
                        if(applyFilters) {
                            setDisableInfiniteScroll(true);
                        }else{
                            setDisableInfiniteScroll(false);
                        }
                    }), 200);
            } else {
                setTimeout(() => {
                    subscription.disconnect();
                    LocalRepositoryService.getAllPokemons()
                        .then(result => {
                            setPokemons(applyFiltering(result));
                            setAllData(result);
                            setDisableInfiniteScroll(true);
                            setPageNumber(1);
                            setFetching(false);
                        });
                }, 300);
            }


    }, [fetchable, applyFilters ]);
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
                        pokemons.forEach(e => e.saved = true);
                        LocalRepositoryService.setRepositoryInternal(pokemons);
                        setPokemons(applyFiltering(Array.from(pokemons)));
                        setPageNumber(pageNumber + 1);
                        setDisableInfiniteScroll(true);

                    }else{
                        results.data.forEach(e => {
                            if(!pokemons.map(q => q.id).includes(e.id)){
                                pokemons.push(e);
                            }
                        });
                        setPokemons(applyFiltering(Array.from(pokemons)));
                        pokemons.forEach(e => e.saved = true);
                        LocalRepositoryService.setRepositoryInternal(pokemons);
                        setPageNumber(pageNumber + 1);
                    }
                    setAllData(Array.from(pokemons));
                });
        }else{
            setDisableInfiniteScroll(true);
        }
        setTimeout(() => (event.target as HTMLIonInfiniteScrollElement).complete().then(() => {}), 200);

    }
    const catchOne = (id : number) => {
        const pokemonUser = {pokemonId : id, pokemonName : ""} as PokemonUserModel;
        PokemonOnlineService.catchOnePokemon(pokemonUser)
            .then(() => Logger.info(Pokedex.name + ' -> ' + catchOne.name))
    }
    const removeOne = (id : number) => {
        if(fetchable){
            setFetching(true);
            PokemonOnlineService.deleteOneFromPokedex(id)
                .then(() => {
                    Logger.info(Pokedex.name + ' -> ' + removeOne.name);
                    pokemons.forEach(e => e.saved = true);
                    LocalRepositoryService.setRepositoryInternal(pokemons.filter(p => p.id !== id));
                    setPokemons(pokemons.filter(p => p.id !== id));
                    setFetching(false);
                });
        }else{
            LocalRepositoryService.deleteOne(id)
                .then((result) => {
                    if(result) {
                        setPokemons(result);
                    }
                });
        }
    }
    const handleDismiss = () => {
        dismiss();
    };
    const addFilter = (filter: FilterModel) => {
        setFilters(filters.concat(filter));
    }
    const [present, dismiss] = useIonModal(FiltersModalBody, {
        onDismiss : handleDismiss,
        addFilter: addFilter
    });
    const [filters, setFilters] = useState([] as FilterModel[]);
    const deleteOne =(id : number) =>{
        setFilters(filters.filter((e, index)=> index !== id));
    };

    const applyFiltering = (data : PokemonModel[]) => {
        let results : PokemonModel[] = data;
        if(filters.length > 0 && applyFilters){
            filters.forEach((e => {
                console.log(e);
                if(e.field === 'Type'){
                    if(e.comparator === 'is'){
                        results = results.filter(q=> q.types.typeOne === e.value)
                    }
                    if(e.comparator === "isn't"){
                        results = results.filter(q => q.types.typeOne !== e.value);
                    }
                }
                if(e.field === 'Catch Rate'){
                    if(e.comparator === 'bigger than'){
                        results = results.filter(q => q.catchRate > Number(e.value));
                    }
                    if(e.comparator === 'lower than'){
                        results = results.filter(q => q.catchRate < Number(e.value));
                    }
                    if(e.comparator === 'equal to'){
                        results = results.filter(q => q.catchRate === Number(e.value));
                    }
                }
            }));
        }
        return results;
    };
    const getApplyFiltersIcon = () =>{
        if(!applyFilters){
            return checkmark
        }else{
            return closeSharp;
        }
    };
    return (
        <IonContent>
        <IonCardContent >
            <IonLoading isOpen={fetching}/>
            <IonListHeader>
                <IonTitle>
                    Available Pokemons
                </IonTitle>
            </IonListHeader>
            <IonSearchbar onIonChange={filterByName}/>
            <IonToolbar>
                <IonButtons slot="start" onClick={() => {present()}}>
                    <IonButton>
                        <IonIcon icon={filter} />
                    </IonButton>
                </IonButtons>
                <IonList>
                    {filters.map((e,index) => (<IonChip key={index}>{`${e.field} ${e.comparator} ${e.value}`}<IonIcon icon={closeCircle} onClick={() => {deleteOne(index)}}/></IonChip>))}
                </IonList>
                <IonButtons slot="end">
                    <IonButton onClick={() => {setApplyFilters(!applyFilters); setPageNumber(1);}}>
                        <IonIcon icon={getApplyFiltersIcon()}/>
                    </IonButton>
                </IonButtons>
            </IonToolbar>
            <IonList>
            <IonReorderGroup disabled={false} onIonItemReorder={(e:any) => {e.detail.complete();}}>
                {pokemons.map(e =>
                    <IonItem key={e.id} disabled={e.deletionMark != null && e.deletionMark}>
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
            <IonInfiniteScroll threshold="10px"
                               disabled={disableInfiniteScroll}
                               onIonInfinite={searchNext}>
                <IonInfiniteScrollContent
                    loadingText="Loading more pokemons ...">
                </IonInfiniteScrollContent>
            </IonInfiniteScroll>
        </IonCardContent>
        </IonContent>
    );
}
