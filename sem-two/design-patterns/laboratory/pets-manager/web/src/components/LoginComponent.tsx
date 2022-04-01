import React, {useState} from "react";
import {Card, FormGroup, Form, Input, Button, Label, CardTitle, Container, Col, Row, Spinner} from "reactstrap";
import {UserContextProps} from "../context/UserContext";
import {UserModel} from "../core/model/UserModel";
import {AuthenticationService} from "../infrastructure/services/AuthenticationService";

export const LoginComponent: React.FC<UserContextProps> =
    ({currentUser, changeUserState, loggedIn, setLoggedInState}) => {
    const [isProcessing, setIsProcessing] = useState(false);
    const login = (): void => {
        setIsProcessing(true);
        const currentFormUser = {
            username: currentUser.username,
            password: currentUser.password
        } as UserModel;
        AuthenticationService.authenticate(currentFormUser)
            .then((response) => {
                if(response.data.body){
                    changeUserState(currentFormUser);
                    setIsProcessing(false);
                }
                setIsProcessing(false);
            });
    }

    return (
        <Container>
            <Card className={"text-center"}>
                <Col hidden={isProcessing}>
                    <Row>
                        <Container>
                            <br/>
                            <CardTitle tag={"h3"}>
                                Login
                            </CardTitle>
                        </Container>
                    </Row>
                    <br/>
                    <Row>
                        <Form className="form">
                            <Container>
                                <FormGroup>
                                    <Label for="usernameInput"
                                           hidden={true}>
                                        Username</Label>
                                    <Input
                                        onInput={(e) => currentUser.username = e.currentTarget.value}
                                        type="text"
                                        name="usernameInput"
                                        id="usernameInput"
                                        placeholder="Username"
                                    />
                                </FormGroup>
                            </Container>
                            <Container>
                                <FormGroup>
                                    <Label for="passwordInput"
                                           hidden={true}>
                                        Password</Label>
                                    <Input
                                        onInput={(e) => currentUser.password = e.currentTarget.value}
                                        type="password"
                                        name="passwordInput"
                                        id="passwordInput"
                                        placeholder="Password"
                                    />
                                </FormGroup>
                            </Container>
                            <Container>
                                <Row>
                                   <Container>
                                       <Button color={"primary"}
                                            onClick={login}>Submit</Button>
                                   </Container>
                                </Row>
                                <br/>
                                <Row>
                                    <Container>
                                        Register
                                    </Container>
                                </Row>
                                <br/>
                            </Container>
                        </Form>
                    </Row>
                </Col>
                <Col>
                    <Spinner hidden={!isProcessing}
                             color={"info"}
                             size={"lg"}>
                        Processing...
                    </Spinner>
                </Col>
            </Card>

        </Container>
    );
}