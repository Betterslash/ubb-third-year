import React, {useContext, useEffect, useState} from "react";
import {
    Nav,
    Navbar,
    NavbarBrand,
    NavbarToggler,
    NavItem,
    NavLink,
    UncontrolledCollapse
} from "reactstrap";
import {UserContext} from "../../context/UserContext";
import {ContextService} from "../../infrastructure/services/ContextService";
import {AuthenticationService} from "../../infrastructure/services/AuthenticationService";
import {UserModel} from "../../core/model/UserModel";

export const HeaderComponent : React.FC = () => {
    const currentUserState = useContext(UserContext);
    const logout = () => {
        AuthenticationService.logout();
        currentUserState.changeUserState({} as UserModel);
        currentUserState.setLoggedInState(false);
    }
    useEffect(() => {
        if(ContextService.isAuthenticated()){
            currentUserState.setLoggedInState(true);
        }
    }, [currentUserState,]);
    return(
        <div>
            <Navbar
                color="faded"
                light>
                <NavbarBrand
                    className="me-auto"
                    href="/">
                    Pets Manager
                </NavbarBrand>
                <NavbarToggler
                    id="toggler"
                    className="me-2"/>
                <UncontrolledCollapse navbar
                toggler="#toggler">
                    <Nav navbar>
                        <NavItem>
                            <NavLink href="#" hidden={currentUserState.loggedIn}>
                                Login
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink href="#" hidden={currentUserState.loggedIn}>
                                Register
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink href="#">
                                Home
                            </NavLink>
                        </NavItem>
                        <NavItem hidden={!currentUserState.loggedIn}>
                            <NavLink onClick={logout}>
                                Logout
                            </NavLink>
                        </NavItem>
                    </Nav>
                </UncontrolledCollapse>
            </Navbar>
        </div>
    );
}