import React, {useEffect, useState} from "react";
import {
    IonButton,
    IonButtons,
    IonContent, IonIcon,
    IonImg,
    IonItem,
    IonList,
    IonListHeader,
    IonPage,
    IonTitle
} from "@ionic/react";
import {PokemonModel} from "../model/PokemonModel";
import {PokemonOnlineService} from "../services/PokemonOnlineService";
import {Header} from "../components/layout/Header";
import {Footer} from "../components/layout/Footer";
import {remove} from "ionicons/icons";
import {CompatClient, IMessage, Stomp} from "@stomp/stompjs";
import {NotificationModel} from "../model/NotificationModel";
import {NotificationService} from "../services/NotificationService";
import {Logger} from "../helpers/logger/Logger";
import {UserTokenService} from "../services/UserTokenService";
import jwtDecode from "jwt-decode";
import SockJS from "sockjs-client";
import {Environment} from "../environment/Environment";

let persistedPokemonsState = [] as PokemonModel[];
export const CaughtPokemons : React.FC = () => {
    const [pokemons, setPokemons] = useState(Array.from(persistedPokemonsState));
    const [subscription,] = useState(Stomp.over(() => {
        return new SockJS(Environment.webSocketUrl);
    }));
    const onSubscribe = async (stompClient : CompatClient) => {
        const result = (await UserTokenService.getToken()).value;
        //@ts-ignore
        stompClient.subscribe(`/notify/${result?.sub()}`, (e : IMessage) =>{
            const message : NotificationModel = JSON.parse(e.body);
            Logger.info(CaughtPokemons.name + ' -> message received' + JSON.stringify(message));
            persistedPokemonsState = persistedPokemonsState.filter(e => e.id !== message.body.id);
            setPokemons(persistedPokemonsState);
        });
    };
    const onError = (err: any) => {
        Logger.danger(err)
    };
    const onClose = () => {
        Logger.warning(`Subscrption closed on topic ${subscription.brokerURL}`);
    };
    useEffect(() => {
        subscription.debug = () => {};
        subscription.connect({},
            () => onSubscribe(subscription),
            (err : any) => onError(err),
            () => onClose());
        PokemonOnlineService.getMyPokemons()
            .then(result => {
                persistedPokemonsState = Array.from(result.data);
               setPokemons(result.data);
            });
    }, []);

    const releaseOnePokemon = (id:number) => {
        PokemonOnlineService.releaseOnePokemon(id)
            .then(() => {
                Logger.info(CaughtPokemons.name + ' -> ' + releaseOnePokemon.name);
                setPokemons(pokemons.filter(e => e.id !== id));
            });
    }
    return (
        <IonPage>
            <Header/>
                <IonContent>
                    <IonListHeader>
                        <IonTitle>My Pokemons</IonTitle>
                    </IonListHeader>
                    <IonList>
                        {pokemons.map(e => {return (
                            <IonItem key={e.id}>
                                <IonImg src={`../assets/pokemon_types/${e.types.typeOne.toLowerCase()}.png`} slot="start"/>
                                {e.name}
                                <IonButtons slot="end">
                                    <IonButton onClick={() => releaseOnePokemon(e.id)}>
                                        <IonIcon icon={remove}/>
                                    </IonButton>
                                </IonButtons>
                            </IonItem>
                        );})}
                    </IonList>
                </IonContent>
            <Footer/>
        </IonPage>
    );
}
