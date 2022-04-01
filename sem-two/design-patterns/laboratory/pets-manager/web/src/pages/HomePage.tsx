import React, {useContext, useEffect, useState} from "react";
import {LoginComponent} from "../components/LoginComponent";
import {Container, Nav, NavItem, NavLink, TabContent, TabPane} from "reactstrap";
import {UserContext} from "../context/UserContext";
import {ContextService} from "../infrastructure/services/ContextService";
import {ManagePetsComponent} from "../components/ManagePetsComponent";
import {AddAnimalComponent} from "../components/AddAnimalComponent";

export const HomePage: React.FC = () => {
    const userState = useContext(UserContext);
    const [activeTab, setActiveTab] = useState("1");
    useEffect(() => {
        if(ContextService.isAuthenticated()){
            userState.setLoggedInState(true);
        }
    }, [userState]);
    return (
        <Container>
            <div hidden={userState.loggedIn}>
                <LoginComponent
                    loggedIn={userState.loggedIn}
                    setLoggedInState={userState.setLoggedInState}
                    currentUser={userState.currentUser}
                    changeUserState={userState.changeUserState}/>
            </div>
            <div hidden={!userState.loggedIn}>
                <Nav tabs>
                    <NavItem>
                        <NavLink
                            className=""
                            onClick={() => setActiveTab("1")}>
                            Available Animals
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink
                            className=""
                            onClick={() => setActiveTab("2")}>
                            Add Animal
                        </NavLink>
                    </NavItem>
                </Nav>
                <TabContent activeTab={activeTab}>
                    <TabPane tabId="1">
                        <ManagePetsComponent/>
                    </TabPane>
                    <TabPane tabId="2">
                        <AddAnimalComponent/>
                    </TabPane>
                </TabContent>

            </div>
        </Container>
    );
}