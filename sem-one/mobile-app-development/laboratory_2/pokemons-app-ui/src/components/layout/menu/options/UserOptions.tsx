import React, {useEffect} from "react";
import {CreateAnimation, IonCardContent, IonIcon, IonImg, IonItem, IonList} from "@ionic/react";
import {list, logOut, mail, person} from "ionicons/icons";
import {UserTokenService} from "../../../../services/UserTokenService";
import {useHistory} from "react-router";
import {useUserState} from "../../../../hooks/AppHooks";

export const UserOptions : React.FC = () => {
    const squareARef= React.createRef<CreateAnimation>();
    const history = useHistory();
    const context = useUserState();
    const logout = async () => {
        await UserTokenService.deleteToken();
        await context.setToken("");
        history.push("/");
    }
    const navigateToMyPokemons = () =>{
        history.push("/my-pokemons");
    }
    const navigateToMyProfile = () =>{
        history.push("/my-profile");
    }

    return (
        <>
        <IonList>
            <br/>
            <IonItem onClick={navigateToMyProfile}>
                <IonIcon slot={"start"} icon={person}/> My Profile
            </IonItem>

            <IonItem onClick={navigateToMyPokemons}>
               <IonIcon slot="start" icon={list}/> My Pokemons
            </IonItem>

            <IonItem onClick={logout}>
                <IonIcon slot="start" icon={logOut}/>Logout
            </IonItem>

            <IonItem>
                <IonIcon slot="start" icon={mail}/>Contact
            </IonItem>
        </IonList>
            <br/><br/>
            <CreateAnimation
                ref={squareARef}
                duration={1000}
                beforeStyles={{
                    opacity: 0.2
                }}
                afterStyles={{
                    background: 'rgba(0,0,0,0)'
                }}
                afterClearStyles={['opacity']}
                keyframes={[
                    { offset: 0, transform: 'scale(1)' },
                    { offset: 0.5, transform: 'scale(1.5)' },
                    { offset: 1, transform: 'scale(1)' }
                ]}
            >
                <IonCardContent >
                    <IonImg src={"../assets/charizard.png"} className={"animated-picture"} onClick={() => {
                        const squareA = squareARef.current!.animation;
                        squareA.play();
                    }}/>
                </IonCardContent>
            </CreateAnimation>
        </>
    );
}
