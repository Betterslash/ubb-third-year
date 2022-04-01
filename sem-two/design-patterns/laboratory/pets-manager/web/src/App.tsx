import React, {useEffect, useState} from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {HomePage} from './pages/HomePage';
import {Card, Container, Row} from "reactstrap";
import {HeaderComponent} from './components/layout/HeaderComponent';
import {FooterComponent} from './components/layout/FooterComponent';
import {UserContext, UserContextProps} from './context/UserContext';
import {UserModel} from "./core/model/UserModel";
import {ContextService} from "./infrastructure/services/ContextService";
import {Environment} from "./core/environment/Environment";
import {ViewAnimalPage} from "./pages/ViewAnimalPage";

function App() {
    const [currentUser, setCurrentUser] = useState({} as UserModel);
    const [loggedIn, setLoggedIn] = useState(false);
    const currentUserState = {
        currentUser: currentUser,
        changeUserState: (value) => {
            if (value !== undefined && JSON.stringify(value) !== "{}") {
                localStorage.setItem('current-user', JSON.stringify(currentUser));
                setCurrentUser(value);
            }
        },
        loggedIn: ContextService.isAuthenticated(),
        setLoggedInState: setLoggedIn
    } as UserContextProps;
    useEffect(() => {
    }, [currentUser, loggedIn]);
    return (
        <div style={{
            height: "100vh",
            backgroundImage: `url("${Environment.localUrl}background.png")`,
            backgroundRepeat: 'no-repeat',
        }}>
            <Container>
                <br/>
                <UserContext.Provider value={currentUserState}>
                    <Row>
                        <Card>
                            <HeaderComponent/>
                        </Card>
                    </Row>
                    <br/>
                    <br/>
                    <Row>
                        <Card>
                            <div style={{padding: "20px"}}>
                                <BrowserRouter>
                                    <Routes>
                                        <Route path={"/"} element={<HomePage/>}/>
                                        <Route path={"/view/:animalId/:animalType"} element={<ViewAnimalPage/>}/>
                                    </Routes>
                                </BrowserRouter>
                            </div>
                        </Card>
                    </Row>
                    <Row>
                        <FooterComponent/>
                    </Row>
                </UserContext.Provider>
            </Container>
        </div>
    );
}

export default App;
