import React, {useEffect, useReducer, useState} from "react";
import {PokemonModel, PokemonType} from "../model/PokemonModel";
import {useNetowrk, useUserState} from "./AppHooks";
import {PokemonOnlineService} from "../services/PokemonOnlineService";
import {LocalRepositoryService} from "../services/repository/LocalRepositoryService";
import {LocalDateService} from "../services/LocalDateService";

const createInitialState = (id : string | undefined) : PokemonModel => {
    const initialState : PokemonModel = {
        id: -1,
        hasShiny : false,
        registeredAt : '',
        name: '',
        evolvesFrom: -1,
        catchRate : 0,
        types : {typeOne : PokemonType[PokemonType.GRASS]}
    };
    if(id != null){
        initialState.id = Number(id);
    }
    return initialState;
}

const copyState = (initialState : PokemonModel) => {
    return {
        id: initialState.id,
        hasShiny : initialState.hasShiny,
        registeredAt : initialState.registeredAt,
        name: initialState.name,
        evolvesFrom: initialState.evolvesFrom,
        catchRate : initialState.catchRate,
        types : initialState.types,
        photoPath : initialState.photoPath
    } as PokemonModel;
};

export const useInitialState = (id : string | undefined) => {

    const userState = useUserState();
    const reducer = (state : any, action : any) : PokemonModel => {
        let futureState = copyState(state);
        switch (action.type){
            case 'ALL' : {futureState = copyState(action.item); return futureState;}
            case 'NAME' : {futureState.name = action.item.detail.value; return futureState;}
            case 'CATCH_RATE' : {futureState.catchRate = action.item.detail.value; return futureState;}
            case 'HAS_SHINY' : {futureState.hasShiny = action.item.detail.checked; return futureState;}
            case 'REGISTERED_AT' : {futureState.registeredAt = LocalDateService.parseToJavaLocalDate(action.item.detail.value); return futureState;}
            case 'TYPE_ONE' : {futureState.types.typeOne = PokemonType[action.item.detail.value]; return futureState;}
            case 'PHOTO_PATH': {futureState.photoPath = action.item; return futureState}
            case 'LOCATION' : {futureState.location = action.item; return futureState}
            default : {return futureState;}
        }
    }
    const [formState, dispatch] = useReducer(reducer, {} as PokemonModel);
    const [fetching, setFetchingState] = useState(false);
    const [viewOnly, setViewOnly] = useState(true);
    const isUserAdmin = () : boolean => {
        return userState.authorities.filter(e => {return e.includes('ROLE_GYM_LEADER') || e.includes('GYM_LEADER')}).length > 0;
    }
    const fetchable = useNetowrk().connected;
    useEffect(() => {
        setFetchingState(true);
        setViewOnly(isUserAdmin());
        if(id != null && id !== "-1" && id !== undefined){
            if(fetchable){
                PokemonOnlineService.getOneById(Number(id))
                    .then(result => {
                        dispatch({item : result.data, type : 'ALL'});
                        setFetchingState(false);
                    });
            }else{
                LocalRepositoryService.getOneById(Number(id))
                    .then(result => {
                        dispatch({item : result, type : 'ALL'});
                        setFetchingState(false);
                    });
            }
        }
        else{
            const initalEmptyState = createInitialState(undefined);
            dispatch({item : initalEmptyState, type : 'ALL'});
            setFetchingState(false);
        }

    }, [fetchable, userState]);
    return {pokemonReducer : {pokemon : formState, dispatcher : dispatch}, fetching : fetching, viewOnly : !viewOnly};
}


