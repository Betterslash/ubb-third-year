import React, {useEffect, useState} from "react";
import {PokemonModel} from "../../model/PokemonModel";
import {PokemonOnlineService} from "../../services/PokemonOnlineService";
import {Logger} from "../../helpers/logger/Logger";
import {
    IonButton, IonButtons,
    IonCard,
    IonCardContent,
    IonCardHeader, IonHeader,
    IonIcon,
    IonImg,
    IonItem, IonLabel, IonLoading, IonThumbnail,
    IonTitle
} from "@ionic/react";
import {add, arrowForward} from "ionicons/icons";
import {useHistory} from "react-router";

export interface PokemonEvolutionInfoPorps{
    pokemonId? : number;
}

export const PokemonEvolutionInfo : React.FC<PokemonEvolutionInfoPorps> = ({pokemonId}) => {
    const [currentPokemon, setPokemonState] = useState({} as PokemonModel);
    const [fetching, setFetching] = useState(false);
    const history = useHistory();
    useEffect(() => {
        if(pokemonId !== undefined && pokemonId != null && pokemonId !== -1){
            setFetching(true);
            setTimeout(() => {
                PokemonOnlineService.getOneById(pokemonId)
                    .then((result) => {
                        setPokemonState(result.data);
                        Logger.info(PokemonEvolutionInfo.name + ' -> gotEvolution')})
            }, 300);
            setFetching(false);
        }
    }, [pokemonId]);
    const navigateToDetails = () => {
        history.push(`/pokemon/${pokemonId}`);
        window.location.reload();
    }

    return(<>
        {currentPokemon !== {} as PokemonModel && pokemonId != null && pokemonId !== -1 &&
            <>  <IonHeader>
                   <IonTitle>
                       <IonLabel>Evolves From : </IonLabel>
                   </IonTitle>
                </IonHeader>
                <IonLoading isOpen={fetching}/>
                <IonCard>
                <IonCardHeader>
                    <IonTitle>
                        {currentPokemon.name}
                    </IonTitle>
                </IonCardHeader>
                <IonCardContent>
                    {
                        currentPokemon.types !== undefined &&
                        <IonItem>
                            <IonThumbnail slot="start">
                                <IonImg src={`../assets/pokemon_types/${currentPokemon.types.typeOne.toLowerCase()}.png`} />
                            </IonThumbnail>
                            <IonButtons>
                                <IonButton onClick={navigateToDetails}>See Details<IonIcon icon={arrowForward} slot="end"/></IonButton>
                            </IonButtons>
                            <IonButtons slot="end">
                                <IonButton>
                                    <IonIcon icon={add}/>
                                </IonButton>
                            </IonButtons>
                        </IonItem>
                    }
                </IonCardContent>
            </IonCard></>
        }
    </>);
}
